package aoc.aoc2023;

import org.checkerframework.checker.units.qual.C;
import org.checkerframework.checker.units.qual.N;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day18LavaductLagoon {
    final List<Dig> digs = new ArrayList<>();

    public Day18LavaductLagoon(List<String> inputLines) {
        var pattern = Pattern.compile("(\\w) (\\d+) \\(#(\\w+)\\)");
        for (String line : inputLines) {
            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                digs.add(new Dig(Direction.fromString(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        matcher.group(3)));
            }
        }
    }

    public int problem1() {
        Set<Position> map = new HashSet<>();
        var position = new Position(0, 0);
        map.add(position);
        for (var dig : digs) {
            for (int i = 0; i < dig.steps; i++) {
                position = position.adjacent(dig.direction);
                map.add(position);
            }
        }
        Position.printMap(map, true);

        Set<Position> insideMap = new HashSet<>(map);
        int sum = 0;
        for (int y = Position.getYMin(map); y < Position.getYMax(map) + 1; y++) {
            boolean nextIsInside = false;
            int numberOfWalls = 0;

            int finalY = y;
            var pos = map.stream()
                    .filter(p -> p.getY() == finalY)
                    .sorted()
                    .toList();
            log.info("size: {}", pos.size());
            sum += pos.size();

            Position previousPosition = null;
            for (var p : pos) {
                if (previousPosition == null) {
                    previousPosition = p;
                    nextIsInside = true;
                } else {
                    // if the position is single, switch inside
                    if (!(map.contains(position.adjacent(Direction.West)) || map.contains(position.adjacent(Direction.East)))) {
                        nextIsInside = !nextIsInside;
                    } else {
                        // if it's the end of a row, switch if it has different angle than the start
                        Position startOfRow = position.adjacent(Direction.West);
                        while (map.contains(startOfRow)) {
                            startOfRow = startOfRow.adjacent(Direction.West);
                        }
                        startOfRow = startOfRow.adjacent(Direction.East);
                        if ((map.contains(startOfRow.adjacent(Direction.North)) && map.contains(position.adjacent(Direction.South))) ||
                                (map.contains(startOfRow.adjacent(Direction.South)) && map.contains(position.adjacent(Direction.North)))) {
                            nextIsInside = !nextIsInside;
                        }
                    }

                    if (nextIsInside) {
                        
                        // if it's the end of a row, switch if the start of the row is the opposite direction

                        // if the previous position
                        sum += p.getX() - previousPosition.getX() - 1;
                        for (int i = previousPosition.getX(); i < p.getX() + 1; i++) {
                            insideMap.add(new Position(i, y));

                        }
                    }
                    // change side if the border turns.
                    if (!map.contains(p.adjacent(Direction.East)) ||
                            (map.contains(p.adjacent(Direction.North)) && map.contains(p.adjacent(Direction.South)))) {
                        nextIsInside = !nextIsInside;
                    }
                    previousPosition = p;
                }
            }
            log.info("sum: {}", sum);

//            for (int x = Position.getXMin(map); x < Position.getXMax(map) + 1; x++) {
//
//                position = new Position(x, y);
//                if (map.contains(position)) {
//                    insideMap.add(position);
//                    numberOfWalls++;
//                    if (!map.contains(position.adjacent(Direction.West)) && (numberOfWalls % 2 == 1)) {
//                       inside = !inside;
//                       numberOfWalls = 0;
//                    }
//                } else {
//                    if (inside) {
//                        insideMap.add(position);
//                    }
//                }
//            }

        }
        Position.printMap(insideMap, true);

        return sum;
    }

    public int problem2() {
        return 0;
    }

    @Data
    static class Dig {
        final Direction direction;
        final int steps;
        final String colorCode;
    }
}
