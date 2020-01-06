package aoc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position implements Comparable<Position> {
    int x;
    int y;

    public Position(Position p) {
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

    public int distance(Position p) {
        return Math.abs(x - p.x) + Math.abs(y - p.y);
    }

    public Position adjacent(Direction direction) {
        Position adj = new Position(x, y);
        switch (direction) {
            case North:
            case Up:
                adj.y--;
                break;
            case East:
            case Right:
                adj.x++;
                break;
            case South:
            case Down:
                adj.y++;
                break;
            case West:
            case Left:
                adj.x--;
                break;
            case NorthEast:
                adj.x++;
                adj.y--;
                break;
            case NorthWest:
                adj.x--;
                adj.y--;
                break;
            case SouthEast:
                adj.x++;
                adj.y++;
                break;
            case SouthWest:
                adj.x--;
                adj.y++;
                break;
        }
        return adj;
    }

    public Direction directionTo(Position position, boolean allowDiagonal) {
        if (position.x == x && position.y < y) {
            return Direction.North;
        } else if (allowDiagonal && position.x > x && position.y < y) {
            return Direction.NorthEast;
        } else if (position.x > x && position.y == y) {
            return Direction.East;
        } else if (allowDiagonal && position.x > x) {
            return Direction.SouthEast;
        } else if (position.x == x && position.y > y) {
            return Direction.South;
        } else if (allowDiagonal && position.x < x && position.y > y) {
            return Direction.SouthWest;
        } else if (position.x < x && position.y == y) {
            return Direction.West;
        } else if (allowDiagonal && position.x < x) {
            return Direction.NorthWest;
        } else {
            return Direction.Unknown;
        }
    }

    public Set<Position> allAdjacent() {
        Set<Position> adjacent = new HashSet<>();
        adjacent.add(new Position(x, y - 1)); // up or north
        adjacent.add(new Position(x - 1, y)); // left or west
        adjacent.add(new Position(x + 1, y)); // right or east
        adjacent.add(new Position(x, y + 1)); // down or south
        return adjacent;
    }

    public Set<Position> adjacentDiagonal(Direction dir) {
        Set<Position> adjacent = new HashSet<>();
        switch (dir) {
            case NorthEast:
                adjacent.add(new Position(this.adjacent(Direction.North)));
                adjacent.add(new Position(this.adjacent(Direction.East)));
                break;
            case NorthWest:
                adjacent.add(new Position(this.adjacent(Direction.North)));
                adjacent.add(new Position(this.adjacent(Direction.West)));
                break;
            case SouthEast:
                adjacent.add(new Position(this.adjacent(Direction.South)));
                adjacent.add(new Position(this.adjacent(Direction.East)));
                break;
            case SouthWest:
                adjacent.add(new Position(this.adjacent(Direction.South)));
                adjacent.add(new Position(this.adjacent(Direction.West)));
                break;
            default:
                log.error("Not a diagonal direction");
                break;
        }
        return adjacent;
    }
}