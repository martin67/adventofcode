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
}
