package aoc.aoc2016;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23SafeCracking {
    final MonorailComputer computer;

    public Day23SafeCracking(List<String> program) {
        computer = new MonorailComputer();
        computer.loadProgram(program);
    }

    int safeValue() {
        Map<Character, Integer> registers = new HashMap<>();
        registers.put('a', 7);
        registers.put('b', 0);
        registers.put('c', 0);
        registers.put('d', 0);
        computer.loadRegisters(registers);
        computer.run();
        return computer.getRegister('a');
    }

    int secondValue() {
        Map<Character, Integer> registers = new HashMap<>();
        registers.put('a', 12);
        registers.put('b', 0);
        registers.put('c', 0);
        registers.put('d', 0);
        computer.loadRegisters(registers);
        computer.setMultiplyHack(true);
        computer.run();
        return computer.getRegister('a');
    }
}
