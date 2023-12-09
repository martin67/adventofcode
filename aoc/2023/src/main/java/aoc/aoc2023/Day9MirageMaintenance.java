package aoc.aoc2023;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day9MirageMaintenance {
    List<History> histories = new ArrayList<>();

    public Day9MirageMaintenance(List<String> inputLines) {
        for (String line : inputLines) {
            var history = new History();
            List<Integer> values = new ArrayList<>();
            for (String s : line.split(" ")) {
                values.add(Integer.parseInt(s));
            }
            history.values.add(values);
            histories.add(history);
        }
    }

    public int problem1() {
        return histories.stream()
                .mapToInt(h -> {
                    h.createSequences();
                    h.extrapolate();
                    return h.values.get(0).get(h.values.get(0).size() - 1);
                })
                .sum();
    }

    public int problem2() {
        return histories.stream()
                .mapToInt(h -> {
                    h.createSequences();
                    h.extrapolateBackwards();
                    return h.values.get(0).get(0);
                })
                .sum();
    }

    static class History {
        List<List<Integer>> values = new ArrayList<>();

        List<Integer> lastValue() {
            return values.get(values.size() - 1);
        }

        List<Integer> nextSequence() {
            var value = lastValue();
            List<Integer> nextValue = new ArrayList<>();
            for (int i = 0; i < value.size() - 1; i++) {
                nextValue.add(value.get(i + 1) - value.get(i));
            }
            return nextValue;
        }

        boolean allZeros(List<Integer> value) {
            return value.stream().allMatch(v -> v == 0);
        }

        void createSequences() {
            while (!allZeros(lastValue())) {
                values.add(nextSequence());
            }
        }

        void extrapolate() {
            lastValue().add(0);
            for (int i = values.size() - 2; i > -1; i--) {
                var value = values.get(i);
                var prevValue = values.get(i + 1);
                value.add(value.get(value.size() - 1) + prevValue.get(prevValue.size() - 1));
            }
        }

        void extrapolateBackwards() {
            lastValue().add(0);
            for (int i = values.size() - 2; i > -1; i--) {
                var value = values.get(i);
                var prevValue = values.get(i + 1);
                value.add(0, value.get(0) - prevValue.get(0));
            }
        }
    }
}
