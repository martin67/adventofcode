package aoc.aoc2016;

import aoc.common.Direction;
import aoc.common.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day18LikeARogue {
    private Map<Position, Character> lastRow = new HashMap<>();
    private int width;
    private int safeTiles;

    public Day18LikeARogue(List<String> inputLines) {
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                lastRow.put(new Position(x, 0), c);
                if (c == '.') {
                    safeTiles++;
                }
                x++;
            }
            width = x;
        }
    }

    long safeTiles(int rows) {
        for (int y = 1; y < rows; y++) {
            Map<Position, Character> nextRow = new HashMap<>();
            for (int x = 0; x < width; x++) {
                Position p = new Position(x, y);
                char c = computeTile(p);
                if (c == '.') {
                    safeTiles++;
                }
                nextRow.put(p, c);
            }
            lastRow = nextRow;
        }
        return safeTiles;
    }

    char computeTile(Position pos) {
        char result;
        char left = lastRow.getOrDefault(pos.adjacent(Direction.NorthWest), '.');
        char center = lastRow.get(pos.adjacent(Direction.North));
        char right = lastRow.getOrDefault(pos.adjacent(Direction.NorthEast), '.');

        if ((left == '^' && center == '^' && right == '.') ||
                (center == '^' && right == '^' && left == '.') ||
                (left == '^' && center == '.' && right == '.') ||
                (right == '^' && center == '.' && left == '.')) {
            result = '^';
        } else {
            result = '.';
        }
        return result;
    }
}
