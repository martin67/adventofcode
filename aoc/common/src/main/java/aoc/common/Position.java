package aoc.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Data
public class Position implements Comparable<Position> {
    private int x;
    private int y;

    public Position() {
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position p) {
        this.x = p.x;
        this.y = p.y;
    }

    public static void printMap(Set<Position> map) {
        printMap(map, null, false);
    }

    public static void printMap(Set<Position> map, String msg) {
        printMap(map, msg, false);
    }

    public static void printMap(Set<Position> map, boolean coordinates) {
        printMap(map, null, coordinates);
    }

    public static void printMap(Set<Position> map, String msg, boolean coordinates) {
        int xMin = map.stream().mapToInt(Position::getX).min().orElseThrow(NoSuchElementException::new);
        int xMax = map.stream().mapToInt(Position::getX).max().orElseThrow(NoSuchElementException::new);
        int yMin = map.stream().mapToInt(Position::getY).min().orElseThrow(NoSuchElementException::new);
        int yMax = map.stream().mapToInt(Position::getY).max().orElseThrow(NoSuchElementException::new);

        if (msg != null) {
            System.out.println(msg);
        }
        if (coordinates) {
            System.out.printf("Upper left <%d,%d>, lower right: <%d,%d>\n", xMin, yMin, xMax, yMax);
        }
        for (int y = yMin; y < yMax + 1; y++) {
            for (int x = xMin; x < xMax + 1; x++) {
                if (map.contains(new Position(x, y))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int getXMax(Set<Position> map) {
        return map.stream().mapToInt(Position::getX).max().orElseThrow();
    }

    public static int getYMax(Set<Position> map) {
        return map.stream().mapToInt(Position::getY).max().orElseThrow();
    }

    public static int getXMin(Set<Position> map) {
        return map.stream().mapToInt(Position::getX).min().orElseThrow();
    }

    public static int getYMin(Set<Position> map) {
        return map.stream().mapToInt(Position::getY).min().orElseThrow();
    }

    public static int getHeight(Set<Position> map) {
        return getYMax(map) - getYMin(map) + 1;
    }

    public static int getWidth(Set<Position> map) {
        return getXMax(map) - getXMin(map) + 1;
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
            case North, Up -> adj.y--;
            case East, Right -> adj.x++;
            case South, Down -> adj.y++;
            case West, Left -> adj.x--;
            case NorthEast -> {
                adj.x++;
                adj.y--;
            }
            case NorthWest -> {
                adj.x--;
                adj.y--;
            }
            case SouthEast -> {
                adj.x++;
                adj.y++;
            }
            case SouthWest -> {
                adj.x--;
                adj.y++;
            }
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
        adjacent.add(adjacent(Direction.North));
        adjacent.add(adjacent(Direction.South));
        adjacent.add(adjacent(Direction.East));
        adjacent.add(adjacent(Direction.West));
        return adjacent;
    }

    public Set<Position> allAdjacentIncludingDiagonal() {
        Set<Position> adjacent = new HashSet<>();
        adjacent.add(adjacent(Direction.North));
        adjacent.add(adjacent(Direction.South));
        adjacent.add(adjacent(Direction.East));
        adjacent.add(adjacent(Direction.West));
        adjacent.add(adjacent(Direction.NorthEast));
        adjacent.add(adjacent(Direction.NorthWest));
        adjacent.add(adjacent(Direction.SouthEast));
        adjacent.add(adjacent(Direction.SouthWest));
        return adjacent;
    }

    public Set<Position> adjacentDiagonal(Direction dir) {
        Set<Position> adjacent = new HashSet<>();
        switch (dir) {
            case NorthEast -> {
                adjacent.add(adjacent(Direction.North));
                adjacent.add(adjacent(Direction.East));
            }
            case NorthWest -> {
                adjacent.add(adjacent(Direction.North));
                adjacent.add(adjacent(Direction.West));
            }
            case SouthEast -> {
                adjacent.add(adjacent(Direction.South));
                adjacent.add(adjacent(Direction.East));
            }
            case SouthWest -> {
                adjacent.add(adjacent(Direction.South));
                adjacent.add(adjacent(Direction.West));
            }
            default -> log.error("Not a diagonal direction");
        }
        return adjacent;
    }

    // create a square with size (2 x distance) + 1 and with the position in the middle
    public Set<Position> square(int distance) {
        Set<Position> result = new HashSet<>();
        for (int y = -distance; y < distance + 1; y++) {
            for (int x = -distance; x < distance + 1; x++) {
                result.add(new Position(this.x + x, this.y + y));
            }
        }
        return result;
    }

    public boolean insideSquare(int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

