package aoc.aoc2020;

import aoc.common.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3TobogganTrajectory {
    final Set<Position> trees = new HashSet<>();
    final int width;
    final int height;

    Day3TobogganTrajectory(List<String> inputLines) {
        int x = 0;
        int y = 0;

        for (String line : inputLines) {
            x = 0;
            for (char c : line.toCharArray()) {
                if (c == '#') {
                    trees.add(new Position(x, y));
                }
                x++;
            }
            y++;
        }
        width = x;
        height = y;
    }

    int problem1(int right, int down) {
        var position = new Position(0, 0);
        int numberOfTrees = 0;

        while (position.getY() < height) {
            if (trees.contains(position)) {
                numberOfTrees++;
            }
            position.setY(position.getY() + down);
            position.setX(position.getX() + right);
            if (position.getX() >= width) {
                position.setX(position.getX() - width);
            }
        }

        return numberOfTrees;
    }

    long problem2() {
        long numberOfTrees;

        numberOfTrees = problem1(1, 1);
        numberOfTrees *= problem1(3, 1);
        numberOfTrees *= problem1(5, 1);
        numberOfTrees *= problem1(7, 1);
        numberOfTrees *= problem1(1, 2);

        return numberOfTrees;
    }
}

