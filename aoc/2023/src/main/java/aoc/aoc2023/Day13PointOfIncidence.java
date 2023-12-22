package aoc.aoc2023;

import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day13PointOfIncidence {

    final List<Set<Position>> maps = new ArrayList<>();

    public Day13PointOfIncidence(List<String> inputLines) {
        Set<Position> map = new HashSet<>();
        maps.add(map);
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            if (line.isEmpty()) {
                map = new HashSet<>();
                maps.add(map);
                y = 0;
            } else {
                for (char c : line.toCharArray()) {
                    if (c == '#') {
                        map.add(new Position(x, y));
                    }
                    x++;
                }
                y++;
            }
        }
    }

    public int problem1() {
        int sum = 0;
        int numberOfColumns = 0;
        int numberOfRows = 0;

        for (var map : maps) {
            log.info("----------------");
            int height = map.stream().mapToInt(Position::getY).max().orElseThrow() + 1;
            int width = map.stream().mapToInt(Position::getX).max().orElseThrow() + 1;
            int maxColumnMirrors = 0;
            int maxRowMirrors= 0;
            int maxColumnValue = 0;
            int maxRowValue = 0;

            // vertical
            for (int x = 0; x < width - 1; x++) {
                if (sameRowValues(getColumn(map, x), getColumn(map, x + 1))) {
                    log.info("found match at column {}", x);
                    // Check number of mirror images
                    int distance = 1;
                    Set<Position> left;
                    Set<Position> right;

                    do {
                        int finalDistance = distance;
                        int finalX = x;
                        left = map.stream().filter(p -> p.getX() == finalX - finalDistance).collect(Collectors.toSet());
                        right = map.stream().filter(p -> p.getX() == finalX + finalDistance + 1).collect(Collectors.toSet());
                        distance++;
                    } while (sameRowValues(left, right) && x - distance > 0 && x + distance < width);
                    log.info("Found {} column mirror images", distance);
                    maxColumnMirrors = distance;
                    maxColumnValue = x + 1;
                    break;
                }
            }

            // horizontal
            for (int y = 0; y < height - 1; y++) {
                    if (sameColumnValues(getRow(map, y), getRow(map, y + 1))) {

                        log.info("found match at row {}", y);
                    // Check number of mirror images
                    int distance = 1;
                    Set<Position> upper;
                    Set<Position> lower;

                    do {
                        int finalDistance = distance;
                        int finalY = y;
                        upper = map.stream().filter(p -> p.getY() == finalY - finalDistance).collect(Collectors.toSet());
                        lower = map.stream().filter(p -> p.getY() == finalY + finalDistance + 1).collect(Collectors.toSet());
                        distance++;
                    } while (sameColumnValues(upper, lower) && y - distance > 0 && y + distance < height);
                    log.info("Found {} row mirror images", distance);
                    maxRowMirrors = distance;
                    maxRowValue = y + 1;
                    break;
                }
            }
            if (maxColumnMirrors > maxRowMirrors) {
                log.info("  Using column with value {}", maxColumnValue);
                numberOfColumns += maxColumnValue;
                sum += maxColumnValue;
            } else {
                log.info("  Using row with value {}", maxRowValue);
                numberOfRows += maxRowValue;
                sum += maxRowValue * 100;
            }
        }
        return numberOfColumns + numberOfRows * 100;
    }

    boolean sameRowValues(Set<Position> left, Set<Position> right) {
        if (left.size() != right.size()) {
            return false;
        }
        Set<Integer> values1 = left.stream().map(Position::getY).collect(Collectors.toSet());
        Set<Integer> values2 = right.stream().map(Position::getY).collect(Collectors.toSet());
        return values1.containsAll(values2);
    }

    boolean sameColumnValues(Set<Position> upper, Set<Position> lower) {
        if (upper.size() != lower.size()) {
            return false;
        }
        Set<Integer> values1 = upper.stream().map(Position::getX).collect(Collectors.toSet());
        Set<Integer> values2 = lower.stream().map(Position::getX).collect(Collectors.toSet());
        return new HashSet<>(values1).containsAll(values2);
    }

    Set<Position> getColumn(Set<Position> map, int index) {
        return map.stream().filter(p -> p.getX() == index).collect(Collectors.toSet());
    }

    Set<Position> getRow(Set<Position> map, int index) {
        return map.stream().filter(p -> p.getY() == index).collect(Collectors.toSet());
    }

    public int problem2() {
        return 0;
    }
}
