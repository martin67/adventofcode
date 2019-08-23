import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19GoWithTheFlow {

    @Data
    @AllArgsConstructor
    static class Instruction {
        OpCode opCode;
        int a;
        int b;
        int c;

        @Override
        public String toString() {
            return opCode.getName() + " " + a + " " + b + " " + c;
        }
    }

    private Processor processor;
    private List<Instruction> instructions;


    public Day19GoWithTheFlow(String fileName) throws IOException {
        processor = new Processor(6);
        readInstructions(fileName);
    }

    private void readInstructions(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));
        Iterator<String> inputIterator = inputStrings.iterator();

        int ip = Integer.parseInt(StringUtils.substringAfter(inputIterator.next(), "#ip "));
        processor.setInstructionPointerIndex(ip);

        String regexStr = "(\\w+) (\\d+) (\\d+) (\\d+)";
        // Compile the regex String into a Pattern
        Pattern pattern = Pattern.compile(regexStr);

        instructions = new ArrayList<>();
        while (inputIterator.hasNext()) {
            String line = inputIterator.next();
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String opCodeName = matcher.group(1);
                int a = Integer.parseInt(matcher.group(2));
                int b = Integer.parseInt(matcher.group(3));
                int c = Integer.parseInt(matcher.group(4));
                Optional<OpCode> opCode = processor.getOpCode(opCodeName);

                opCode.ifPresent(o -> {
                    instructions.add(new Instruction(o, a, b, c));
                });
            }
        }

    }

    void printPseudoCode() {
        int index = 0;
        for (Instruction instruction : instructions) {
            System.out.print(index + ": ");
            System.out.println(instruction.getOpCode().pseudoCode(instruction.getA(),
                        instruction.getB(), instruction.getC()));
            index++;
        }
    }

    int getLeftInRegister() {

        boolean programEnd = false;
        int iterations = 0;
        while (!programEnd && iterations < 1000) {
            processor.saveInstructionPointer();
            System.out.print("ip=" + processor.getInstructionPointer() + " " + processor.registerToString());
            Instruction instruction = instructions.get(processor.getInstructionPointer());
            System.out.print(instruction + " ");
            // update the instruction pointer register to the current value of the instruction pointer

            instruction.getOpCode().operator(instruction.getA(), instruction.getB(), instruction.getC());
            System.out.println(processor.registerToString());

            processor.loadInstructionPointer();
            processor.incrementInstructionPointer();
            if (processor.getInstructionPointer() >= instructions.size()) {
                programEnd = true;
            }
            iterations++;
        }
        return processor.getRegister()[0];
    }

    int getLeftInRegister2() {
        printPseudoCode();
        int result = 0;

        if (false) {
            int[] newRegister = {1, 0, 0, 0, 0, 0};
            processor.setRegister(newRegister);
            result = getLeftInRegister();
        } else {
            // use this, but change r2 to one less than r1
            // ip=3 [0, 10551410, 123, 1, 0, 3] mulr 3 2 4 [0, 10551410, 123, 1, 123, 3]

            int[] newRegister = {0, 10551410, 10551409, 1, 0, 3};
            processor.setRegister(newRegister);
            processor.setInstructionPointer(3);
            result = getLeftInRegister();
        }
        return result;
    }

    int implementPseudoCode() {
        long r0 = 0;
        long r1 = 10551410;
        long r2 = 0;
        long r3 = 0;
        long r4 = 10550400;
        long r5 = 0;

//        2:  r2 = 1
//        3:  r4 = r2 * r3
//        4:  if r1 == r4 goto 7 else goto 8
//        7:  r0 = r0 + r3                        --
//        8:  r2 = r2 + 1
//        9:  if r2 > r1 goto 12 else goto 3
//        12: r3 = r3 + 1
//        13: if r3 > r1 exit else goto 2

        // divide r1 into factors and add them = will give r0

        // all possible combinations of r2 and r3 where r2*r3 = r1
        while (r1 > r3) {
            r2 = 1;
            while (r2 <= r1) {
                r4 = r2 * r3;
                if (r4 == r1) {
                    System.out.println("r0: " + r0 + ", r1: " + r1 + ", r2: " + r2 + ", r3: " + r3 + ", r4: " + r4);
                    r0 = r0 + r3;
                }
                r2 = r2 + 1;
            }
            r3 = r3 + 1;
        }

        return (int)r0;
    }

    int findFactors(int number) {

        List<Integer> factors = new ArrayList<>();

        System.out.print("Factors of " + number + " are: ");
        for(int i = 1; i <= number; ++i) {
            if (number % i == 0) {
                factors.add(i);
                System.out.print(i + " ");
            }
        }
        System.out.println ("Factors of " + number + " are: " + factors.size());

        return factors.stream().mapToInt(Integer::intValue).sum();
    }
}
