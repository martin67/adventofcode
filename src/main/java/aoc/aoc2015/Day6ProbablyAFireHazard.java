package aoc.aoc2015;

import aoc.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6ProbablyAFireHazard {

    long lightsLit(List<String> inputLines) {

        Map<Position, Boolean> lights = new HashMap<>();
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                lights.put(new Position(x, y), false);
            }
        }
        // toggle 461,550 through 564,900
        // turn off 370,39 through 425,839
        Pattern pattern = Pattern.compile("(.*) (\\d+),(\\d+) through (\\d+),(\\d+)");

        for (String line : inputLines) {
            // commands
            // toggle, turn on, turn off

            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String command = matcher.group(1);
                Position start = new Position(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
                Position end = new Position(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)));

                for (int x = start.getX(); x < end.getX(); x++) {
                    for (int y = start.getY(); y < end.getY(); y++) {
                        Position pos = new Position(x, y);
                        switch (command) {
                            case "toggle":
                                lights.put(pos, !lights.get(pos));
                                break;
                            case "turn on":
                                lights.put(pos, true);
                                break;
                            case "turn off":
                                lights.put(pos, false);
                                break;
                        }
                    }
                }
            }
        }
        return lights.values().stream().filter(b -> b).count();
    }
}

// 542387 too low