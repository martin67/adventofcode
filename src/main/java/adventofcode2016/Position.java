package adventofcode2016;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

enum Direction {North, South, East, West}

@Data
@AllArgsConstructor
public class Position implements Comparable<Position> {
    int x;
    int y;

    Position(Position p) {
        x = p.x;
        y = p.y;
    }

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

    Position adjacent(Direction direction) {
        Position adj = new Position(x, y);
        switch (direction) {
            case North:
                adj.y--;
                break;
            case East:
                adj.x++;
                break;
            case South:
                adj.y++;
                break;
            case West:
                adj.x--;
                break;
        }
        return adj;
    }

    Set<Position> allAdjacent() {
        Set<Position> adjacent = new HashSet<>();
        adjacent.add(new Position(x, y - 1)); // up or north
        adjacent.add(new Position(x - 1, y)); // left or west
        adjacent.add(new Position(x + 1, y)); // right or east
        adjacent.add(new Position(x, y + 1)); // down or south
        return adjacent;
    }
}