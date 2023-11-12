package aoc.aoc2021;

import java.util.ArrayList;
import java.util.List;

public class Day1SonarSweep {

    private final List<Integer> inputValues = new ArrayList<>();

    public Day1SonarSweep(List<String> inputLines) {
        inputLines.forEach(line -> inputValues.add(Integer.parseInt(line)));
    }

    public int problem1() {
        return countIncreases(inputValues);
    }

    public int problem2() {
        List<Integer> slidingWindow = new ArrayList<>();

        for (int i = 0; i < inputValues.size(); i++) {
            if (i > 1) {
                slidingWindow.add(inputValues.get(i) + inputValues.get(i - 1) + inputValues.get(i - 2));
            }
        }
        return countIncreases(slidingWindow);
    }

    private int countIncreases(List<Integer> inputValues) {
        int previousDepth = Integer.MAX_VALUE;
        int increases = 0;
        for (int depth : inputValues) {
            if (depth > previousDepth) {
                increases++;
            }
            previousDepth = depth;
        }
        return increases;
    }
}
