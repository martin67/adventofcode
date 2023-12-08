package aoc.aoc2022;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class Day14RegolithReservoir {
    final Set<Position> map = new HashSet<>();
    final Position source = new Position(500, 0);
    int xMin;
    int xMax;
    int yMax;

    public Day14RegolithReservoir(List<String> inputLines) {
        var pattern = Pattern.compile("(\\d+),(\\d+)");

        for (String line : inputLines) {
            List<Position> positions = new ArrayList<>();
            var matcher = pattern.matcher(line);
            while (matcher.find()) {
                positions.add(new Position(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
            }

            Position start = null;
            for (Position p : positions) {
                if (start == null) {
                    start = p;
                } else {
                    while (!p.equals(start)) {
                        map.add(start);
                        Direction d = start.directionTo(p, false);
                        start = start.adjacent(d);
                    }
                    map.add(p);
                }
            }
        }
        xMin = map.stream().mapToInt(Position::getX).min().orElseThrow();
        xMax = map.stream().mapToInt(Position::getX).max().orElseThrow();
        yMax = map.stream().mapToInt(Position::getY).max().orElseThrow();
    }

    long problem1(boolean problem2) {
        int rocks = map.size();
        long previousGrains = 0;
        boolean stop = false;
        while (!stop) {
            pourSand(problem2);
            long grains = map.size() - rocks;
            if (grains == previousGrains) {
                stop = true;
            } else {
                previousGrains = grains;
            }
        }
        return map.size() - rocks;
    }

    void pourSand(boolean problem2) {
        Position start = new Position(source);
        Position next;
        boolean stop = false;
        while (!stop) {
            next = moveGrain(start, problem2);
            if (next == null) {
                stop = true;
            } else {
                if (next.equals(start)) {
                    map.add(next);
                    stop = true;
                } else {
                    start = next;
                }
            }
        }
    }

    Position moveGrain(Position start, boolean problem2) {
        if (start.getY() == yMax) {
            // at bottom
            if (problem2) {
                return start;
            } else {
                return null;
            }
        }
        if (!map.contains(start.adjacent(Direction.South))) {
            return start.adjacent(Direction.Down);
        } else if (!map.contains(start.adjacent(Direction.SouthWest))) {
            if (start.getX() == xMin) {
                return null;
            }
            return start.adjacent(Direction.SouthWest);
        } else if (!map.contains(start.adjacent(Direction.SouthEast))) {
            if (start.getX() == xMax) {
                return null;
            }
            return start.adjacent(Direction.SouthEast);
        } else {
            // could not move the grain any further
            return start;
        }
    }

    long problem2() {
        yMax = yMax + 1;
        xMin = Integer.MIN_VALUE;
        xMax = Integer.MAX_VALUE;
        return problem1(true);
    }

}
