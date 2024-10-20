package aoc.aoc2022;

import java.util.*;

public class Day1CalorieCounting {
    final Map<Integer, Integer> elves = new HashMap<>();

    Day1CalorieCounting(List<String> inputLines) {
        int elv = 1;
        for (String line : inputLines) {
            if (line.isEmpty()) {
                elv++;
            } else {
                int calorie = Integer.parseInt(line);
                elves.merge(elv, calorie, Integer::sum);
            }
        }
    }

    int problem1() {
        return Collections.max(elves.values());
    }

    int problem2() {
        List<Integer> sortedCalories = new ArrayList<>(elves.values());
        sortedCalories.sort(Collections.reverseOrder());
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            sum += sortedCalories.get(i);
        }
        return sum;
    }
}
