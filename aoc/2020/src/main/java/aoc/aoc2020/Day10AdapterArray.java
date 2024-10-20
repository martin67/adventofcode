package aoc.aoc2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class Day10AdapterArray {
    final List<Integer> adapters = new ArrayList<>();

    Day10AdapterArray(List<String> inputLines) {
        adapters.add(0);
        for (String line : inputLines) {
            adapters.add(Integer.parseInt(line));
        }
        int max = adapters.stream().mapToInt(a -> a).max().orElseThrow(NoSuchElementException::new);
        adapters.add(max + 3);
    }

    int problem1() {
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
        return diff1 * diff3;
    }

    long problem2() {
        long combinations = 1;
        Collections.sort(adapters);

        int index = 0;
        while (index < adapters.size() - 1) {
            int reachable = reachable(index);
            int factor = 1;

            switch (reachable) {
                case 1 -> index++;
                case 2 -> {
                    factor = 2;
                    if (reachable(index + 1) == 2) {
                        factor = 3;
                        if (reachable(index + 2) == 2) {
                            factor = 5;
                        }
                    } else if (reachable(index + 1) == 3) {
                        factor = 6;
                        if (reachable(index + 3) == 3) {
                            factor = 7;
                        }
                    }
                    index += 2;
                }
                case 3 -> {
                    factor = 3;
                    if (reachable(index + 1) == 2) {
                        factor = 4;
                        if (reachable(index + 2) == 2) {
                            factor = 6;
                        } else if (reachable(index + 2) == 3)
                            factor = 8;
                    } else if (reachable(index + 1) == 3) {
                        factor = 7;
                        if (reachable(index + 2) == 3) {
                            factor = 9;
                        }
                    }
                    index += 3;
                }
            }
            combinations *= factor;
        }

        return combinations;
    }

    int reachable(int index) {
        if (index < adapters.size()) {
            int start = adapters.get(index);
            if (index + 3 < adapters.size() && adapters.get(index + 3) - start == 3) {
                return 3;
            } else if (index + 2 < adapters.size() && (adapters.get(index + 2) - start == 3 || adapters.get(index + 2) - start == 2)) {
                return 2;
            } else if (index + 1 < adapters.size() && adapters.get(index + 1) - start < 4) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
}
