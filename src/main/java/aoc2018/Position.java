package aoc2018;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class Position implements Comparable<Position> {
    int x;
    int y;

    @Override
    public int compareTo(Position p) {
        if (y < p.y) {
            return -1;
        } else if (y > p.y) {
            return 1;
        } else return Integer.compare(x, p.x);
    }

    int distance(Position p) {
        return Math.abs(x - p.x) + Math.abs(y - p.y);
    }

    Set<Position> adjacent() {
        Set<Position> adjacent = new HashSet<>();
        adjacent.add(new Position(x, y - 1)); // up
        adjacent.add(new Position(x - 1, y)); // left
        adjacent.add(new Position(x + 1, y)); // right
        adjacent.add(new Position(x, y + 1)); // down
        return adjacent;
    }
}