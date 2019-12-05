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

        while (!quit) {
            String opcodeString = opcodes.get(programCounter).toString();
            String paddedOpcodeString = StringUtils.leftPad(opcodeString, 5 - opcodeString.length(), '0');
            int v1 = paddedOpcodeString.charAt(1) == '1' ? opcodes.get(programCounter + 1) : opcodes.get(opcodes.get(programCounter + 1));
            int v2 = paddedOpcodeString.charAt(0) == '1' ? opcodes.get(programCounter + 2) : opcodes.get(opcodes.get(programCounter + 2));

            switch (paddedOpcodeString.substring(2, 4)) {
                case "01":
                    log.info("{} {}: Adding {} + {} and storing in position {}\n", programCounter, paddedOpcodeString, v1, v2, programCounter + 3);
                    opcodes.set(opcodes.get(programCounter + 3), v1 + v2);
                    programCounter += 4;
                    break;
                case "02":
                    log.info("{} {}: Multiplying {} * {} and storing in position {}\n", programCounter, paddedOpcodeString, v1, v2, programCounter + 3);
                    opcodes.set(opcodes.get(programCounter + 3), v1 * v2);
                    programCounter += 4;
                    break;
                case "03":
                    log.info("{} {}: Reading {} and storing in position {}\n", programCounter, paddedOpcodeString, input, v1);
                    opcodes.set(v1, input);
                    programCounter += 2;
                    break;
                case "04":
                    log.info("{} {}: Printing {} from position {}\n", programCounter, paddedOpcodeString, input, programCounter + 1);
                    System.out.printf("Output: %d at pc %d\n", v1, programCounter);
                    programCounter += 2;
                    break;
                case "99":
                    quit = true;
                    break;
                default:
                    log.error("oops");
            }
        }
        return 0;
    }
}
