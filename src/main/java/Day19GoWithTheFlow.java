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

    int getLeftInRegister() {

        boolean programEnd = false;

        while (!programEnd) {
            processor.saveInstructionPointer();
            //System.out.print("ip=" + processor.getInstructionPointer() + " " + processor.registerToString());
            Instruction instruction = instructions.get(processor.getInstructionPointer());
            //System.out.print(instruction + " ");
            // update the instruction pointer register to the current value of the instruction pointer

            instruction.getOpCode().operator(instruction.getA(), instruction.getB(), instruction.getC());
            //System.out.println(processor.registerToString());

            processor.loadInstructionPointer();
            processor.incrementInstructionPointer();
            if (processor.getInstructionPointer() >= instructions.size()) {
                programEnd = true;
            }
        }
        return processor.getRegister()[0];
    }

}
