package aoc.aoc2021;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day6Lanternfish {
    List<Integer> fishes = new ArrayList<>();

    Day6Lanternfish(List<String> inputLines) {
        inputLines.forEach(line -> fishes = Stream.of(line.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList()));
    }

    int problem1() {
        for (int i = 0; i < 80; i++) {
            int newFishes = 0;

            for (int j = 0; j < fishes.size(); j++) {
                if (fishes.get(j) == 0) {
                    fishes.set(j, 6);
                    newFishes++;
                } else {
                    fishes.set(j, fishes.get(j) - 1);
                }
            }
            for (int j = 0; j < newFishes; j++) {
                fishes.add(8);
            }
        }
        return fishes.size();
    }

    long problem2() {
        Map<Integer, Long> fishAges = new HashMap<>();

        for (int i = 0; i < 9; i++) {
            fishAges.put(i, 0L);
        }

        for (int fish : fishes) {
            fishAges.merge(fish, 1L, Long::sum);
        }

        for (int i = 0; i < 256; i++) {
            long newFishes = fishAges.get(0);
            for (int j = 0; j < 8; j++) {
                fishAges.put(j, fishAges.get(j + 1));
            }
            fishAges.put(6, fishAges.get(6) + newFishes);
            fishAges.put(8, newFishes);
        }

        return fishAges.values().stream().mapToLong(Long::longValue).sum();
    }
}
