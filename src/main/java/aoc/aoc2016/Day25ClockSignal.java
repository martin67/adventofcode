package aoc.aoc2016;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day25ClockSignal {
    MonorailComputer computer;

    public Day25ClockSignal(List<String> program) {
        computer = new MonorailComputer();
        computer.loadProgram(program);
    }

    int signalValue() {
        Map<Character, Integer> registers = new HashMap<>();
        int a = 0;
        do {
            a++;
            registers.put('a', a);
            registers.put('b', 0);
            registers.put('c', 0);
            registers.put('d', 0);
            computer.loadRegisters(registers);
            computer.run();
        } while (computer.getRegister('a') == 0);

        return computer.getRegister('a');
    }

}
