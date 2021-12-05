package aoc.aoc2021;

import aoc.Direction;
import aoc.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5HydrothermalVenture {

    Map<Position, Integer> vents = new HashMap<>();
    Map<Position, Integer> diagonalVents = new HashMap<>();

    public Day5HydrothermalVenture(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^(\\d+),(\\d+) -> (\\d+),(\\d+)");
        Matcher matcher;
        for (String line : inputLines) {
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                Position start = new Position(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                Position end = new Position(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
                if (start.getX() == end.getX() || start.getY() == end.getY()) {
                    List<Position> vent = getLine(start, end, false);
                    for (Position p : vent) {
                        vents.merge(p, 1, Integer::sum);
                    }
                }
                List<Position> vent = getLine(start, end, true);
                for (Position p : vent) {
                    diagonalVents.merge(p, 1, Integer::sum);
                }
            }
        }
    }

    private List<Position> getLine(Position start, Position end, boolean allowDiagonal) {
        List<Position> line = new ArrayList<>();
        Direction dir = start.directionTo(end, allowDiagonal);
        Position p = start;
        while (!p.equals(end)) {
            line.add(p);
            p = p.adjacent(dir);
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
