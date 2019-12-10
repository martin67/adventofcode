package aoc2019;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day9SensorBoost {
    public Day9SensorBoost() {
    }

    Stream<BigInteger> boostKeycode(String opcodeString, Integer phaseSetting) throws InterruptedException {

        List<String> opcodes = Stream.of(opcodeString.split(","))
                .collect(Collectors.toList());

        CountDownLatch countDownLatch = new CountDownLatch(1);
        IntcodeComputer ic = new IntcodeComputer(opcodes, phaseSetting, countDownLatch);
        new Thread(ic).start();
        countDownLatch.await();

        return ic.getOutputQueue().stream();
    }
}
