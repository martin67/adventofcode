package aoc.aoc2022;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Day24BlizzardBasin {

    final Set<Blizzard> map = new HashSet<>();
    int width;
    final int height;

    Day24BlizzardBasin(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                var position = new Position(x, y);
                switch (c) {
                    case '>' -> map.add(new Blizzard(position, Direction.East));
                    case '<' -> map.add(new Blizzard(position, Direction.West));
                    case '^' -> map.add(new Blizzard(position, Direction.North));
                    case 'v' -> map.add(new Blizzard(position, Direction.South));
                }
                x++;
            }
            width = x;
            y++;
        }
        height = y;
        log.info("Height: {}, width: {}", height, width);
    }

    int problem1() {
        Position.printMap(map.stream().map(b -> b.position).collect(Collectors.toSet()), "Start");

        for (int i = 0; i < 5; i++) {
            for (var blizzard : map) {
                blizzard.move();
            }
            Position.printMap(map.stream().map(b -> b.position).collect(Collectors.toSet()), "Round " + i);
        }
        return 0;
    }

    int problem2() {
        return 0;
    }

    class Blizzard {
        Position position;
        final Direction direction;

        public Blizzard(Position position, Direction direction) {
            this.position = position;
            this.direction = direction;
        }

        void move() {
            position = position.adjacent(direction);
            if (position.getX() < 1) {
                position.setX(width - 2);
            }
            if (position.getX() > width - 2) {
                position.setX(1);
            }
            if (position.getY() < 1) {
                position.setY(height - 2);
            }
            if (position.getY() > height - 2) {
                position.setY(1);
            }
        }
    }
}
