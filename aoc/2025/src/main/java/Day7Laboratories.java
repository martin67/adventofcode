import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import static aoc.common.Direction.Down;
import static aoc.common.Direction.Left;

@Slf4j
class Day7Laboratories {
    Set<Position> map = new HashSet<>();
    Position start;
    int height;

    public Day7Laboratories(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == '^') {
                    map.add(new Position(x, y));
                } else if (c == 'S') {
                    start = new Position(x, y);
                }
                x++;
            }
            y++;
        }
        height = y;
    }

    public int problem1() {
        Set<Position> beams = new HashSet<>();
        beams.add(start.adjacent(Down));
        int splits = 0;

        for (int row = 1; row < height; row++) {
            Set<Position> newBeams = new HashSet<>();
            for (var beam : beams) {
                Position down = beam.adjacent(Down);
                if (map.contains(down)) {
                    newBeams.addAll(List.of(down.adjacent(Left), down.adjacent(Direction.Right)));
                    splits++;
                } else {
                    newBeams.add(down);
                }
            }
            beams = newBeams;
        }
        return splits;
    }

    public int problem2() {
        Set<Position> beams = new HashSet<>();
        Set<Position> allBeams = new HashSet<>();

        beams.add(start.adjacent(Down));
        allBeams.add(start.adjacent(Down));
        int splits = 0;

        for (int row = 1; row < height; row++) {
            Set<Position> newBeams = new HashSet<>();
            for (var beam : beams) {
                Position down = beam.adjacent(Down);
                if (map.contains(down)) {
                    newBeams.addAll(List.of(down.adjacent(Left), down.adjacent(Direction.Right)));
                    allBeams.addAll(List.of(down.adjacent(Left), down.adjacent(Direction.Right)));
                    splits++;
                } else {
                    newBeams.add(down);
                    allBeams.add(down);
                }
            }
            beams = newBeams;
        }
        Position.printMap(allBeams);
        for (int y = 0; y < height; y++) {
            if (y % 2 == 0) {
                // remove all beams in this row
                int finalY = y;
                allBeams.removeIf(p -> p.getY() == finalY);
            }
        }
        // remove first and last row
        allBeams.removeIf(p -> p.getY() == 1 || p.getY() == height - 1);

        System.out.println();
        Position.printMap(allBeams);

        return allBeams.size();
    }
}

// 3125 too low