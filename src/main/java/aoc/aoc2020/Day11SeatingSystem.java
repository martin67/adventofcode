package aoc.aoc2020;

import aoc.Direction;
import aoc.Position;

import java.util.*;

public class Day11SeatingSystem {

    Map<Position, Character> seats = new HashMap<>();
    int width;
    int height;

    public Day11SeatingSystem(List<String> inputLines) {
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

    long seatsOccupied() {
        Map<Position, Character> newSeats = new HashMap<>();
        long previousNumberOfOccupiedSeats = -1;
        long occupiedSeats = 0;

        while (occupiedSeats != previousNumberOfOccupiedSeats) {
            previousNumberOfOccupiedSeats = occupiedSeats;

            for (Position pos : seats.keySet()) {
                Set<Position> adjacentSeats = pos.allAdjacentIncludingDiagonal();
                int occupied = 0;
                for (Position adj : adjacentSeats) {
                    if (seats.containsKey(adj) && seats.get(adj) == '#') {
                        occupied++;
                    }
                }
                if (seats.get(pos) == 'L' && occupied == 0) {
                    newSeats.put(pos, '#');
                } else if (seats.get(pos) == '#' && occupied >= 4) {
                    newSeats.put(pos, 'L');
                } else {
                    newSeats.put(pos, seats.get(pos));
                }
            }
            for (Position pos : newSeats.keySet()) {
                seats.put(pos, newSeats.get(pos));
            }
            //printSeats();
            occupiedSeats = seats.values().stream().filter(c -> c == '#').count();
        }
        return occupiedSeats;
    }

    long seatsOccupied2() {
        Map<Position, Character> newSeats = new HashMap<>();
        long previousNumberOfOccupiedSeats = -1;
        long occupiedSeats = 0;

        while (occupiedSeats != previousNumberOfOccupiedSeats) {
            previousNumberOfOccupiedSeats = occupiedSeats;

            for (Position pos : seats.keySet()) {

                Set<Position> adjacentSeats = pos.allAdjacentIncludingDiagonal();
                int occupied = 0;
                for (Position adj : adjacentSeats) {
                    Direction dir = pos.directionTo(adj, true);
                    Position p2 = adj;

                    while (p2.insideSquare(width, height)) {
                        if (seats.containsKey(p2)) {
                            if (seats.get(p2) == '#') {
                                occupied++;
                            }
                            break;
                        } else {
                            p2 = p2.adjacent(dir);
                        }
                    }
                }

                if (seats.get(pos) == 'L' && occupied == 0) {
                    newSeats.put(pos, '#');
                } else if (seats.get(pos) == '#' && occupied >= 5) {
                    newSeats.put(pos, 'L');
                } else {
                    newSeats.put(pos, seats.get(pos));
                }
            }
            for (Position pos : newSeats.keySet()) {
                seats.put(pos, newSeats.get(pos));
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
                Position p = new Position(x, y);
                if (seats.containsKey(p)) {
                    sb.append(seats.get(p));
                } else {
                    sb.append('.');
                }
            }
            System.out.println(sb.toString());
        }
        System.out.println();
    }
}
