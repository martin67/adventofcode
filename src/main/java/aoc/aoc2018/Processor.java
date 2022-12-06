package aoc.aoc2018;

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

@Data
public class Processor {

    private int[] register;
    private int instructionPointer;
    private int instructionPointerIndex;
    private List<OpCode> opCodes;
    private List<Instruction> instructions;

    Processor(int numberOfRegisters) {
        opCodes = new ArrayList<>();
        register = new int[numberOfRegisters];

        setupOpCodes();
    }

    private void setupOpCodes() {
        opCodes.add(new OpCode("addr") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] + register[b];
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                if (a > b) {
                    return "r" + c + " = r" + b + " + r" + a;
                } else {
                    return "r" + c + " = r" + a + " + r" + b;
                }
            }
        });

        opCodes.add(new OpCode("addi") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] + b;
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                return "r" + c + " = r" + a + " + " + b;
            }
        });

        opCodes.add(new OpCode("mulr") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] * register[b];
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                if (a > b) {
                    return "r" + c + " = r" + b + " * r" + a;
                } else {
                    return "r" + c + " = r" + a + " * r" + b;
                }
            }
        });

        opCodes.add(new OpCode("muli") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] * b;
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                return "r" + c + " = r" + a + " * " + b;
            }
        });

        opCodes.add(new OpCode("banr") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] & register[b];
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                if (a > b) {
                    return "r" + c + " = r" + b + " AND r" + a;
                } else {
                    return "r" + c + " = r" + a + " AND r" + b;
                }
            }
        });

        opCodes.add(new OpCode("bani") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] & b;
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                return "r" + c + " = r" + a + " AND " + b;
            }
        });

        opCodes.add(new OpCode("borr") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] | register[b];
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                if (a > b) {
                    return "r" + c + " = r" + b + " OR r" + a;
                } else {
                    return "r" + c + " = r" + a + " OR r" + b;
                }
            }
        });

        opCodes.add(new OpCode("bori") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] | b;
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                return "r" + c + " = r" + a + " OR " + b;
            }
        });

        opCodes.add(new OpCode("setr") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a];
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                return "r" + c + " = r" + a;
            }
        });

        opCodes.add(new OpCode("seti") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = a;
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                return "r" + c + " = " + a;
            }
        });

        opCodes.add(new OpCode("gtir") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = a > register[b] ? 1 : 0;
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                return "if " + a + " > r" + b + " then r" + c + " = 1 else r" + c + " = 0";
            }
        });

        opCodes.add(new OpCode("gtri") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] > b ? 1 : 0;
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                return "if r" + a + " > " + b + " then r" + c + " = 1 else r" + c + " = 0";
            }
        });

        opCodes.add(new OpCode("gtrr") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] > register[b] ? 1 : 0;
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                return "if r" + a + " > r" + b + " then r" + c + " = 1 else r" + c + " = 0";
            }
        });

        opCodes.add(new OpCode("eqir") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = a == register[b] ? 1 : 0;
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                return "if " + a + " == r" + b + " then r" + c + " = 1 else r" + c + " = 0";
            }
        });

        opCodes.add(new OpCode("eqri") {
            void operator(int a, int b, int c) {
                register[c] = register[a] == b ? 1 : 0;
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                return "if r" + a + " == " + b + " then r" + c + " = 1 else r" + c + " = 0";
            }
        });

        opCodes.add(new OpCode("eqrr") {
            @Override
            void operator(int a, int b, int c) {
                register[c] = register[a] == register[b] ? 1 : 0;
            }

            @Override
            public String pseudoCode(int a, int b, int c) {
                return "if r" + a + " == r" + b + " then r" + c + " = 1 else r" + c + " = 0";
            }
        });
    }

    private Optional<OpCode> getOpCode(String opCodeName) {
        return opCodes.stream().filter(opCode -> opCode.getName().equals(opCodeName)).findFirst();
    }

    String registerToString() {
        StringBuilder output = new StringBuilder();

        output.append("[");
        for (int i = 0; i < register.length; i++) {
            if (i == register.length - 1) {
                output.append(register[i]).append("] ");
            } else {
                output.append(register[i]).append(", ");
            }
        }
        return output.toString();
    }

    void saveInstructionPointer() {
        register[instructionPointerIndex] = instructionPointer;
    }

    void loadInstructionPointer() {
        instructionPointer = register[instructionPointerIndex];
    }

    void incrementInstructionPointer() {
        instructionPointer++;
    }

    void readInstructions(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));
        Iterator<String> inputIterator = inputStrings.iterator();

        int ip = Integer.parseInt(StringUtils.substringAfter(inputIterator.next(), "#ip "));
        setInstructionPointerIndex(ip);

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
                Optional<OpCode> opCode = getOpCode(opCodeName);

                opCode.ifPresent(o -> instructions.add(new Instruction(o, a, b, c)));
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

    int runCode(int maxIterations) {
        boolean programEnd = false;
        int iterations = 0;
        while (!programEnd && iterations < maxIterations) {
//            if (getInstructionPointer() == 28) {
//                break;
//            }
            saveInstructionPointer();
//            System.out.printf("(%4d) ip=%-6d %35s", iterations, getInstructionPointer(), registerToString());
            Instruction instruction = getInstructions().get(getInstructionPointer());
            // update the instruction pointer register to the current value of the instruction pointer

            instruction.getOpCode().operator(instruction.getA(), instruction.getB(), instruction.getC());
//            System.out.printf(" => %-35s  %-40s %s", registerToString(), instruction.getOpCode().pseudoCode(instruction.getA(),
//                    instruction.getB(), instruction.getC()), instruction);
//            System.out.println();
            loadInstructionPointer();
            incrementInstructionPointer();
            if (getInstructionPointer() >= getInstructions().size()) {
                programEnd = true;
            }
            iterations++;
        }
        return iterations;
    }

    @Data
    static class OpCode {
        int sampleId;
        private String name;

        OpCode(String name) {
            this.name = name;
        }

        void operator(int a, int b, int c) {
        }

        String pseudoCode(int a, int b, int c) {
            return null;
        }
    }

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

}

