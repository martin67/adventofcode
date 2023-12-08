package aoc.aoc2022;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class Day4CampCleanup {

    final List<Pair> pairs = new ArrayList<>();

    public Day4CampCleanup(List<String> inputLines) {
        var pattern = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");

        for (String line : inputLines) {
            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                pairs.add(new Pair(Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4))));
            }
        }
    }

    long problem1() {
        return pairs.stream().filter(Pair::isEnclosed).count();
    }

    long problem2() {
        return pairs.size() - pairs.stream().filter(Pair::isSeparate).count();
    }

    record Pair(int firstSectionStart, int firstSectionEnd, int secondSectionStart, int secondSectionEnd) {

        boolean isEnclosed() {
            return (firstSectionStart >= secondSectionStart && firstSectionEnd <= secondSectionEnd) ||
                    (secondSectionStart >= firstSectionStart && secondSectionEnd <= firstSectionEnd);
        }

        boolean isSeparate() {
            return (firstSectionEnd < secondSectionStart || firstSectionStart > secondSectionEnd);
        }
    }
}
