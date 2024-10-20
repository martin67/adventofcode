package aoc.aoc2020;

import aoc.common.Position;

import java.util.*;

public class Day11SeatingSystem {
    final Map<Position, Character> seats = new HashMap<>();
    final int width;
    final int height;

    Day11SeatingSystem(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == 'L') {
                    seats.put(new Position(x, y), c);
                }
                x++;
            }
            y++;
        }
        width = seats.keySet()
                .stream()
                .max(Comparator.comparing(Position::getX))
                .orElseThrow(NoSuchElementException::new)
                .getX() + 1;
        height = seats.keySet()
                .stream()
                .max(Comparator.comparing(Position::getY))
                .orElseThrow(NoSuchElementException::new)
                .getY() + 1;
    }

    long problem1() {
        Map<Position, Character> newSeats = new HashMap<>();
        long previousNumberOfOccupiedSeats = -1;
        long occupiedSeats = 0;

        while (occupiedSeats != previousNumberOfOccupiedSeats) {
            previousNumberOfOccupiedSeats = occupiedSeats;

            for (var position : seats.keySet()) {
                Set<Position> adjacentSeats = position.allAdjacentIncludingDiagonal();
                int occupied = 0;
                for (var adjacent : adjacentSeats) {
                    if (seats.containsKey(adjacent) && seats.get(adjacent) == '#') {
                        occupied++;
                    }
                }
                if (seats.get(position) == 'L' && occupied == 0) {
                    newSeats.put(position, '#');
                } else if (seats.get(position) == '#' && occupied >= 4) {
                    newSeats.put(position, 'L');
                } else {
                    newSeats.put(position, seats.get(position));
                }
            }
            for (var position : newSeats.keySet()) {
                seats.put(position, newSeats.get(position));
            }
            //printSeats();
            occupiedSeats = seats.values().stream().filter(c -> c == '#').count();
        }
        return occupiedSeats;
    }

    long problem2() {
        Map<Position, Character> newSeats = new HashMap<>();
        long previousNumberOfOccupiedSeats = -1;
        long occupiedSeats = 0;

        while (occupiedSeats != previousNumberOfOccupiedSeats) {
            previousNumberOfOccupiedSeats = occupiedSeats;

            for (var position : seats.keySet()) {
                int occupied = 0;
                for (var adjacent : position.allAdjacentIncludingDiagonal()) {
                    var direction = position.directionTo(adjacent, true);
                    var p2 = adjacent;

                    while (p2.insideSquare(width, height)) {
                        if (seats.containsKey(p2)) {
                            if (seats.get(p2) == '#') {
                                occupied++;
                            }
                            break;
                        } else {
                            p2 = p2.adjacent(direction);
                        }
                    }
                }

                if (seats.get(position) == 'L' && occupied == 0) {
                    newSeats.put(position, '#');
                } else if (seats.get(position) == '#' && occupied >= 5) {
                    newSeats.put(position, 'L');
                } else {
                    newSeats.put(position, seats.get(position));
                }
            }
            for (var position : newSeats.keySet()) {
                seats.put(position, newSeats.get(position));
            }
            //printSeats();
            occupiedSeats = seats.values().stream().filter(c -> c == '#').count();
        }
        return occupiedSeats;

    }

    void printSeats() {
        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {
                var position = new Position(x, y);
                if (seats.containsKey(position)) {
                    sb.append(seats.get(position));
                } else {
                    sb.append('.');
                }
            }
            System.out.println(sb);
        }
        System.out.println();
    }
}
