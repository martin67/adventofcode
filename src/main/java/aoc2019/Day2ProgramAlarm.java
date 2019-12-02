package aoc2019;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day2ProgramAlarm {

    int valueAtProgramHalt(String opcodeString, Integer noun, Integer verb) {
        List<Integer> opcodes = Stream.of(opcodeString.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int programCounter = 0;
        if (noun != null & verb != null) {
            opcodes.set(1, noun);
            opcodes.set(2, verb);
        }

        while (true) {
            //log.info("status: " + opcodes + " (" + programCounter + ")");
            switch (opcodes.get(programCounter)) {
                case 1:
                    opcodes.set(opcodes.get(programCounter + 3),
                            opcodes.get(opcodes.get(programCounter + 1)) + opcodes.get(opcodes.get(programCounter + 2)));
                    break;
                case 2:
                    opcodes.set(opcodes.get(programCounter + 3),
                            opcodes.get(opcodes.get(programCounter + 1)) * opcodes.get(opcodes.get(programCounter + 2)));
                    break;
                case 99:
                    //log.info("Exiting");
                    return opcodes.get(0);

                default:
                    log.error("Ooops");
                    break;
            }
            programCounter += 4;
        }
    }

    int nounAndVerb(String opcodeString) {
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                int result = valueAtProgramHalt(opcodeString, noun, verb);
                if (result == 19690720) {
                    return 100 * noun + verb;
                }
            }
        }
        return 0;
    }
}
