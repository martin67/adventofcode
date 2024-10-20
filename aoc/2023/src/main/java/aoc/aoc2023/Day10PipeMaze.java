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
        return loop.size() / 2;
    }

    int problem2() {
        Set<Position> inside = new HashSet<>();

        // Replace the S with a real value
        var start = loop.getFirst();
        char c = getStartChar(loop);
        map.put(start, c);

        // go clockwise
        Position previousPosition = loop.getLast();

        for (var position : loop) {
            switch (map.get(position)) {
                case '|' -> {
                    if (previousPosition.directionTo(position, false) == Direction.North) {
                        if (!loop.contains(position.adjacent(Direction.East))) {
                            inside.add(position.adjacent(Direction.East));
                        }
                    } else {
                        if (!loop.contains(position.adjacent(Direction.West))) {
                            inside.add(position.adjacent(Direction.West));
                        }
                    }
                }
                case '-' -> {
                    if (previousPosition.directionTo(position, false) == Direction.East) {
                        if (!loop.contains(position.adjacent(Direction.South))) {
                            inside.add(position.adjacent(Direction.South));
                        }
                    } else {
                        if (!loop.contains(position.adjacent(Direction.North))) {
                            inside.add(position.adjacent(Direction.North));
                        }
                    }
                }
                case '7' -> {
                    if (previousPosition.directionTo(position, false) == Direction.East) {
                        if (!loop.contains(position.adjacent(Direction.SouthWest))) {
                            inside.add(position.adjacent(Direction.SouthWest));
                        }
                    } else {
                        if (!loop.contains(position.adjacent(Direction.North))) {
                            inside.add(position.adjacent(Direction.North));
                        }
                        if (!loop.contains(position.adjacent(Direction.NorthEast))) {
                            inside.add(position.adjacent(Direction.NorthEast));
                        }
                        if (!loop.contains(position.adjacent(Direction.East))) {
                            inside.add(position.adjacent(Direction.East));
                        }
                    }
                }
                case 'J' -> {
                    if (previousPosition.directionTo(position, false) == Direction.South) {
                        if (!loop.contains(position.adjacent(Direction.NorthWest))) {
                            inside.add(position.adjacent(Direction.NorthWest));
                        }
                    } else {
                        if (!loop.contains(position.adjacent(Direction.South))) {
                            inside.add(position.adjacent(Direction.South));
                        }
                        if (!loop.contains(position.adjacent(Direction.SouthEast))) {
                            inside.add(position.adjacent(Direction.SouthEast));
                        }
                        if (!loop.contains(position.adjacent(Direction.East))) {
                            inside.add(position.adjacent(Direction.East));
                        }
                    }
                }
                case 'L' -> {
                    if (previousPosition.directionTo(position, false) == Direction.West) {
                        if (!loop.contains(position.adjacent(Direction.NorthEast))) {
                            inside.add(position.adjacent(Direction.NorthEast));
                        }
                    } else {
                        if (!loop.contains(position.adjacent(Direction.West))) {
                            inside.add(position.adjacent(Direction.West));
                        }
                        if (!loop.contains(position.adjacent(Direction.SouthWest))) {
                            inside.add(position.adjacent(Direction.SouthWest));
                        }
                        if (!loop.contains(position.adjacent(Direction.South))) {
                            inside.add(position.adjacent(Direction.South));
                        }
                    }
                }
                case 'F' -> {
                    if (previousPosition.directionTo(position, false) == Direction.North) {
                        if (!loop.contains(position.adjacent(Direction.SouthEast))) {
                            inside.add(position.adjacent(Direction.SouthEast));
                        }
                    } else {
                        if (!loop.contains(position.adjacent(Direction.West))) {
                            inside.add(position.adjacent(Direction.West));
                        }
                        if (!loop.contains(position.adjacent(Direction.NorthWest))) {
                            inside.add(position.adjacent(Direction.NorthWest));
                        }
                        if (!loop.contains(position.adjacent(Direction.North))) {
                            inside.add(position.adjacent(Direction.North));
                        }
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + map.get(position));
            }
            previousPosition = position;
        }

        // There might be more inside positions
        Set<Position> allInside = new HashSet<>();
        for (Position p : inside) {
            if (!allInside.contains(p)) {
                Set<Position> positionsToCheck = getConnectedPositions(new HashSet<>(), p);
                allInside.addAll(positionsToCheck);
            }
        }

        return allInside.size();
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

    char getStartChar(List<Position> positionList) {
        Position start = positionList.getFirst();
        Position next = positionList.get(1);
        Position previous = positionList.getLast();

        return switch (start.directionTo(next, false)) {
            case North -> switch (start.directionTo(previous, false)) {
                case South -> '|';
                case East -> 'L';
                case West -> 'J';
                default -> throw new IllegalStateException("Unexpected value: " + start.directionTo(previous, false));
            };
            case East -> switch (start.directionTo(previous, false)) {
                case South -> 'F';
                case West -> '-';
                case North -> 'L';
                default -> throw new IllegalStateException("Unexpected value: " + start.directionTo(previous, false));
            };
            case South -> switch (start.directionTo(previous, false)) {
                case West -> '7';
                case North -> '|';
                case East -> 'F';
                default -> throw new IllegalStateException("Unexpected value: " + start.directionTo(previous, false));
            };
            case West -> switch (start.directionTo(previous, false)) {
                case North -> 'J';
                case East -> '-';
                case South -> '7';
                default -> throw new IllegalStateException("Unexpected value: " + start.directionTo(previous, false));
            };
            default -> throw new IllegalStateException("Unexpected value: " + start.directionTo(next, false));
        };
    }

    Set<Position> getConnectedPositions(Set<Position> alreadyVisited, Position position) {
        Set<Position> result = new HashSet<>();
        alreadyVisited.add(position);
        result.add(position);
        for (Position p : position.allAdjacent()) {
            if (!alreadyVisited.contains(p) && !loop.contains(p)) {
                result.addAll(getConnectedPositions(alreadyVisited, p));
            }
        }
        return result;
    }
}
