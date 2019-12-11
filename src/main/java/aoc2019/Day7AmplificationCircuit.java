package aoc2019;

import com.google.common.collect.Collections2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7AmplificationCircuit {

    private final List<String> opcodes;
    private int highestSignal;

    public Day7AmplificationCircuit(List<String> inputLines) {
        opcodes = Stream.of(inputLines.get(0).split(","))
                .collect(Collectors.toList());
        highestSignal = 0;
    }

    int highestSignal() throws InterruptedException {
        // Generate all possible phase settings (permutations) => n! => 5! = 1*2*3*4*5 = 120
        Collection<List<Integer>> permutations = Collections2.permutations(Arrays.asList(0, 1, 2, 3, 4));

        for (List<Integer> phaseSetting : permutations) {
            CountDownLatch countDownLatch = new CountDownLatch(5);
            List<IntcodeComputer> computers = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                IntcodeComputer ic = new IntcodeComputer(opcodes, countDownLatch);
                ic.getInputQueue().add(new BigInteger(String.valueOf(phaseSetting.get(i))));
                computers.add(ic);
            }

            computers.get(0).setOutputQueue(computers.get(1).getInputQueue());
            computers.get(1).setOutputQueue(computers.get(2).getInputQueue());
            computers.get(2).setOutputQueue(computers.get(3).getInputQueue());
            computers.get(3).setOutputQueue(computers.get(4).getInputQueue());

            computers.get(0).getInputQueue().add(new BigInteger("0"));

            for (int i = 0; i < 5; i++) {
                new Thread(computers.get(i)).start();
            }

            // Wait until all threads have completed. last entry in the ampE.outputQueue is the signal
            countDownLatch.await();

            BigInteger[] output = computers.get(4).getOutputQueue().toArray(new BigInteger[0]);
            int signal = output[output.length - 1].intValue();

            if (signal > highestSignal) {
                highestSignal = signal;
            }
        }
        return highestSignal;
    }

    int highestSignalFeedbackLoop() throws InterruptedException {
        Collection<List<Integer>> permutations = Collections2.permutations(Arrays.asList(5, 6, 7, 8, 9));

        for (List<Integer> phaseSetting : permutations) {
            CountDownLatch countDownLatch = new CountDownLatch(5);
            List<IntcodeComputer> computers = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                IntcodeComputer ic = new IntcodeComputer(opcodes, countDownLatch);
                ic.getInputQueue().add(new BigInteger(String.valueOf(phaseSetting.get(i))));
                computers.add(ic);
            }

            computers.get(0).setOutputQueue(computers.get(1).getInputQueue());
            computers.get(1).setOutputQueue(computers.get(2).getInputQueue());
            computers.get(2).setOutputQueue(computers.get(3).getInputQueue());
            computers.get(3).setOutputQueue(computers.get(4).getInputQueue());
            computers.get(4).setOutputQueue(computers.get(0).getInputQueue());

            computers.get(0).getInputQueue().add(new BigInteger("0"));

            for (int i = 0; i < 5; i++) {
                new Thread(computers.get(i)).start();
            }

            countDownLatch.await();

            BigInteger[] output = computers.get(4).getOutputQueue().toArray(new BigInteger[0]);
            int signal = output[output.length - 1].intValue();

            if (signal > highestSignal) {
                highestSignal = signal;
            }
        }
        return highestSignal;
    }
}
