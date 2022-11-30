package aoc.aoc2017;

import java.util.*;
import java.util.stream.Collectors;

public class Day6MemoryReallocation {

    private final List<Integer> banks;
    private final Map<String, Integer> bankStateCycles;

    public Day6MemoryReallocation(List<String> input) {
        banks = Arrays.stream(input.get(0).split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());
        bankStateCycles = new HashMap<>();
    }

    public int countCycles(boolean partTwo) {
        int cycles = 0;

        while (!bankStateCycles.containsKey(currentState())) {
            bankStateCycles.put(currentState(), cycles);

            int largestBankIndex = banks.indexOf(Collections.max(banks));
            int largestBankContents = banks.get(largestBankIndex);

            // Empty the largest bank
            banks.set(largestBankIndex, 0);

            int completeRedistributions = largestBankContents / banks.size();
            int remaining = largestBankContents % banks.size();

            // update all banks with the complete redistributions
            banks.replaceAll(integer -> integer + completeRedistributions);

            // distribute to the right
            if (remaining > (banks.size() - largestBankIndex - 1)) {
                // fill all the way to the right and then continue from start
                for (int i = largestBankIndex + 1; i < banks.size(); i++) {
                    banks.set(i, banks.get(i) + 1);
                }
                for (int i = 0; i < remaining - (banks.size() - largestBankIndex) + 1; i++) {
                    banks.set(i, banks.get(i) + 1);
                }
            } else {
                // only fill to the right
                for (int i = largestBankIndex + 1; i < largestBankIndex + remaining + 1; i++) {
                    banks.set(i, banks.get(i) + 1);
                }
            }
            cycles++;
        }

        if (partTwo) {
            return cycles - bankStateCycles.get(currentState());
        } else {
            return cycles;
        }
    }

    private String currentState() {
        StringJoiner joiner = new StringJoiner("-");
        for (Integer i : banks) {
            joiner.add(i.toString());
        }
        return joiner.toString();
    }
}
