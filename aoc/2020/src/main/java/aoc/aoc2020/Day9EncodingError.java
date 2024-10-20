package aoc.aoc2020;

import java.util.ArrayList;
import java.util.List;

public class Day9EncodingError {
    final int preamble;
    final List<Long> numbers = new ArrayList<>();

    Day9EncodingError(int preamble, List<String> inputLines) {
        this.preamble = preamble;
        for (String line : inputLines) {
            numbers.add(Long.parseLong(line));
        }
    }

    long problem1() {
        for (int i = preamble; i < numbers.size(); i++) {
            if (!checkSum(i, numbers.get(i))) {
                return numbers.get(i);
            }
        }
        return 0;
    }

    boolean checkSum(int index, long number) {
        for (int i = index - preamble; i < index; i++) {
            for (int j = index - preamble; j < index; j++) {
                if (i != j && numbers.get(i) + numbers.get(j) == number) {
                    return true;
                }
            }
        }
        return false;
    }

    long problem2() {
        long invalidNumber = problem1();

        for (int i = 0; i < numbers.size(); i++) {
            int end = findRange(i, invalidNumber);
            if (end > 0) {
                long min = Long.MAX_VALUE;
                long max = 0;
                for (int j = i; j < end; j++) {
                    if (numbers.get(j) < min) {
                        min = numbers.get(j);
                    }
                    if (numbers.get(j) > max) {
                        max = numbers.get(j);
                    }
                }
                return min + max;

            }
        }
        return 0;
    }

    int findRange(int start, long target) {
        int end = start + 1;
        long sum = numbers.get(start);

        while (sum < target) {
            sum += numbers.get(end);
            end++;
        }
        if (sum == target) {
            return end;
        } else {
            return 0;
        }
    }

}
