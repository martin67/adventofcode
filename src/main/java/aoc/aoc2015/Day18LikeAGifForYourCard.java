package aoc.aoc2015;

import aoc.Position;

import java.util.*;

public class Day18LikeAGifForYourCard {

    Map<Position, Boolean> lights = new HashMap<>();
    Position size = new Position();

    public Day18LikeAGifForYourCard(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                lights.put(new Position(x, y), c == '#');
                x++;
            }
            y++;
            size.setX(x - 1);
        }
        size.setY(y - 1);
    }

    long numberOfLights(int steps, boolean setCorners) {
        Set<Position> corners = new HashSet<>();
        if (setCorners) {
            corners.add(new Position(0, 0));
            corners.add(new Position(0, size.getY()));
            corners.add(new Position(size.getX(), 0));
            corners.add(new Position(size.getX(), size.getY()));
            lights.entrySet().stream()
                    .filter(e -> corners.contains(e.getKey()))
                    .forEach(e -> e.setValue(true));
        }

        for (int i = 0; i < steps; i++) {
            Map<Position, Boolean> nextIteration = new HashMap<>();
            for (Map.Entry<Position, Boolean> entry : lights.entrySet()) {
                Set<Position> allAdjacent = entry.getKey().allAdjacentIncludingDiagonal();
                int adjacentOn = 0;
                for (Position p : allAdjacent) {
                    if (lights.containsKey(p) && lights.get(p)) {
                        adjacentOn++;
                    }
                }

                if (entry.getValue()) {
                    nextIteration.put(entry.getKey(), adjacentOn == 2 || adjacentOn == 3);
                } else {
                    nextIteration.put(entry.getKey(), adjacentOn == 3);
                }
            }
            lights = nextIteration;

            if (setCorners) {
                lights.entrySet().stream()
                        .filter(e -> corners.contains(e.getKey()))
                        .forEach(e -> e.setValue(true));
            }
        }
        return lights.entrySet().stream().filter(Map.Entry::getValue).count();
    }
}