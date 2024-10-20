package aoc.aoc2021;

import aoc.common.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Day5HydrothermalVenture {
    final Map<Position, Integer> vents = new HashMap<>();
    final Map<Position, Integer> diagonalVents = new HashMap<>();

    Day5HydrothermalVenture(List<String> inputLines) {
        var pattern = Pattern.compile("^(\\d+),(\\d+) -> (\\d+),(\\d+)");

        for (String line : inputLines) {
            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                var start = new Position(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                var end = new Position(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
                if (start.getX() == end.getX() || start.getY() == end.getY()) {
                    var vent = getLine(start, end, false);
                    for (var position : vent) {
                        vents.merge(position, 1, Integer::sum);
                    }
                }
                var vent = getLine(start, end, true);
                for (var position : vent) {
                    diagonalVents.merge(position, 1, Integer::sum);
                }
            }
        }
    }

    List<Position> getLine(Position start, Position end, boolean allowDiagonal) {
        List<Position> line = new ArrayList<>();
        var direction = start.directionTo(end, allowDiagonal);
        var position = start;
        while (!position.equals(end)) {
            line.add(position);
            position = position.adjacent(direction);
        }
        line.add(end);
        return line;
    }

    long problem1() {
        return vents.values().stream()
                .filter(s -> s > 1)
                .count();
    }

    long problem2() {
        return diagonalVents.values().stream()
                .filter(s -> s > 1)
                .count();
    }
}
