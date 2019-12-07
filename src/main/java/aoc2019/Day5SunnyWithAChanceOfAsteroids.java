package aoc2019;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day5SunnyWithAChanceOfAsteroids {

    int diagnosticCode(String opcodeStrings, int input) {

        List<Integer> opcodes = Stream.of(opcodeStrings.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<Integer> in = Collections.singletonList(input);
        IntcodeComputer ic = new IntcodeComputer();
        List<Integer> out = ic.run(opcodes, in);
        return out.get(out.size() - 1);
    }
}
