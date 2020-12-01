package aoc.aoc2020;

import java.util.ArrayList;
import java.util.List;

public class Day1ReportRepair {
    static final int SUM = 2020;
    List<Integer> entries = new ArrayList<>();

    public Day1ReportRepair(List<String> inputLines) {
        for (String line : inputLines) {
            entries.add(Integer.parseInt(line));
        }
    }

    int sumAndMultiplyTwoEntries() {
        for (int a : entries) {
            for (int b : entries) {
                if (a + b == SUM) {
                    return a * b;
                }
            }
        }
        return 0;
    }

    long sumAndMultiplyThreeEntries() {
        for (int a : entries) {
            for (int b : entries) {
                for (int c : entries) {
                    if (a + b + c == SUM) {
                        return a * b * c;
                    }
                }
            }
        }
        return 0;
    }
}
