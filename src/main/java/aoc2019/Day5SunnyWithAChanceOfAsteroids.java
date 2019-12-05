package aoc2019;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day5SunnyWithAChanceOfAsteroids {

    int diagnosticCode(String opcodeStrings, int input) {
        List<Integer> opcodes = Stream.of(opcodeStrings.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        boolean quit = false;
        int programCounter = 0;
        int lastCode = 0;

        while (!quit) {
            String opcodeString = StringUtils.leftPad(opcodes.get(programCounter).toString(), 4, '0');
            int v1;
            int v2;
            switch (opcodeString.substring(2, 4)) {
                case "01":
                    v1 = opcodeString.charAt(1) == '1' ? opcodes.get(programCounter + 1) : opcodes.get(opcodes.get(programCounter + 1));
                    v2 = opcodeString.charAt(0) == '1' ? opcodes.get(programCounter + 2) : opcodes.get(opcodes.get(programCounter + 2));
                    log.info("{} {}: Adding {} + {} and storing in position {}", programCounter, opcodeString, v1, v2, opcodes.get(programCounter + 3));
                    opcodes.set(opcodes.get(programCounter + 3), v1 + v2);
                    programCounter += 4;
                    break;
                case "02":
                    v1 = opcodeString.charAt(1) == '1' ? opcodes.get(programCounter + 1) : opcodes.get(opcodes.get(programCounter + 1));
                    v2 = opcodeString.charAt(0) == '1' ? opcodes.get(programCounter + 2) : opcodes.get(opcodes.get(programCounter + 2));
                    log.info("{} {}: Multiplying {} * {} and storing in position {}", programCounter, opcodeString, v1, v2, opcodes.get(programCounter + 3));
                    opcodes.set(opcodes.get(programCounter + 3), v1 * v2);
                    programCounter += 4;
                    break;
                case "03":
                    log.info("{} {}: Reading {} and storing in position {}", programCounter, opcodeString, input, opcodes.get(programCounter + 1));
                    opcodes.set(opcodes.get(programCounter + 1), input);
                    programCounter += 2;
                    break;
                case "04":
                    log.info("{} {}: Printing {} from position {}", programCounter, opcodeString, opcodes.get(opcodes.get(programCounter + 1)), programCounter + 1);
                    System.out.printf("Output: %d\n", opcodes.get(opcodes.get(programCounter + 1)));
                    lastCode = opcodes.get(opcodes.get(programCounter + 1));
                    programCounter += 2;
                    break;
                case "99":
                    quit = true;
                    break;
                default:
                    log.error("oops");
            }
        }
        return lastCode;
    }
}
