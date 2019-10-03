package adventofcode2016;

import javafx.geometry.Pos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day1NoTimeForATaxicab {

    enum Direction {N, S, E, W}

    private List<String> instructions = new ArrayList<>();
    private List<Position> positionsVisited = new ArrayList<>();

    public Day1NoTimeForATaxicab(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        for (String row : inputStrings) {
            instructions.addAll(Arrays.asList(row.split("\\s*,\\s*")));
        }
    }

    int shortestPath() {
        Position pos = new Position(0, 0);
        Direction currentDirection = Direction.N;

        for (String instruction : instructions) {
            char turn = instruction.charAt(0);
            int distance = Integer.parseInt(instruction.substring(1));
            switch (currentDirection) {
                case N:
                    currentDirection = (turn == 'R') ? Direction.E : Direction.W;
                    break;
                case E:
                    currentDirection = (turn == 'R') ? Direction.S : Direction.N;
                    break;
                case S:
                    currentDirection = (turn == 'R') ? Direction.W : Direction.E;
                    break;
                case W:
                    currentDirection = (turn == 'R') ? Direction.N : Direction.S;
                    break;
            }
            switch (currentDirection) {
                case N:
                    pos.y -= distance;
                    break;
                case E:
                    pos.x += distance;
                    break;
                case S:
                    pos.y += distance;
                    break;
                case W:
                    pos.x -= distance;
                    break;
            }
        }
        return pos.distance(new Position(0, 0));
    }

    int visitedTwice() {
        return 0;
    }
}
