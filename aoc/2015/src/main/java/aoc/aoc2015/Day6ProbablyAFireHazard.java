package aoc.aoc2015;

import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class Day6ProbablyAFireHazard {

    public long lightsLit(List<String> inputLines, boolean nordicElvish) {

        Map<Position, Boolean> lights = new HashMap<>();
        Map<Position, Integer> brightness = new HashMap<>();

        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                if (nordicElvish) {
                    brightness.put(new Position(x, y), 0);
                } else {
                    lights.put(new Position(x, y), false);
                }
            }
        }
        // toggle 461,550 through 564,900
        // turn off 370,39 through 425,839
        var pattern = Pattern.compile("(.*) (\\d+),(\\d+) through (\\d+),(\\d+)");

        for (String line : inputLines) {
            // commands
            // toggle, turn on, turn off

            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                String command = matcher.group(1);
                Position start = new Position(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
                Position end = new Position(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)));

                if (start.getX() > end.getX()) {
                    log.error("start X > end X");
                }
                if (start.getY() > end.getY()) {
                    log.error("start Y > end Y");
                }

                for (int x = start.getX(); x <= end.getX(); x++) {
                    for (int y = start.getY(); y <= end.getY(); y++) {
                        Position pos = new Position(x, y);
                        if (nordicElvish) {
                            switch (command) {
                                case "toggle" -> brightness.put(pos, brightness.get(pos) + 2);
                                case "turn on" -> brightness.put(pos, brightness.get(pos) + 1);
                                case "turn off" -> brightness.put(pos, Math.max(0, brightness.get(pos) - 1));
                            }
                        } else {
                            switch (command) {
                                case "toggle" -> lights.put(pos, !lights.get(pos));
                                case "turn on" -> lights.put(pos, true);
                                case "turn off" -> lights.put(pos, false);
                            }
                        }
                    }
                }
            }
        }
        if (nordicElvish) {
            return brightness.values().stream().mapToInt(Integer::intValue).sum();
        } else {
            return lights.values().stream().filter(b -> b).count();
        }
    }
}
