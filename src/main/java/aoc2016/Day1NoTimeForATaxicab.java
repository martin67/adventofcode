package aoc2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day1NoTimeForATaxicab {

    private List<String> instructions = new ArrayList<>();
    private Set<Position> positionsVisited = new HashSet<>();
    private int distanceToVisitTwice = 0;

    public Day1NoTimeForATaxicab(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        for (String row : inputStrings) {
            instructions.addAll(Arrays.asList(row.split("\\s*,\\s*")));
        }
    }

    private Direction turnDirection(Direction currentDirection, char turn) {
        switch (currentDirection) {
            case North:
                currentDirection = (turn == 'R') ? Direction.East : Direction.West;
                break;
            case East:
                currentDirection = (turn == 'R') ? Direction.South : Direction.North;
                break;
            case South:
                currentDirection = (turn == 'R') ? Direction.West : Direction.East;
                break;
            case West:
                currentDirection = (turn == 'R') ? Direction.North : Direction.South;
                break;
        }
        return currentDirection;
    }

    int shortestPath() {
        Position pos = new Position(0, 0);
        Direction currentDirection = Direction.North;

        for (String instruction : instructions) {
            char turn = instruction.charAt(0);
            int distance = Integer.parseInt(instruction.substring(1));
            currentDirection = turnDirection(currentDirection, turn);

            switch (currentDirection) {
                case North:
                    pos.y -= distance;
                    break;
                case East:
                    pos.x += distance;
                    break;
                case South:
                    pos.y += distance;
                    break;
                case West:
                    pos.x -= distance;
                    break;
            }
        }
        return pos.distance(new Position(0, 0));
    }

    private Position walkUntilSecondVisit(Position startPosition, Direction walkingDirection, int distance) {
        Position current = startPosition;
        for (int i = 0; i < distance; i++) {
            Position next = current.adjacent(walkingDirection);
            if (positionsVisited.contains(next)) {
                distanceToVisitTwice = next.distance(new Position(0, 0));
                return null;
            } else {
                positionsVisited.add(next);
                current = next;
            }
        }
        return current;
    }

    int visitedTwice() {
        Position currentPosition = new Position(0, 0);
        positionsVisited.add(currentPosition);
        Direction currentDirection = Direction.North;

        for (String instruction : instructions) {
            char turn = instruction.charAt(0);
            int distance = Integer.parseInt(instruction.substring(1));
            currentDirection = turnDirection(currentDirection, turn);

            currentPosition = walkUntilSecondVisit(currentPosition, currentDirection, distance);
            if (currentPosition == null) {
                return distanceToVisitTwice;
            }
        }
        return 0;
    }

}
