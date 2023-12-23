package aoc.aoc2023;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
class Day14ParabolicReflectorDish {
    Set<Position> roundRocks = new HashSet<>();
    Set<Position> cubicRocks = new HashSet<>();
    int height;
    int width;

    Day14ParabolicReflectorDish(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == 'O') {
                    roundRocks.add(new Position(x, y));
                } else if (c == '#') {
                    cubicRocks.add(new Position(x, y));
                }
                x++;
                width = x;
            }
            y++;
        }
        height = y;
    }

    int problem1() {
        var rocks = roll(roundRocks, Direction.North);
        return getLoad(rocks);
    }

    int problem2() {
        List<Set<Position>> previousRocks = new ArrayList<>();
        previousRocks.add(new HashSet<>(roundRocks));
        Set<Position> rocks = new HashSet<>(roundRocks);

        for (int i = 0; i < 1000; i++) {
            rocks = roll(rocks, Direction.North);
            rocks = roll(rocks, Direction.West);
            rocks = roll(rocks, Direction.South);
            rocks = roll(rocks, Direction.East);

            for (int j = 0; j < previousRocks.size(); j++) {
                var r = previousRocks.get(j);
                if (r.containsAll(rocks)) {
                    int remains = (1000000000 - j) % (i - j + 1);
                    return getLoad(previousRocks.get(j + remains));
                }
            }
            previousRocks.add(new HashSet<>(rocks));
        }
        return 0;
    }

    Set<Position> roll(Set<Position> rocks, Direction direction) {
        Set<Position> nextRocks = new HashSet<>();
        switch (direction) {
            case North -> {
                for (int y = 0; y < height; y++) {
                    int finalY = y;
                    var rocksToRoll = rocks.stream().filter(p -> p.getY() == finalY).toList();
                    for (var rock : rocksToRoll) {
                        if (rock.getY() > 0 && !cubicRocks.contains(rock.adjacent(Direction.North)) && !nextRocks.contains(rock.adjacent(Direction.North))) {
                            var nextPosition = rock.adjacent(Direction.North);
                            while (nextPosition.getY() > 0 && !cubicRocks.contains(nextPosition.adjacent(Direction.North)) && !nextRocks.contains(nextPosition.adjacent(Direction.North))) {
                                nextPosition = nextPosition.adjacent(Direction.North);
                            }
                            nextRocks.add(nextPosition);
                        } else {
                            nextRocks.add(rock);
                        }
                    }
                }
            }
            case West -> {
                for (int x = 0; x < width; x++) {
                    int finalX = x;
                    var rocksToRoll = rocks.stream().filter(p -> p.getX() == finalX).toList();
                    for (var rock : rocksToRoll) {
                        if (rock.getX() > 0 && !cubicRocks.contains(rock.adjacent(Direction.West)) && !nextRocks.contains(rock.adjacent(Direction.West))) {
                            var nextPosition = rock.adjacent(Direction.West);
                            while (nextPosition.getX() > 0 && !cubicRocks.contains(nextPosition.adjacent(Direction.West)) && !nextRocks.contains(nextPosition.adjacent(Direction.West))) {
                                nextPosition = nextPosition.adjacent(Direction.West);
                            }
                            nextRocks.add(nextPosition);
                        } else {
                            nextRocks.add(rock);
                        }
                    }
                }
            }
            case South -> {
                for (int y = height - 1; y >= 0; y--) {
                    int finalY = y;
                    var rocksToRoll = rocks.stream().filter(p -> p.getY() == finalY).toList();
                    for (var rock : rocksToRoll) {
                        if (rock.getY() < height - 1 && !cubicRocks.contains(rock.adjacent(Direction.South)) && !nextRocks.contains(rock.adjacent(Direction.South))) {
                            var nextPosition = rock.adjacent(Direction.South);
                            while (nextPosition.getY() < height - 1 && !cubicRocks.contains(nextPosition.adjacent(Direction.South)) && !nextRocks.contains(nextPosition.adjacent(Direction.South))) {
                                nextPosition = nextPosition.adjacent(Direction.South);
                            }
                            nextRocks.add(nextPosition);
                        } else {
                            nextRocks.add(rock);
                        }
                    }
                }
            }
            case East -> {
                for (int x = width - 1; x >= 0; x--) {
                    int finalX = x;
                    var rocksToRoll = rocks.stream().filter(p -> p.getX() == finalX).toList();
                    for (var rock : rocksToRoll) {
                        if (rock.getX() < width - 1 && !cubicRocks.contains(rock.adjacent(Direction.East)) && !nextRocks.contains(rock.adjacent(Direction.East))) {
                            var nextPosition = rock.adjacent(Direction.East);
                            while (nextPosition.getX() < width - 1 && !cubicRocks.contains(nextPosition.adjacent(Direction.East)) && !nextRocks.contains(nextPosition.adjacent(Direction.East))) {
                                nextPosition = nextPosition.adjacent(Direction.East);
                            }
                            nextRocks.add(nextPosition);
                        } else {
                            nextRocks.add(rock);
                        }
                    }
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
        return nextRocks;
    }

    int getLoad(Set<Position> rocks) {
        int sum = 0;
        for (var rock : rocks) {
            sum += height - rock.getY();
        }
        return sum;
    }

}
