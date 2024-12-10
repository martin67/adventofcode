package aoc.aoc2024;

import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day10HoofIt {

    Map<Position, Integer> map = new HashMap<>();
    Set<Position> startPositions;

    Day10HoofIt(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                map.put(new Position(x, y), (c == '.') ? -1 : Character.getNumericValue(c));
                x++;
            }
            y++;
        }
        startPositions = map.entrySet().stream()
                .filter(e -> e.getValue() == 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }


    int problem1() {
        return startPositions.stream().mapToInt(this::findTrails).sum();
    }

    int problem2() {
        return startPositions.stream().mapToInt(this::findDistinctTrails).sum();
    }

    int findTrails(Position startPosition) {
        Set<Position> currentPositions = new HashSet<>();
        currentPositions.add(startPosition);
        int size = 0;
        boolean done = false;
        while (!done) {
            Set<Position> nextPositions = new HashSet<>();
            for (Position currentPosition : currentPositions) {
                for (Position p : currentPosition.allAdjacent()) {
                    if (map.getOrDefault(p, 0) == map.get(currentPosition) + 1) {
                        nextPositions.add(p);
                    }
                    if (map.getOrDefault(p, 0) == 9 && map.get(currentPosition) == 8) {
                        done = true;
                    }
                }
            }
            currentPositions = nextPositions;
            if (done) {
                size += nextPositions.size();
            }
        }
        return size;
    }

    int findDistinctTrails(Position startPosition) {
        int current = map.get(startPosition);
        int sum = 0;
        if (current == 9) {
            return 1;
        }

        for (Position p : startPosition.allAdjacent()) {
            if (map.getOrDefault(p, 0) == current + 1) {
                sum += findDistinctTrails(p);
            }
        }
        return sum;
    }
}