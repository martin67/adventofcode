package aoc.aoc2018;

import java.util.*;

class Day1ChronalCalibration {
    int computeFrequency(String input) {
        return Arrays.stream(input.trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .sum();
    }

    int computeDoubleFrequency(String input) {
        Set<Integer> previousFrequencies = new HashSet<>();

        List<Integer> changes = Arrays.stream(input.trim().split("\\s+"))
                .map(Integer::parseInt).toList();

        int sum = 0;
        previousFrequencies.add(0);

        while (true) {
            for (int i : changes) {
                sum += i;
                if (previousFrequencies.contains(sum)) {
                    return sum;
                } else {
                    previousFrequencies.add(sum);
                }
            }
        }
    }
}
