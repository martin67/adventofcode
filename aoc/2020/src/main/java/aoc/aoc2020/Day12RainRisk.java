package aoc.aoc2020;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day12RainRisk {
    final List<Action> actions = new ArrayList<>();

    Day12RainRisk(List<String> inputLines) {
        for (String line : inputLines) {
            actions.add(new Action(line.charAt(0), Integer.parseInt(line.substring(1))));
        }
    }

    int problem1() {
        var position = new Position(0, 0);
        var direction = Direction.East;

        for (var action : actions) {
            switch (action.instruction) {
                case 'N' -> position.setY(position.getY() - action.argument);
                case 'S' -> position.setY(position.getY() + action.argument);
                case 'E' -> position.setX(position.getX() + action.argument);
                case 'W' -> position.setX(position.getX() - action.argument);
                case 'L' -> direction = direction.turn(Direction.Left, action.argument);
                case 'R' -> direction = direction.turn(Direction.Right, action.argument);
                case 'F' -> {
                    for (int i = 0; i < action.argument; i++) {
                        position = position.adjacent(direction);
                    }
                }
            }
        }
        return position.distance(new Position(0, 0));
    }

    int problem2() {
        var position = new Position(0, 0);
        var waypoint = new Position(10, -1);
        int dX;
        int dY;

        for (var action : actions) {
            switch (action.instruction) {
                case 'N' -> waypoint.setY(waypoint.getY() - action.argument);
                case 'S' -> waypoint.setY(waypoint.getY() + action.argument);
                case 'E' -> waypoint.setX(waypoint.getX() + action.argument);
                case 'W' -> waypoint.setX(waypoint.getX() - action.argument);
                case 'L', 'R' -> {
                    dX = Math.abs(waypoint.getX() - position.getX());
                    dY = Math.abs(waypoint.getY() - position.getY());
                    if (action.instruction == 'L' && action.argument == 90 ||
                            action.instruction == 'R' && action.argument == 270) {
                        if (inUpperRightQuadrant(position, waypoint)) {
                            waypoint.setX(position.getX() - dY);
                            waypoint.setY(position.getY() - dX);
                        } else if (inUpperLeftQuadrant(position, waypoint)) {
                            waypoint.setX(position.getX() - dY);
                            waypoint.setY(position.getY() + dX);
                        } else if (inLowerLeftQuadrant(position, waypoint)) {
                            waypoint.setX(position.getX() + dY);
                            waypoint.setY(position.getY() + dX);
                        } else if (inLowerRightQuadrant(position, waypoint)) {
                            waypoint.setX(position.getX() + dY);
                            waypoint.setY(position.getY() - dX);
                        } else {
                            log.error("oopsie");
                        }

                    } else if (action.instruction == 'R' && action.argument == 90 ||
                            action.instruction == 'L' && action.argument == 270) {
                        if (inUpperRightQuadrant(position, waypoint)) {
                            waypoint.setX(position.getX() + dY);
                            waypoint.setY(position.getY() + dX);
                        } else if (inUpperLeftQuadrant(position, waypoint)) {
                            waypoint.setX(position.getX() + dY);
                            waypoint.setY(position.getY() - dX);
                        } else if (inLowerLeftQuadrant(position, waypoint)) {
                            waypoint.setX(position.getX() - dY);
                            waypoint.setY(position.getY() - dX);
                        } else if (inLowerRightQuadrant(position, waypoint)) {
                            waypoint.setX(position.getX() - dY);
                            waypoint.setY(position.getY() + dX);
                        } else {
                            log.error("oopsie");
                        }

                    } else if (action.argument == 180) {
                        if (inUpperRightQuadrant(position, waypoint)) {
                            waypoint.setX(position.getX() - dX);
                            waypoint.setY(position.getY() + dY);
                        } else if (inUpperLeftQuadrant(position, waypoint)) {
                            waypoint.setX(position.getX() + dX);
                            waypoint.setY(position.getY() + dY);
                        } else if (inLowerLeftQuadrant(position, waypoint)) {
                            waypoint.setX(position.getX() + dX);
                            waypoint.setY(position.getY() - dY);
                        } else if (inLowerRightQuadrant(position, waypoint)) {
                            waypoint.setX(position.getX() - dX);
                            waypoint.setY(position.getY() - dY);
                        } else {
                            log.error("oopsie");
                        }

                    } else {
                        log.error("Ooops");
                    }
                }
                case 'F' -> {
                    dX = Math.abs(waypoint.getX() - position.getX());
                    if (waypoint.getX() < position.getX()) {
                        dX = -dX;
                    }
                    dY = Math.abs(waypoint.getY() - position.getY());
                    if (waypoint.getY() < position.getY()) {
                        dY = -dY;
                    }
                    for (int i = 0; i < action.argument; i++) {
                        position.setX(position.getX() + dX);
                        position.setY(position.getY() + dY);
                    }
                    waypoint.setX(position.getX() + dX);
                    waypoint.setY(position.getY() + dY);
                }
            }
        }
        return position.distance(new Position(0, 0));

    }

    boolean inUpperRightQuadrant(Position p, Position w) {
        return w.getX() >= p.getX() && w.getY() <= p.getY();
    }

    boolean inUpperLeftQuadrant(Position p, Position w) {
        return w.getX() <= p.getX() && w.getY() <= p.getY();
    }

    boolean inLowerLeftQuadrant(Position p, Position w) {
        return w.getX() <= p.getX() && w.getY() >= p.getY();
    }

    boolean inLowerRightQuadrant(Position p, Position w) {
        return w.getX() >= p.getX() && w.getY() >= p.getY();
    }

    record Action(char instruction, int argument) {
    }
}

