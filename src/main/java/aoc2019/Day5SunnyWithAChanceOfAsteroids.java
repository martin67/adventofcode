package aoc2019;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day5SunnyWithAChanceOfAsteroids {

    int diagnosticCode(String opcodeStrings, int input) throws InterruptedException {

        List<Integer> opcodes = Stream.of(opcodeStrings.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        CountDownLatch countDownLatch = new CountDownLatch(1);
        IntcodeComputer ic = new IntcodeComputer(opcodes, input, countDownLatch);
        new Thread(ic).start();
        countDownLatch.await();

        Integer[] output = ic.getOutputQueue().toArray(new Integer[0]);
        return output[output.length - 1];
    }
}
