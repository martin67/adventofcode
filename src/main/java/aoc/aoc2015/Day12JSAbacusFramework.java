package aoc.aoc2015;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12JSAbacusFramework {
    int sumOfAllNumbers = 0;

    public Day12JSAbacusFramework(List<String> inputLines) {
        Pattern pattern = Pattern.compile("(-?\\d+)");
        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                sumOfAllNumbers += Integer.parseInt(matcher.group(1));
            }
        }
    }

    int sumOfAllNumbers() {
        return sumOfAllNumbers;
    }
}
