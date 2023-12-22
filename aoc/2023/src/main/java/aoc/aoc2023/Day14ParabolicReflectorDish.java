package aoc.aoc2023;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day14ParabolicReflectorDish {
    Set<Position> roundedRocks = new HashSet<>();
    Set<Position> cubicRocks = new HashSet<>();

    public Day14ParabolicReflectorDish(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == 'O') {
                    roundedRocks.add(new Position(x, y));
                } else if (c == '#') {
                    cubicRocks.add(new Position(x, y));
                }
                x++;
            }
            y++;
        }
    }

    public int problem1() {
        int height = roundedRocks.stream().mapToInt(Position::getY).max().orElseThrow() + 1;
        int width = roundedRocks.stream().mapToInt(Position::getX).max().orElseThrow() + 1;

        for (int y = 0; y < height; y++) {
            int finalY = y;
            var rocks = roundedRocks.stream().filter(p -> p.getY() == finalY).toList();
            for (var rock : rocks) {

                while (rock.getY() > 0 && !cubicRocks.contains(rock.adjacent(Direction.North)) && !roundedRocks.contains(rock.adjacent(Direction.North))) {
                    rock.setY(rock.getY() - 1);
                }
            }
        }

        Position.printMap(roundedRocks, true);
        int sum = 0;
        for (var rock : roundedRocks) {
            sum += 10 - rock.getY();
        }

        return sum;
    }

    public int problem2() {
        return 0;
    }
}
