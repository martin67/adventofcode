package aoc.aoc2016;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Day15TimingIsEverything {

    final Set<Disc> discs = new HashSet<>();

    public Day15TimingIsEverything(List<String> inputLines) {
        var pattern = Pattern.compile("^Disc #\\d+ has (\\d+) positions; at time=0, it is at position (\\d+).$");
        int order = 1;
        for (String line : inputLines) {
            var matcher = pattern.matcher(line);

            if (matcher.find()) {
                discs.add(new Disc(Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)), order));
                order++;
            }
        }
    }

    int firstTime() {
        int time = 0;
        boolean fallThrough = false;

        while (!fallThrough) {
            for (Disc disc : discs) {
                if (disc.getCurrentPosition(time) != 0) {
                    fallThrough = false;
                    break;
                } else {
                    fallThrough = true;
                }
            }
            time++;
        }
        return time - 1;
    }

    record Disc(int numberOfPositions, int startPosition, int order) {

        int getCurrentPosition(int time) {
            return (time + startPosition + order) % numberOfPositions;
        }
    }
}
