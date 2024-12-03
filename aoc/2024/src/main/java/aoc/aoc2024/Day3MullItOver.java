package aoc.aoc2024;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class Day3MullItOver {

    List<String> instructions = new ArrayList<>();

    Day3MullItOver(List<String> inputLines) {
        var pattern = Pattern.compile("(mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\))");
        for (String line : inputLines) {
            var matcher = pattern.matcher(line);
            while (matcher.find()) {
                instructions.add(matcher.group(1));
            }
        }
    }

    int problem1() {
        var pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        int sum = 0;
        for (String instruction : instructions) {
            var matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
        }
        return sum;
    }

    int problem2() {
        var pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        int sum = 0;
        boolean enabled = true;
        for (String instruction : instructions) {
            if (instruction.equals("do()")) {
                enabled = true;
            } else if (instruction.equals("don't()")) {
                enabled = false;
            } else if (enabled) {
                var matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
                }
            }
        }
        return sum;
    }

}
