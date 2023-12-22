package aoc.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day15LensLibrary {
    String input;
    public Day15LensLibrary(List<String> inputLines) {
        for (String line : inputLines) {
            input = line;
        }
    }

    public int problem1() {
        int sum = 0;
        for (String in : input.split(",")) {
            sum += hash(in);
        }
        return sum;
        //return Arrays.stream(Arrays.stream(input.split(",")).mapToInt(this::hash).sum());
    }

    public int problem2() {
        return 0;
    }

    int hash(String in) {
        int currentValue = 0;
        for (char c : in.toCharArray()) {
            int a = c;
            currentValue += a;
            currentValue *= 17;
            currentValue = currentValue % 256;

        }
        return currentValue;
    }
}
