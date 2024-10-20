package aoc.aoc2021;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class Day7TheTreacheryOfWhales {

    final int min;
    final int max;
    List<Integer> crabs = new ArrayList<>();

    Day7TheTreacheryOfWhales(List<String> inputLines) {
        inputLines.forEach(line -> crabs = Stream.of(line.split(","))
                .map(Integer::parseInt)
                .toList());
        min = crabs.stream().mapToInt(v -> v)
                .min().orElseThrow();
        max = crabs.stream().mapToInt(v -> v)
                .max().orElseThrow();
    }

    int problem1() {
        int minFuel = Integer.MAX_VALUE;
        for (int i = min; i < max; i++) {
            int fuel = 0;
            for (int crab : crabs) {
                fuel += Math.abs(crab - i);
            }
            if (fuel < minFuel) {
                minFuel = fuel;
            }
        }
        return minFuel;
    }

    int problem2() {
        int minFuel = Integer.MAX_VALUE;
        for (int i = min; i < max; i++) {
            int fuel = 0;
            for (int crab : crabs) {
                int n = Math.abs(crab - i);
                fuel += n * (n + 1) / 2;
            }
            if (fuel < minFuel) {
                minFuel = fuel;
            }
        }
        return minFuel;
    }
}
