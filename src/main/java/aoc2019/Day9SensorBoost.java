package aoc2019;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day9SensorBoost {

    List<String> boostKeyCode(String opcodeString, int mode) throws InterruptedException {

        List<String> opcodes = Stream.of(opcodeString.split(","))
                .collect(Collectors.toList());

        ExecutorService executorService = Executors.newCachedThreadPool();
        IntcodeComputer ic = new IntcodeComputer(opcodes);
        if (mode > 0) {
            ic.getInputQueue().add(new BigInteger(String.valueOf(mode)));
        }
        executorService.invokeAll(Collections.singletonList(ic));

        return ic.getOutputQueue().stream().map(BigInteger::toString).collect(Collectors.toList());
    }
}
