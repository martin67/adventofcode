package aoc.aoc2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day8HandheldHalting {
    private final List<Instruction> instructions = new ArrayList<>();

    public Day8HandheldHalting(List<String> inputLines) {
        for (String line : inputLines) {
            String[] s = line.split(" ");
            instructions.add(new Instruction(s[0], Integer.parseInt(s[1])));
        }
    }

    int accumulatorValue() {
        return runProgram(instructions).value;
    }

    private Result runProgram(List<Instruction> program) {
        int accumulator = 0;
        int pc = 0;
        Set<Integer> instructionsRun = new HashSet<>();

        while (!instructionsRun.contains(pc) && pc != program.size()) {
            instructionsRun.add(pc);
            switch (program.get(pc).operation) {
                case "nop" -> pc++;
                case "acc" -> {
                    accumulator += program.get(pc).argument;
                    pc++;
                }
                case "jmp" -> pc += program.get(pc).argument;
                default -> {
                    assert false : "Ooops";
                }
            }
        }

        return new Result((pc != program.size()), accumulator);
    }

    int accumulatorValueNoLoop() {
        int instructionToChange = 0;
        List<Instruction> modifiedInstructions;
        Result result;
        do {
            modifiedInstructions = new ArrayList<>();
            for (Instruction instruction : instructions) {
                modifiedInstructions.add(new Instruction(instruction.operation, instruction.argument));
            }

            while (modifiedInstructions.get(instructionToChange).operation.equals("acc")) {
                instructionToChange++;
            }

            if (modifiedInstructions.get(instructionToChange).operation.equals("jmp")) {
                modifiedInstructions.get(instructionToChange).operation = "nop";
            } else {
                modifiedInstructions.get(instructionToChange).operation = "jmp";
            }

            result = runProgram(modifiedInstructions);
            instructionToChange++;
        } while (result.looped);
        return result.value;
    }

    static class Instruction {
        final int argument;
        String operation;

        public Instruction(String operation, int argument) {
            this.operation = operation;
            this.argument = argument;
        }
    }

    record Result(boolean looped, int value) {
    }
}
