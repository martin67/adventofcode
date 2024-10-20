package aoc.aoc2023;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day10PipeMaze {
    final Map<Position, Character> map = new HashMap<>();
    final List<Position> loop = new ArrayList<>();
    int height;
    int width;

    Day10PipeMaze(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c != '.') {
                    map.put(new Position(x, y), c);
                }
                x++;
            }
            y++;
            width = x;
        }
        height = y;

        var start = map.entrySet().stream().filter(e -> e.getValue() == 'S').findFirst().orElseThrow().getKey();
        log.info("start: {}", start);

        // Which are connected to start?
        List<Position> connected = new ArrayList<>();
        for (var position : start.allAdjacent()) {
            if (map.containsKey(position) && getConnected(position, map.get(position)).contains(start)) {
                connected.add(position);
            }
        }
        log.info("Start connected to: {}", connected);

        // Walk the loop
        loop.add(start);
        var previous = start;
        var current = connected.getFirst();
        while (!current.equals(start)) {
            loop.add(current);
            Set<Position> next = getConnected(current, map.get(current));
            Position finalPrevious = previous;
            previous = current;
            current = next.stream().filter(p -> !p.equals(finalPrevious)).findFirst().orElseThrow();
        }

    }

    int problem1() {
        log.info("Loop length: {}", loop.size());
        log.info("Inside: {}", getInsideLoop(new HashSet<>(loop)));
        return loop.size() / 2;
    }

    int problem2() {
        Set<Position> inside = new HashSet<>();

        // replace S with its real value
        // Start in the upper left corner
        // check include mode
        // go right, set include mode on
        // if the next is not in the loop, add it if include mode is on
        // if the next is anything other than -, toggle mode
        // stop when outside
        // go one row down

        return 0;
    }

    Optional<Set<Position>> getLoop(Position position) {
        if (!map.containsKey(position)) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    Set<Position> getInsideLoop(Set<Position> loop) {
        Position.printMap(loop);
        Set<Position> inside = new HashSet<>();
        var upperLeft = Position.upperLeft(loop);
        var lowerRight = Position.lowerRight(loop);
        boolean isInside;
        log.info("ul: {}, lr: {}", upperLeft, lowerRight);
        for (int y = upperLeft.getY(); y < lowerRight.getY() + 1; y++) {
            isInside = false;
            for (int x = upperLeft.getX(); x < lowerRight.getX() + 1; x++) {
                var position = new Position(x, y);
                log.info("Pos: {}, value: {}, inside: {}", position, map.get(position), isInside);
                if (loop.contains(position)) {
                    if (isInside) {
                        isInside = switch (map.get(position)) {
                            case '|' -> false;
                            case '7', 'J' -> false;
                            default -> true;
                        };
                    } else {
                        isInside = switch (map.get(position)) {
                            case '|' -> true;
                            case 'F', 'L' -> true;
                            default -> false;
                        };
                    }
                    log.info("next inside: {}", isInside);
                } else {
                    log.info("pos not on edge: {}", position);
                    if (isInside) {
                        inside.add(position);
                    }
                }
            }
        }
        return inside;
    }

    Set<Position> getConnected(Position position, char pipe) {
        return switch (pipe) {
            case '|' -> Set.of(new Position(position.adjacent(Direction.North)),
                    new Position(position.adjacent(Direction.South)));
            case '-' -> Set.of(new Position(position.adjacent(Direction.East)),
                    new Position(position.adjacent(Direction.West)));
            case 'L' -> Set.of(new Position(position.adjacent(Direction.North)),
                    new Position(position.adjacent(Direction.East)));
            case 'J' -> Set.of(new Position(position.adjacent(Direction.North)),
                    new Position(position.adjacent(Direction.West)));
            case '7' -> Set.of(new Position(position.adjacent(Direction.South)),
                    new Position(position.adjacent(Direction.West)));
            case 'F' -> Set.of(new Position(position.adjacent(Direction.South)),
                    new Position(position.adjacent(Direction.East)));
            default -> throw new IllegalStateException("Unexpected value: " + pipe);
        };
    }

    Set<Position> expand(Position position) {
        Set<Position> result = new HashSet<>();
        for (var p : position.allAdjacent()) {
            if (!loop.contains(p)) {
                result.addAll(expand(p));
            }
        }
        return result;
    }

//    Set<Position> getInsidePositions(Position position) {
//        return switch (map.get(position) {
//            case '|' -> Set.of(new Position(position.adjacent(Direction.East)))
//        }
//    }
}
