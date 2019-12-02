package aoc2019;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2ProgramAlarm {

    int valueAtProgramHalt(String opcodeString) {
        List<Integer> opcodes =  Stream.of(opcodeString.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return 0;
    }
}
