package aoc2019;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7AmplificationCircuit {

    private final List<String> opcodes;
    private int highestSignal;

    public Day7AmplificationCircuit(List<String> inputLines) {

        highestSignal = 0;
        opcodes = Stream.of(inputLines.get(0).split(","))
                .collect(Collectors.toList());
    }

    int highestSignal() throws InterruptedException {
        // Generate all possible phase settings (permutations) => n! => 5! = 1*2*3*4*5 = 120
        List<int[]> allPhaseSettings = new ArrayList<>();
        heapPermutation(allPhaseSettings, new int[]{0, 1, 2, 3, 4}, 5);

        for (int[] phaseSetting : allPhaseSettings) {
            CountDownLatch countDownLatch = new CountDownLatch(5);

            IntcodeComputer ampA = new IntcodeComputer(opcodes, countDownLatch);
            IntcodeComputer ampB = new IntcodeComputer(opcodes, countDownLatch);
            IntcodeComputer ampC = new IntcodeComputer(opcodes, countDownLatch);
            IntcodeComputer ampD = new IntcodeComputer(opcodes, countDownLatch);
            IntcodeComputer ampE = new IntcodeComputer(opcodes, countDownLatch);

            ampA.getInputQueue().add(new BigInteger(String.valueOf(phaseSetting[0])));
            ampB.getInputQueue().add(new BigInteger(String.valueOf(phaseSetting[1])));
            ampC.getInputQueue().add(new BigInteger(String.valueOf(phaseSetting[2])));
            ampD.getInputQueue().add(new BigInteger(String.valueOf(phaseSetting[3])));
            ampE.getInputQueue().add(new BigInteger(String.valueOf(phaseSetting[4])));

            ampA.setOutputQueue(ampB.getInputQueue());
            ampB.setOutputQueue(ampC.getInputQueue());
            ampC.setOutputQueue(ampD.getInputQueue());
            ampD.setOutputQueue(ampE.getInputQueue());

            ampA.getInputQueue().add(new BigInteger("0"));

            new Thread(ampA).start();
            new Thread(ampB).start();
            new Thread(ampC).start();
            new Thread(ampD).start();
            new Thread(ampE).start();

            // Wait until all threads have completed. last entry in the ampE.outputqueue is the signal
            countDownLatch.await();

            BigInteger[] output = ampE.getOutputQueue().toArray(new BigInteger[0]);
            int signal = output[output.length - 1].intValue();

            if (signal > highestSignal) {
                highestSignal = signal;
            }
        }
        return highestSignal;
    }

    int highestSignalFeedbackLoop() throws InterruptedException {
        List<int[]> allPhaseSettings = new ArrayList<>();
        heapPermutation(allPhaseSettings, new int[]{5, 6, 7, 8, 9}, 5);

        for (int[] phaseSetting : allPhaseSettings) {
            CountDownLatch countDownLatch = new CountDownLatch(5);

            IntcodeComputer ampA = new IntcodeComputer(opcodes, countDownLatch);
            IntcodeComputer ampB = new IntcodeComputer(opcodes, countDownLatch);
            IntcodeComputer ampC = new IntcodeComputer(opcodes, countDownLatch);
            IntcodeComputer ampD = new IntcodeComputer(opcodes, countDownLatch);
            IntcodeComputer ampE = new IntcodeComputer(opcodes, countDownLatch);

            ampA.getInputQueue().add(new BigInteger(String.valueOf(phaseSetting[0])));
            ampB.getInputQueue().add(new BigInteger(String.valueOf(phaseSetting[1])));
            ampC.getInputQueue().add(new BigInteger(String.valueOf(phaseSetting[2])));
            ampD.getInputQueue().add(new BigInteger(String.valueOf(phaseSetting[3])));
            ampE.getInputQueue().add(new BigInteger(String.valueOf(phaseSetting[4])));

            ampA.setOutputQueue(ampB.getInputQueue());
            ampB.setOutputQueue(ampC.getInputQueue());
            ampC.setOutputQueue(ampD.getInputQueue());
            ampD.setOutputQueue(ampE.getInputQueue());
            ampE.setOutputQueue(ampA.getInputQueue());

            ampA.getInputQueue().add(new BigInteger("0"));

            new Thread(ampA).start();
            new Thread(ampB).start();
            new Thread(ampC).start();
            new Thread(ampD).start();
            new Thread(ampE).start();

            countDownLatch.await();

            BigInteger[] output = ampE.getOutputQueue().toArray(new BigInteger[0]);
            int signal = output[output.length - 1].intValue();

            if (signal > highestSignal) {
                highestSignal = signal;
            }
        }
        return highestSignal;
    }

    //Generating permutation using Heap Algorithm
    private void heapPermutation(List<int[]> result, int[] a, int size) {

        if (size == 1) {
            result.add(a.clone());
        }

        for (int i = 0; i < size; i++) {
            heapPermutation(result, a, size - 1);

            if (size % 2 == 1) {
                int temp = a[0];
                a[0] = a[size - 1];
                a[size - 1] = temp;
            } else {
                int temp = a[i];
                a[i] = a[size - 1];
                a[size - 1] = temp;
            }
        }
    }
}
