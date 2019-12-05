package aoc2019;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day5SunnyWithAChanceOfAsteroids {

    int diagnosticCode(String opcodeString, int input) {
        List<Integer> opcodes = Stream.of(opcodeString.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        boolean quit = false;
        int programCounter = 0;

        while (!quit) {
            int opcode = opcodes.get(programCounter);
            switch (opcode) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    opcodes.set(opcodes.get(programCounter + 1), input);
                    programCounter += 2;
                    break;
                case 4:
                    System.out.printf("Output: %d at pc %d\n", opcodes.get(opcodes.get(programCounter + 1)), programCounter);
                    programCounter += 2;
                    break;
                case 99:
                    quit = true;
                    break;
                default:
                    log.error("oops");
            }
        }
        return 0;
    }
}
