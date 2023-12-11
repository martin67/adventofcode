package aoc.aoc2023;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day11CosmicExpansion {
    final Set<Position> map = new HashSet<>();

    Day11CosmicExpansion(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == '#') {
                    map.add(new Position(x, y));
                }
                x++;
            }
            y++;
        }
    }

    long problem1() {
        expand(1);
        return getDistance();
    }

    long problem2(int size) {
        expand(size - 1);
        return getDistance();
    }

    void expand(int size) {
        int height = map.stream().mapToInt(Position::getY).max().orElseThrow();
        int width = map.stream().mapToInt(Position::getX).max().orElseThrow();

        for (int y = 0; y < height; y++) {
            int finalY = y;
            if (map.stream().noneMatch(p -> p.getY() == finalY)) {
                map.stream().filter(p -> p.getY() < finalY).forEach(p2 -> p2.setY(p2.getY() - size));
            }
        }
        for (int x = 0; x < width; x++) {
            int finalX = x;
            if (map.stream().noneMatch(p -> p.getX() == finalX)) {
                map.stream().filter(p -> p.getX() < finalX).forEach(p2 -> p2.setX(p2.getX() - size));
            }
        }
    }

    long getDistance() {
        long distance = 0;
        for (var src : map) {
            for (var dst : map) {
                if (!src.equals(dst)) {
                    distance += src.distance(dst);
                }
            }
        }
        return distance / 2;
    }
}
