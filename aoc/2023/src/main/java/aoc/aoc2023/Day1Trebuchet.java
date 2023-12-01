package aoc.aoc2023;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day1Trebuchet {
    static final List<String> numbers = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
    List<List<Integer>> calibrationValues = new ArrayList<>();

    public Day1Trebuchet(List<String> inputLines, boolean useStrings) {
        for (String line : inputLines) {
            List<Integer> value = new ArrayList<>();
            int i = 0;
            while (i < line.length()) {
                if (useStrings) {
                    String s = line.substring(i);
                    for (int j = 0; j < numbers.size(); j++) {
                        if (s.startsWith(numbers.get(j))) {
                            value.add(j + 1);
                            break;
                        }
                    }
                }
                if (Character.isDigit(line.charAt(i))) {
                    value.add(Character.getNumericValue(line.charAt(i)));
                }
                i++;
            }
            calibrationValues.add(value);
        }
    }

    public int problem() {
        int sum = 0;
        for (var cv : calibrationValues) {
            int first = cv.get(0);
            int last = cv.get(cv.size() - 1);
            int value = first * 10 + last;
            sum += value;
            log.info("{} + {} = {}", first, last, value);
        }
        return sum;
    }
}
