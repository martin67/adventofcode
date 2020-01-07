package aoc.aoc2015;

import aoc.Direction;
import aoc.Position;

import java.util.HashSet;
import java.util.Set;

public class Day3PerfectlySphericalHouseInAVacuum {
    int atLeastOnePresent(String line) {
        Set<Position> housesVisited = new HashSet<>();
        Position position = new Position(0, 0);
        housesVisited.add(position);

        for (char c : line.toCharArray()) {
            switch (c) {
                case '^':
                    position = position.adjacent(Direction.Up);
                    break;
                case '>':
                    position = position.adjacent(Direction.Right);
                    break;
                case 'v':
                    position = position.adjacent(Direction.Down);
                    break;
                case '<':
                    position = position.adjacent(Direction.Left);
                    break;
            }
            housesVisited.add(position);
        }
        return housesVisited.size();
    }

    int roboSanta(String line) {
        Set<Position> housesVisited = new HashSet<>();
        Position santaPosition = new Position(0, 0);
        Position robotPosition = new Position(0, 0);
        Position position;
        housesVisited.add(santaPosition);
        boolean santasTurn = true;

        for (char c : line.toCharArray()) {
            position = (santasTurn) ? santaPosition : robotPosition;

            switch (c) {
                case '^':
                    position = position.adjacent(Direction.Up);
                    break;
                case '>':
                    position = position.adjacent(Direction.Right);
                    break;
                case 'v':
                    position = position.adjacent(Direction.Down);
                    break;
                case '<':
                    position = position.adjacent(Direction.Left);
                    break;
            }
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
