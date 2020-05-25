package aoc.aoc2016;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
class MonorailComputer {
    Map<Character, Integer> registers;
    List<String> program;
    int instructionPointer;
    boolean multiplyHack;

    public MonorailComputer() {
        registers = new HashMap<>();
        registers.put('a', 0);
        registers.put('b', 0);
        registers.put('c', 0);
        registers.put('d', 0);
        instructionPointer = 0;
    }

    void loadProgram(List<String> input) {
        program = new ArrayList<>(input);
    }

    void loadRegisters(Map<Character, Integer> newRegisters) {
        registers = newRegisters;
    }

    void run() {
        while (instructionPointer < program.size()) {
            String instruction = program.get(instructionPointer);
            String[] s = instruction.split(" ");

            if (multiplyHack && instructionPointer == 2) {
                log.info("multiplyHack: a: {}, b {}", getRegister('a'), getRegister('b'));
                registers.put('a', getRegister('a') * getRegister('b'));
                registers.put('b', getRegister('b') - 1);
                registers.put('c', getRegister('b') * 2);
                registers.put('d', 0);
                instructionPointer = 16;
                continue;
            }
            //log.info("IP: {}, instruction: {}, registers:{}", instructionPointer, instruction, registers);
            char c;
            int x;
            switch (s[0]) {

                // cpy x y copies x (either an integer or the value of a register) into register y.
                case "cpy":
                    c = s[1].charAt(0);
                    if (registers.containsKey(c)) {
                        x = registers.get(c);
                    } else {
                        x = Integer.parseInt(s[1]);
                    }
                    char y = s[2].charAt(0);
                    if (registers.containsKey(y)) {
                        registers.put(y, x);
                    }
                    instructionPointer++;
                    break;

                // inc x increases the value of register x by one.
                case "inc":
                    c = s[1].charAt(0);
                    registers.put(c, registers.get(c) + 1);
                    instructionPointer++;
                    break;

                // dec x decreases the value of register x by one.
                case "dec":
                    c = s[1].charAt(0);
                    registers.put(c, registers.get(c) - 1);
                    instructionPointer++;
                    break;

                // jnz x y jumps to an instruction y away (positive means forward; negative means backward),
                // but only if x is not zero.
                case "jnz":
                    c = s[1].charAt(0);
                    if (registers.containsKey(c)) {
                        x = registers.get(c);
                    } else {
                        x = Integer.parseInt(s[1]);
                    }
                    if (x != 0) {
                        c = s[2].charAt(0);
                        if (registers.containsKey(c)) {
                            instructionPointer += registers.get(c);
                        } else {
                            instructionPointer += Integer.parseInt(s[2]);
                        }
                    } else {
                        instructionPointer++;
                    }
                    break;

                case "tgl":
                    c = s[1].charAt(0);
                    x = registers.get(c);
                    if (x + instructionPointer >= program.size()) {
                        log.info("Instruction outside of program, ignoring");
                    } else {
                        String[] rowToToggle = program.get(instructionPointer + x).split(" ", 2);
                        String toggleInstruction = rowToToggle[0];
                        String toggleArguments = rowToToggle[1];
                        switch (toggleInstruction) {
                            case "inc":
                                program.set(instructionPointer + x, "dec " + toggleArguments);
                                break;
                            case "dec":
                            case "tgl":
                                program.set(instructionPointer + x, "inc " + toggleArguments);
                                break;
                            case "jnz":
                                program.set(instructionPointer + x, "cpy " + toggleArguments);
                                break;
                            case "cpy":
                                program.set(instructionPointer + x, "jnz " + toggleArguments);
                                break;
                            default:
                                log.error("Illegal instruction : {}", instruction);
                                break;
                        }
                    }
                    instructionPointer++;
                    break;

                default:
                    log.error("Illegal instruction : {}", instruction);
                    break;
            }
        }
    }

    int getRegister(char c) {
        return registers.get(c);
    }
}
