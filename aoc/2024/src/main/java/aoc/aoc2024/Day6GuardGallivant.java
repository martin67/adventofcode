package aoc.aoc2024;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day6GuardGallivant {
    Set<Position> map = new HashSet<>();
    Position start;
    int width;
    int height;

    Day6GuardGallivant(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == '#') {
                    map.add(new Position(x, y));
                } else if (c == '^') {
                    start = new Position(x, y);
                }
                x++;
            }
            y++;
        }
        width = Position.getWidth(map);
        height = Position.getHeight(map);
    }

    int problem1() {
        return visit(start, Direction.Up).size();
    }

    int problem2() {
        Set<Position> visited = visit(start, Direction.Up);

        int loops = 0;
        for (Position p : visited) {
            Set<Position> newMap = new HashSet<>(map);
            newMap.add(p);
            if (isLoop(newMap)) {
                loops++;
            }
        }
        return loops;
    }

    Set<Position> visit(Position pos, Direction direction) {
        Set<Position> visited = new HashSet<>();

        while (pos.getY() > 0 && pos.getY() < height && pos.getX() > 0 && pos.getX() < width) {
            Position nextPos = pos.adjacent(direction);
            if (map.contains(nextPos)) {
                direction = direction.turn(Direction.Right);
            } else {
                visited.add(pos);
                pos = nextPos;
            }
        }
        return visited;
    }

    boolean isLoop(Set<Position> newMap) {
        Position pos = start;
        Direction direction = Direction.Up;
        Map<Position, Direction> visited = new HashMap<>();

        while (pos.getY() > 0 && pos.getY() < height && pos.getX() > 0 && pos.getX() < width) {
            Position nextPos = pos.adjacent(direction);
            if (visited.containsKey(pos) && visited.get(pos) == direction) {
                return true;
            }
            if (newMap.contains(nextPos)) {
                direction = direction.turn(Direction.Right);
            } else {
                visited.put(pos, direction);
                pos = nextPos;
            }
        }
        return false;
    }
}
