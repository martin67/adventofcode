package aoc.aoc2023;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day21StepCounter {

    Set<Position> map = new HashSet<>();
    Position start;
    int height;
    int width;

    Day21StepCounter(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == '#') {
                    map.add(new Position(x, y));
                } else if (c == 'S') {
                    start = new Position(x, y);
                }
                x++;
                width = x;
            }
            y++;
        }
        height = y;
    }

    int problem1(int steps) {
        Set<Position> currentPositions = new HashSet<>();
        Set<Position> nextPositions = new HashSet<>();
        currentPositions.add(start);

        for (int i = 0; i < steps; i++) {
            for (var position : currentPositions) {
                for (var adjacent : position.allAdjacent()) {
                    if (!map.contains(adjacent) && adjacent.insideSquare(width, height)) {
                        nextPositions.add(adjacent);
                    }
                }
            }
            currentPositions = new HashSet<>(nextPositions);
            nextPositions.clear();
        }
        return currentPositions.size();
    }

    int problem2(int steps) {
        return 0;
    }
}
