package aoc.aoc2015;

import java.util.HashSet;
import java.util.Set;

import aoc.common.Direction;
import aoc.common.Position;

public class Day3PerfectlySphericalHouseInAVacuum {
    public int atLeastOnePresent(String line) {
        Set<Position> housesVisited = new HashSet<>();
        Position position = new Position(0, 0);
        housesVisited.add(position);

        for (char c : line.toCharArray()) {
            switch (c) {
                case '^' -> position = position.adjacent(Direction.Up);
                case '>' -> position = position.adjacent(Direction.Right);
                case 'v' -> position = position.adjacent(Direction.Down);
                case '<' -> position = position.adjacent(Direction.Left);
            }
            housesVisited.add(position);
        }
        return housesVisited.size();
    }

    public int roboSanta(String line) {
        Set<Position> housesVisited = new HashSet<>();
        Position santaPosition = new Position(0, 0);
        Position robotPosition = new Position(0, 0);
        Position position;
        housesVisited.add(santaPosition);
        boolean santasTurn = true;

        for (char c : line.toCharArray()) {
            position = (santasTurn) ? santaPosition : robotPosition;

            position = switch (c) {
                case '^' -> position.adjacent(Direction.Up);
                case '>' -> position.adjacent(Direction.Right);
                case 'v' -> position.adjacent(Direction.Down);
                case '<' -> position.adjacent(Direction.Left);
                default -> position;
            };
            housesVisited.add(position);
            if (santasTurn) {
                santaPosition = position;
                santasTurn = false;
            } else {
                robotPosition = position;
                santasTurn = true;
            }
        }
        return housesVisited.size();
    }
}
