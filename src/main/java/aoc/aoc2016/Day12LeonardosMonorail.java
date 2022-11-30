package aoc.aoc2016;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day12LeonardosMonorail {

    final MonorailComputer computer;


    public Day12LeonardosMonorail(List<String> program) {

        computer = new MonorailComputer();
        computer.loadProgram(program);
    }

    int registerA() {
        computer.run();
        return computer.getRegister('a');
    }

    int registerAwithC() {
        Map<Character, Integer> registers = new HashMap<>();
        registers.put('a', 0);
        registers.put('b', 0);
        registers.put('c', 1);
        registers.put('d', 0);
        computer.loadRegisters(registers);
        computer.run();
        return computer.getRegister('a');
    }

}
