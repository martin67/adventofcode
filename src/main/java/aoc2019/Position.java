package aoc2019;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

enum Direction {North, South, East, West, Up, Right, Down, Left, NorthEast, NorthWest, SouthEast, SouthWest, Unknown}

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

    Direction opposite(Direction direction) {
        Direction result;
        switch (direction) {
            case North:
                result = Direction.South;
                break;
            case Up:
                result = Direction.Down;
                break;
            case East:
                result = Direction.West;
                break;
            case Right:
                result = Direction.Left;
                break;
            case South:
                result = Direction.North;
                break;
            case Down:
                result = Direction.Up;
                break;
            case West:
                result = Direction.East;
                break;
            case Left:
                result = Direction.Right;
                break;
            case NorthEast:
                result = Direction.SouthWest;
                break;
            case NorthWest:
                result = Direction.SouthEast;
                break;
            case SouthEast:
                result = Direction.NorthWest;
                break;
            case SouthWest:
                result = Direction.NorthEast;
                break;
            default:
                result = Direction.Unknown;
                break;
        }
        return result;
    }

    Direction bounce(Direction direction) {
        Direction result;
        switch (direction) {
            case NorthEast:
                result = Direction.NorthWest;
                break;
            case NorthWest:
                result = Direction.NorthEast;
                break;
            case SouthEast:
                result = Direction.SouthWest;
                break;
            case SouthWest:
                result = Direction.SouthEast;
                break;
            default:
                result = Direction.Unknown;
                break;
        }
        return result;
    }

    Direction directionTo(Position position) {
        if (position.x > x && position.y > y) {
            return Direction.SouthEast;
        } else if (position.x > x && position.y < y) {
            return Direction.NorthEast;
        } else if (position.x < x && position.y > y) {
            return Direction.SouthWest;
        } else if (position.x < x && position.y < y) {
            return Direction.NorthEast;
        } else {
            return Direction.Unknown;
        }
    }

    Position adjacent(char direction) {
        Position adj = new Position(x, y);
        switch (direction) {
            case 'N':
            case 'U':
                adj.y--;
                break;
            case 'E':
            case 'R':
                adj.x++;
                break;
            case 'S':
            case 'D':
                adj.y++;
                break;
            case 'W':
            case 'L':
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