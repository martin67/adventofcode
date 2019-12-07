package aoc2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7AmplificationCircuit {

    private IntcodeComputer ic;
    private List<Integer> opcodes;
    private int highestSignal;


    public Day7AmplificationCircuit(List<String> inputLines) {
        ic = new IntcodeComputer();
        highestSignal = 0;
        opcodes = Stream.of(inputLines.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    int highestSignal() {
        // Generate all possible phase settings (permutations) => n! => 5! = 1*2*3*4*5 = 120
        List<int[]> allPhaseSettings = new ArrayList<>();
        heapPermutation(allPhaseSettings, new int[]{0, 1, 2, 3, 4}, 5);

        for (int[] phaseSetting : allPhaseSettings) {
            int input = 0;
            List<Integer> out = null;
            for (int i = 0; i < 5; i++) {
                List<Integer> programInput = new ArrayList<>(Arrays.asList(phaseSetting[i], input));
                out = ic.run(opcodes, programInput);
                input = out.get(0);
            }

            if (out.get(0) > highestSignal) {
                highestSignal = out.get(0);
            }
        }
        return highestSignal;
    }

    int highestSignalFeedbackLoop() {
        List<int[]> allPhaseSettings = new ArrayList<>();
        heapPermutation(allPhaseSettings, new int[]{5, 6, 7, 8, 9}, 5);

        for (int[] phaseSetting : allPhaseSettings) {
            int input = 0;
            List<Integer> out = null;
            for (int i = 0; i < 5; i++) {
                List<Integer> programInput = new ArrayList<>(Arrays.asList(phaseSetting[i], input));
                out = ic.run(opcodes, programInput);
                input = out.get(0);
            }

            if (out.get(0) > highestSignal) {
                highestSignal = out.get(0);
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
