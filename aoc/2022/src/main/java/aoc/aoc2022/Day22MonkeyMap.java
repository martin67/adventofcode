package aoc.aoc2022;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static aoc.common.Direction.*;

@Slf4j
public class Day22MonkeyMap {

    Map<Position, Character> map = new HashMap<>();
    List<Step> path = new ArrayList<>();
    Position start;
    int dimension;

    // map1     map2
    //   1        12
    // 234        3
    //   56      45
    //           6

    Map<Integer, Map<Integer, Map<Direction, Integer>>> faceMap = Map.of(
            4, Map.of(1, Map.of(Up, 2, Left, 3, Right, 6),
                    2, Map.of(Up, 1, Left, 6, Down, 5),
                    3, Map.of(Up, 1, Down, 5),
                    4, Map.of(Right, 6),
                    5, Map.of(Left, 3, Down, 2),
                    6, Map.of(Up, 4, Right, 1, Down, 2)
            ),
            50, Map.of(1, Map.of(Up, 6)));


    public Day22MonkeyMap(List<String> inputLines) {
        int y = 1;
        for (String line : inputLines) {
            int x = 1;
            if (!line.isEmpty()) {
                if (Character.isDigit(line.charAt(0))) {
                    StringBuilder sb = new StringBuilder();
                    for (char c : line.toCharArray()) {
                        if (Character.isDigit(c)) {
                            sb.append(c);
                        } else {
                            path.add(new Step(Integer.parseInt(sb.toString())));
                            sb = new StringBuilder();
                            path.add(new Step(c));
                        }
                    }
                    path.add(new Step(Integer.parseInt(sb.toString())));
                } else {
                    for (char c : line.toCharArray()) {
                        if (c != ' ') {
                            Position p = new Position(x, y);
                            map.put(p, c);
                            if (start == null) {
                                start = p;
                            }
                        }
                        x++;
                    }
                }
            }
            y++;
        }
        int xMin = map.keySet().stream().mapToInt(Position::getX).min().orElseThrow();
        int xMax = map.keySet().stream().mapToInt(Position::getX).max().orElseThrow();
        int yMin = map.keySet().stream().mapToInt(Position::getY).min().orElseThrow();
        int yMax = map.keySet().stream().mapToInt(Position::getY).max().orElseThrow();
        dimension = Math.max((xMax - xMin + 1) / 4, (yMax - yMin + 1) / 4);
        log.info("Cube dimension {}x{}", dimension, dimension);
    }

    int problem1(boolean problem2) {
        Position position = start;
        Direction direction = East;
        for (Step step : path) {
            if (step.isMove()) {
                for (int i = 0; i < step.number; i++) {
                    Position next = position.adjacent(direction);
                    if (!map.containsKey(next)) {
                        if (problem2) {
                            wrap2(position, direction);
                        } else {
                            wrap(position, direction);
                        }
                    } else {
                        if (map.get(next) == '.') {
                            position = next;
                        }
                        // Do nothing for walls
                    }
                }
            } else {
                // turn
                direction = direction.turn(step.turn);
            }
        }
        return position.getY() * 1000 + position.getX() * 4 + directionValue(direction);
    }

    void wrap(Position position, Direction direction) {
        Position next = new Position();
        switch (direction) {
            case East -> {
                next.setY(position.getY());
                next.setX(map.keySet().stream()
                        .filter(p -> p.getY() == position.getY())
                        .mapToInt(Position::getX).min().orElseThrow());
            }
            case West -> {
                next.setY(position.getY());
                next.setX(map.keySet().stream()
                        .filter(p -> p.getY() == position.getY())
                        .mapToInt(Position::getX).max().orElseThrow());
            }
            case North -> {
                next.setX(position.getX());
                next.setY(map.keySet().stream()
                        .filter(p -> p.getX() == position.getX())
                        .mapToInt(Position::getY).max().orElseThrow());
            }
            case South -> {
                next.setX(position.getX());
                next.setY(map.keySet().stream()
                        .filter(p -> p.getX() == position.getX())
                        .mapToInt(Position::getY).min().orElseThrow());
            }
        }
        if (map.get(next) == '.') {
            position.setX(next.getX());
            position.setY(next.getY());
        }
    }

    void wrap2(Position position, Direction direction) {

    }

    int getFace(Position p) {
        return 0;
    }

    int directionValue(Direction dir) {
        return switch (dir) {
            case East -> 0;
            case South -> 1;
            case West -> 2;
            case North -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + dir);
        };
    }

    int problem2() {
        return problem1(true);
    }

    class Step {
        int number;
        Direction turn;

        public Step(int number) {
            this.number = number;
        }

        public Step(char c) {
            this.turn = switch (c) {
                case 'L' -> Left;
                case 'R' -> Right;
                default -> throw new RuntimeException();
            };
        }

        boolean isMove() {
            return turn == null;
        }

        @Override
        public String toString() {
            if (isMove()) {
                return String.valueOf(number);
            } else {
                return turn.toString();
            }
        }
    }
}
