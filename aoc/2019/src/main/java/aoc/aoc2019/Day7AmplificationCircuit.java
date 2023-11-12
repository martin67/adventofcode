package aoc.aoc2019;

import com.google.common.collect.Collections2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7AmplificationCircuit {

    private final ExecutorService executorService;
    private final List<String> opcodes;
    private int highestSignal;

    public Day7AmplificationCircuit(List<String> inputLines) {
        executorService = Executors.newCachedThreadPool();
        opcodes = Stream.of(inputLines.get(0).split(","))
                .collect(Collectors.toList());
        highestSignal = 0;
    }

    int highestSignal() throws InterruptedException {
        // Generate all possible phase settings (permutations) => n! => 5! = 1*2*3*4*5 = 120
        Collection<List<Integer>> permutations = Collections2.permutations(Arrays.asList(0, 1, 2, 3, 4));

        for (List<Integer> phaseSetting : permutations) {
            List<IntcodeComputer> computers = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                IntcodeComputer ic = new IntcodeComputer(opcodes);
                ic.getInputQueue().add(new BigInteger(String.valueOf(phaseSetting.get(i))));
                computers.add(ic);
            }

            computers.get(0).setOutputQueue(computers.get(1).getInputQueue());
            computers.get(1).setOutputQueue(computers.get(2).getInputQueue());
            computers.get(2).setOutputQueue(computers.get(3).getInputQueue());
            computers.get(3).setOutputQueue(computers.get(4).getInputQueue());

            computers.get(0).getInputQueue().add(new BigInteger("0"));

            executorService.invokeAll(computers);

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
            List<IntcodeComputer> computers = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                IntcodeComputer ic = new IntcodeComputer(opcodes);
                ic.getInputQueue().add(new BigInteger(String.valueOf(phaseSetting.get(i))));
                computers.add(ic);
            }

            computers.get(0).setOutputQueue(computers.get(1).getInputQueue());
            computers.get(1).setOutputQueue(computers.get(2).getInputQueue());
            computers.get(2).setOutputQueue(computers.get(3).getInputQueue());
            computers.get(3).setOutputQueue(computers.get(4).getInputQueue());
            computers.get(4).setOutputQueue(computers.get(0).getInputQueue());

            computers.get(0).getInputQueue().add(new BigInteger("0"));

            executorService.invokeAll(computers);

            BigInteger[] output = computers.get(4).getOutputQueue().toArray(new BigInteger[0]);
            int signal = output[output.length - 1].intValue();

            if (signal > highestSignal) {
                highestSignal = signal;
            }
        }
        return highestSignal;
    }
}
