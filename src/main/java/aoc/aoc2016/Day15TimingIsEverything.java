package aoc.aoc2016;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15TimingIsEverything {

    List<Disc> discs = new ArrayList<>();

    public Day15TimingIsEverything(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^Disc #\\d+ has (\\d+) positions; at time=0, it is at position (\\d+).$");

        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                discs.add(new Disc(Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2))));
            }
        }
    }

    int firstTime() {
        return 0;
    }

    class Disc {
        int numberOfPositions;
        int currentPosition;

        public Disc(int numberOfPositions, int currentPosition) {
            this.numberOfPositions = numberOfPositions;
            this.currentPosition = currentPosition;
        }
    }
}
