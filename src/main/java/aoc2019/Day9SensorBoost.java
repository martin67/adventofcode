package aoc2019;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day9SensorBoost {

    List<String> boostkeyCode(String opcodeString, int mode) throws InterruptedException {

        List<String> opcodes = Stream.of(opcodeString.split(","))
                .collect(Collectors.toList());

        CountDownLatch countDownLatch = new CountDownLatch(1);
        IntcodeComputer ic = new IntcodeComputer(opcodes, countDownLatch);
        if (mode > 0) {
            ic.getInputQueue().add(new BigInteger(String.valueOf(mode)));
        }
        new Thread(ic).start();
        countDownLatch.await();

        return ic.getOutputQueue().stream().map(BigInteger::toString).collect(Collectors.toList());
    }
}
