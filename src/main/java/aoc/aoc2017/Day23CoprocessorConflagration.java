package aoc.aoc2017;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day23CoprocessorConflagration {
    private final List<Instruction> instructions = new ArrayList<>();

    public Day23CoprocessorConflagration(List<String> inputLines) {
        inputLines.forEach(line -> {
            String[] s = line.split(" ");
            instructions.add(new Instruction(s[0], s[1], s.length == 3 ? s[2] : null));
        });
    }

    int problem1() {
        int pointer = 0;
        int numberOfMuls = 0;
        Map<String, Integer> registers = new HashMap<>();

        while (pointer >= 0 && pointer < instructions.size()) {
            Instruction instruction = instructions.get(pointer);
            log.debug("Register: {} - instruction {}, {} {}", registers, instruction.name, instruction.register, instruction.parameter);
            switch (instruction.name) {
                case "set" -> {
                    registers.put(instruction.register, instruction.getValue(registers));
                    pointer++;
                }
                case "sub" -> {
                    registers.merge(instruction.register, -instruction.getValue(registers), Integer::sum);
                    pointer++;
                }
                case "mul" -> {
                    registers.put(instruction.register, registers.getOrDefault(instruction.register, 0) * instruction.getValue(registers));
                    numberOfMuls++;
                    pointer++;
                }
                case "jnz" -> {
                    if (instruction.register.matches("\\d+")) {
                        int val = Integer.parseInt(instruction.register);
                        if (val != 0) {
                            pointer += Integer.parseInt(instruction.parameter);
                        }
                    } else if (registers.getOrDefault(instruction.register, 0) != 0) {
                        pointer += instruction.getValue(registers);
                    } else {
                        pointer++;
                    }
                }
                default -> log.error("Unknown instruction: {}", instruction.name);
            }
        }
        return numberOfMuls;
    }

    int problem2() {
        int pointer = 0;
        Map<String, Integer> registers = new HashMap<>();
        registers.put("a", 1);
        instructions.add(16, new Instruction("jnz", "1", "10"));
        int steps = 0;

        while (pointer >= 0 && pointer < instructions.size()) {
            Instruction instruction = instructions.get(pointer);
            //log.info("Register: {} - instruction {}, {} {}", registers, instruction.name, instruction.register, instruction.parameter);
            switch (instruction.name) {
                case "set" -> {
                    registers.put(instruction.register, instruction.getValue(registers));
                    pointer++;
                }
                case "sub" -> {
                    registers.merge(instruction.register, -instruction.getValue(registers), Integer::sum);
                    pointer++;
                }
                case "mul" -> {
                    registers.put(instruction.register, registers.getOrDefault(instruction.register, 0) * instruction.getValue(registers));
                    pointer++;
                }
                case "jnz" -> {
                    if (instruction.register.matches("\\d+")) {
                        int val = Integer.parseInt(instruction.register);
                        if (val != 0) {
                            pointer += Integer.parseInt(instruction.parameter);
                        }
                    } else if (registers.getOrDefault(instruction.register, 0) != 0) {
                        pointer += instruction.getValue(registers);
                    } else {
                        pointer++;
                    }
                }
                default -> log.error("Unknown instruction: {}", instruction.name);
            }
            //log.info("h: {}", registers.get("h"));
            steps++;
        }
        log.info("Register: {}, steps: {}", registers, steps);
        return registers.get("h");
    }


    record Instruction(String name, String register, String parameter) {

        int getValue(Map<String, Integer> registers) {
            if (parameter == null) {
                if (register.matches("-?\\d+")) {
                    return Integer.parseInt(register);
                } else {
                    return registers.getOrDefault(register, 0);
                }
            } else {
                if (parameter.matches("-?\\d+")) {
                    return Integer.parseInt(parameter);
                } else {
                    return registers.getOrDefault(parameter, 0);
                }
            }
        }
    }
}
