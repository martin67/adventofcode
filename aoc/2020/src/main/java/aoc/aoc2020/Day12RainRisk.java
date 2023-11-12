package aoc.aoc2020;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day12RainRisk {
    private final List<Action> actions = new ArrayList<>();

    public Day12RainRisk(List<String> inputLines) {
        for (String line : inputLines) {
            actions.add(new Action(line.charAt(0), Integer.parseInt(line.substring(1))));
        }
    }

    int distance() {
        Position pos = new Position(0, 0);
        Direction dir = Direction.East;

        for (Action action : actions) {
            switch (action.instruction) {
                case 'N' -> pos.setY(pos.getY() - action.argument);
                case 'S' -> pos.setY(pos.getY() + action.argument);
                case 'E' -> pos.setX(pos.getX() + action.argument);
                case 'W' -> pos.setX(pos.getX() - action.argument);
                case 'L' -> dir = dir.turn(Direction.Left, action.argument);
                case 'R' -> dir = dir.turn(Direction.Right, action.argument);
                case 'F' -> {
                    for (int i = 0; i < action.argument; i++) {
                        pos = pos.adjacent(dir);
                    }
                }
            }
        }
        return pos.distance(new Position(0, 0));
    }

    int distanceWithWaypoint() {
        Position pos = new Position(0, 0);
        Position waypoint = new Position(10, -1);
        int dX;
        int dY;

        for (Action action : actions) {
            switch (action.instruction) {
                case 'N' -> waypoint.setY(waypoint.getY() - action.argument);
                case 'S' -> waypoint.setY(waypoint.getY() + action.argument);
                case 'E' -> waypoint.setX(waypoint.getX() + action.argument);
                case 'W' -> waypoint.setX(waypoint.getX() - action.argument);
                case 'L', 'R' -> {
                    dX = Math.abs(waypoint.getX() - pos.getX());
                    dY = Math.abs(waypoint.getY() - pos.getY());
                    if (action.instruction == 'L' && action.argument == 90 ||
                            action.instruction == 'R' && action.argument == 270) {
                        if (inUpperRightQuadrant(pos, waypoint)) {
                            waypoint.setX(pos.getX() - dY);
                            waypoint.setY(pos.getY() - dX);
                        } else if (inUpperLeftQuadrant(pos, waypoint)) {
                            waypoint.setX(pos.getX() - dY);
                            waypoint.setY(pos.getY() + dX);
                        } else if (inLowerLeftQuadrant(pos, waypoint)) {
                            waypoint.setX(pos.getX() + dY);
                            waypoint.setY(pos.getY() + dX);
                        } else if (inLowerRightQuadrant(pos, waypoint)) {
                            waypoint.setX(pos.getX() + dY);
                            waypoint.setY(pos.getY() - dX);
                        } else {
                            log.error("oopsie");
                        }

                    } else if (action.instruction == 'R' && action.argument == 90 ||
                            action.instruction == 'L' && action.argument == 270) {
                        if (inUpperRightQuadrant(pos, waypoint)) {
                            waypoint.setX(pos.getX() + dY);
                            waypoint.setY(pos.getY() + dX);
                        } else if (inUpperLeftQuadrant(pos, waypoint)) {
                            waypoint.setX(pos.getX() + dY);
                            waypoint.setY(pos.getY() - dX);
                        } else if (inLowerLeftQuadrant(pos, waypoint)) {
                            waypoint.setX(pos.getX() - dY);
                            waypoint.setY(pos.getY() - dX);
                        } else if (inLowerRightQuadrant(pos, waypoint)) {
                            waypoint.setX(pos.getX() - dY);
                            waypoint.setY(pos.getY() + dX);
                        } else {
                            log.error("oopsie");
                        }

                    } else if (action.argument == 180) {
                        if (inUpperRightQuadrant(pos, waypoint)) {
                            waypoint.setX(pos.getX() - dX);
                            waypoint.setY(pos.getY() + dY);
                        } else if (inUpperLeftQuadrant(pos, waypoint)) {
                            waypoint.setX(pos.getX() + dX);
                            waypoint.setY(pos.getY() + dY);
                        } else if (inLowerLeftQuadrant(pos, waypoint)) {
                            waypoint.setX(pos.getX() + dX);
                            waypoint.setY(pos.getY() - dY);
                        } else if (inLowerRightQuadrant(pos, waypoint)) {
                            waypoint.setX(pos.getX() - dX);
                            waypoint.setY(pos.getY() - dY);
                        } else {
                            log.error("oopsie");
                        }

                    } else {
                        log.error("Ooops");
                    }
                }
                case 'F' -> {
                    dX = Math.abs(waypoint.getX() - pos.getX());
                    if (waypoint.getX() < pos.getX()) {
                        dX = -dX;
                    }
                    dY = Math.abs(waypoint.getY() - pos.getY());
                    if (waypoint.getY() < pos.getY()) {
                        dY = -dY;
                    }
                    for (int i = 0; i < action.argument; i++) {
                        pos.setX(pos.getX() + dX);
                        pos.setY(pos.getY() + dY);
                    }
                    waypoint.setX(pos.getX() + dX);
                    waypoint.setY(pos.getY() + dY);
                }
            }
        }
        return pos.distance(new Position(0, 0));

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

    static class Action {
        final char instruction;
        final int argument;

        public Action(char instruction, int argument) {
            this.instruction = instruction;
            this.argument = argument;
        }
    }
}

