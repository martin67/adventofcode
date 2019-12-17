package aoc2019;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day5SunnyWithAChanceOfAsteroids {

    int diagnosticCode(String opcodeStrings, int input) {

        List<String> opcodes = Stream.of(opcodeStrings.split(","))
                .collect(Collectors.toList());
        ExecutorService executorService = Executors.newCachedThreadPool();
        IntcodeComputer ic = new IntcodeComputer(opcodes);
        ic.getInputQueue().add(new BigInteger(String.valueOf(input)));
        Future<Integer> futureSum = executorService.submit(ic);
        try {
            futureSum.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        BigInteger[] output = ic.getOutputQueue().toArray(new BigInteger[0]);
        return output[output.length - 1].intValue();
    }
}
