package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
public class Day10AdapterArray {
    List<Integer> adapters = new ArrayList<>();

    public Day10AdapterArray(List<String> inputLines) {
        adapters.add(0);
        for (String line : inputLines) {
            adapters.add(Integer.parseInt(line));
        }
        int max = adapters.stream().mapToInt(a -> a).max().orElseThrow(NoSuchElementException::new);
        adapters.add(max + 3);
    }

    int joltDifference() {
        Collections.sort(adapters);
        int diff1 = 0;
        int diff3 = 0;

        for (int i = 1; i < adapters.size(); i++) {
            if (adapters.get(i) - adapters.get(i - 1) == 1) {
                diff1++;
            }
            if (adapters.get(i) - adapters.get(i - 1) == 3) {
                diff3++;
            }

        }
        log.info("1 diff: {}, 3 diff: {}", diff1, diff3);
        return diff1 * diff3;
    }

    long adapterCombinations() {
        Collections.sort(adapters);
        return evaluteCombinations(0);
    }

    long evaluteCombinations(int index) {
        long combinations = 0;
        //log.info("Evaluation started index {}, value {}", index, adapters.get(index));
        int foundCombinations = 0;
        if (index == adapters.size() - 1) {
            return 1;
        }
        for (int i = 1; i < 4; i++) {
            if (index < adapters.size() - i && adapters.get(index + i) - adapters.get(index) < 4) {
                foundCombinations++;
                combinations += evaluteCombinations(index + i);
            }
        }
        if (foundCombinations == 1 && combinations < 1) {
            combinations = 0;
        }
        //log.info("Evaluation finished for index {}, value {}, combinations {}", index, adapters.get(index), combinations);
        return combinations;
    }

    long adapterCombinations2() {
        long combinations = 1;
        Collections.sort(adapters);

        for (int index = 0; index < adapters.size(); index++) {
            int routes = 0;
            for (int i = 1; i < 4; i++) {
                if (index < adapters.size() - i && adapters.get(index + i) - adapters.get(index) < 4) {
                    routes++;
                }
            }
            if (routes > 1) {
                combinations += routes;
            }

        }
        return combinations;
    }
}
