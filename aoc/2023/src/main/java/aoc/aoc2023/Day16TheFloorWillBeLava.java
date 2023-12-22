package aoc.aoc2023;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day16TheFloorWillBeLava {
    final Map<Position, Character> map = new HashMap<>();

    public Day16TheFloorWillBeLava(List<String> inputLines) {

        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c != '.') {
                    var position = new Position(x, y);
                    map.put(position, c);
                }
                x++;
            }
            y++;
        }
    }

    public int problem1() {
        int maxX = map.keySet().stream().mapToInt(Position::getX).max().orElseThrow();
        int maxY = map.keySet().stream().mapToInt(Position::getY).max().orElseThrow();
        Set<Beam> beams = new HashSet<>();
        Set<Beam> activeBeams = new HashSet<>();
        Set<Beam> nextActiveBeams = new HashSet<>();

        var start = new Beam(new Position(0, 0), Direction.East);
        beams.add(start);
        activeBeams.add(start);

        while (!activeBeams.isEmpty()) {
            for (var beam : activeBeams) {
                var nextPosition = beam.position.adjacent(beam.direction);
                Direction nextDirection;

                // Check if the beam goes off map
                if (nextPosition.getX() == 0 && beam.direction == Direction.West ||
                        nextPosition.getX() == maxX && beam.direction == Direction.East ||
                        nextPosition.getY() == 0 && beam.direction == Direction.North ||
                        nextPosition.getY() == maxY && beam.direction == Direction.South) {
                    beams.add(beam);
                } else {
                    if (map.containsValue(nextPosition)) {
                        nextDirection = switch (map.get(nextPosition)) {
                            case '\\' -> switch (beam.direction) {
                                case North -> Direction.West;
                                case South -> Direction.East;
                                case West -> Direction.North;
                                case East -> Direction.South;
                                default -> throw new IllegalArgumentException();
                            };
                            case '/' -> switch (beam.direction) {
                                case North -> Direction.East;
                                case South -> Direction.West;
                                case West -> Direction.South;
                                case East -> Direction.North;
                                default -> throw new IllegalArgumentException();
                            };
                            case '-' -> switch (beam.direction) {
                                case North, South -> {
                                    var mirrorBeam = new Beam(nextPosition, Direction.West);
                                    if (!beams.contains(mirrorBeam)) {
                                        nextActiveBeams.add(mirrorBeam);
                                    }
                                    yield Direction.East;
                                }
                                case West, East -> beam.direction;
                                default -> throw new IllegalArgumentException();
                            };
                            case '|' -> switch (beam.direction) {
                                case West, East -> {
                                    var mirrorBeam = new Beam(nextPosition, Direction.North);
                                    if (!beams.contains(mirrorBeam)) {
                                        nextActiveBeams.add(mirrorBeam);
                                    }
                                    yield Direction.South;
                                }
                                case North, South -> beam.direction;
                                default -> throw new IllegalArgumentException();
                            };
                            default -> throw new IllegalArgumentException();
                        };
                    } else {
                        nextDirection = beam.direction;
                    }
                    var nextBeam = new Beam(nextPosition, nextDirection);
                    if (!beams.contains(nextBeam)) {
                        nextActiveBeams.add(nextBeam);
                    }
                }
            }
            activeBeams = nextActiveBeams;
        }

        return beams.size();
    }

    public int problem2() {
        return 0;
    }

    @Data
    @AllArgsConstructor
    static class Beam {
        Position position;
        Direction direction;
    }
}
