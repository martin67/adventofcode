package aoc.aoc2016;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
public class MonorailComputer {
    private Map<Character, Integer> registers;
    private List<String> program;
    private int instructionPointer;
    private boolean multiplyHack;

    public MonorailComputer() {
        registers = new HashMap<>();
        registers.put('a', 0);
        registers.put('b', 0);
        registers.put('c', 0);
        registers.put('d', 0);
    }

    public void loadProgram(List<String> input) {
        program = new ArrayList<>(input);
    }

    public void loadRegisters(Map<Character, Integer> newRegisters) {
        registers = newRegisters;
    }

    public void run() {
        instructionPointer = 0;
        int signalRepeats = 0;
        int signalStart = getRegister('a');
        int lastSignal = -1;

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
                case "cpy" -> {
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
                }

                // inc x increases the value of register x by one.
                case "inc" -> {
                    c = s[1].charAt(0);
                    registers.put(c, registers.get(c) + 1);
                    instructionPointer++;
                }

                // dec x decreases the value of register x by one.
                case "dec" -> {
                    c = s[1].charAt(0);
                    registers.put(c, registers.get(c) - 1);
                    instructionPointer++;
                }

                // jnz x y jumps to an instruction y away (positive means forward; negative means backward),
                // but only if x is not zero.
                case "jnz" -> {
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
                }
                case "tgl" -> {
                    c = s[1].charAt(0);
                    x = registers.get(c);
                    if (x + instructionPointer >= program.size()) {
                        log.info("Instruction outside of program, ignoring");
                    } else {
                        String[] rowToToggle = program.get(instructionPointer + x).split(" ", 2);
                        String toggleInstruction = rowToToggle[0];
                        String toggleArguments = rowToToggle[1];
                        switch (toggleInstruction) {
                            case "inc" -> program.set(instructionPointer + x, "dec " + toggleArguments);
                            case "dec", "tgl" -> program.set(instructionPointer + x, "inc " + toggleArguments);
                            case "jnz" -> program.set(instructionPointer + x, "cpy " + toggleArguments);
                            case "cpy" -> program.set(instructionPointer + x, "jnz " + toggleArguments);
                            default -> log.error("Illegal instruction : {}", instruction);
                        }
                    }
                    instructionPointer++;
                }
                case "out" -> {
                    c = s[1].charAt(0);
                    if (registers.containsKey(c)) {
                        x = registers.get(c);
                    } else {
                        x = Integer.parseInt(s[1]);
                    }
                    if (x == lastSignal || signalRepeats > 100) {
                        log.debug("Got {} repeats for a={}", signalRepeats, signalStart);
                        if (signalRepeats > 100) {
                            log.info("Found it: {}!", signalStart);
                            registers.put('a', signalStart);
                        } else {
                            registers.put('a', 0);
                        }
                        instructionPointer = program.size();
                    } else {
                        lastSignal = x;
                        signalRepeats++;
                        instructionPointer++;
                    }
                }
                default -> log.error("Illegal instruction : {}", instruction);
            }
        }
    }

    int getRegister(char c) {
        return registers.get(c);
    }
}
