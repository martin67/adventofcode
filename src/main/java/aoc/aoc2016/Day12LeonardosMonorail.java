package aoc.aoc2016;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day12LeonardosMonorail {

    Computer computer;


    public Day12LeonardosMonorail(List<String> program) {

        computer = new Computer();
        computer.load(program);
    }

    int registerA() {
        computer.run();
        return computer.getRegister('a');
    }

    static class Computer {
        Map<Character, Integer> registers;
        List<String> program;
        int instructionPointer;

        public Computer() {
            registers = new HashMap<>();
            registers.put('a', 0);
            registers.put('b', 0);
            registers.put('c', 0);
            registers.put('d', 0);
            instructionPointer = 0;
        }

        void load(List<String> input) {
            program = new ArrayList<>(input);
        }

        void run() {
            while (instructionPointer < program.size()) {
                String instruction = program.get(instructionPointer);
                String[] s = instruction.split(" ");
                char c;
                int x;
                switch (s[0]) {
                    case "cpy":
                        c = s[1].charAt(0);
                        if (registers.containsKey(c)) {
                            x = registers.get(c);
                        } else {
                            x = Integer.parseInt(s[1]);
                        }
                        char y = s[2].charAt(0);
                        registers.put(y, x);
                        instructionPointer++;
                        break;
                    case "inc":
                        c = s[1].charAt(0);
                        registers.put(c, registers.get(c) + 1);
                        instructionPointer++;
                        break;
                    case "dec":
                        c = s[1].charAt(0);
                        registers.put(c, registers.get(c) - 1);
                        instructionPointer++;
                        break;
                    case "jnz":
                        c = s[1].charAt(0);
                        if (registers.containsKey(c)) {
                            x = registers.get(c);
                        } else {
                            x = Integer.parseInt(s[1]);
                        }
                        if (x != 0) {
                            instructionPointer += Integer.parseInt(s[2]);
                        } else {
                            instructionPointer++;
                        }
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

}
