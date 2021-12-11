package aoc.aoc2021;

import aoc.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day11DumboOctopus {

    Map<Position, Octopus> grid = new HashMap<>();
    int xSize;
    int ySize;

    public Day11DumboOctopus(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                Position p = new Position(x, y);
                grid.put(p, new Octopus(p, Integer.parseInt(String.valueOf(c))));
                x++;
            }
            xSize = x;
            y++;
        }
        ySize = y;
    }

    int problem1() {
        log.info("Before");
        printGrid();

        for (int i = 0; i < 100; i++) {
            grid.values().forEach(o -> o.energyLevel++);
            grid.keySet().forEach(p -> grid.get(p).checkFlash());
            grid.values().forEach(o -> o.flashed = false);
        }

        return grid.values().stream().mapToInt(o -> o.numberOfFlashes++).sum();
    }

    int problem2() {
        boolean allFlashed = false;
        int steps = 0;
        int finalStep = 0;

        while (!allFlashed) {
            grid.values().forEach(o -> o.energyLevel++);
            grid.keySet().forEach(p -> grid.get(p).checkFlash());

            // count flashes
            long flashes = grid.values().stream().filter(o -> o.flashed).count();

            if (flashes == grid.size()) {
                allFlashed = true;
                finalStep = steps + 1;
            }

            grid.values().forEach(o -> o.flashed = false);
            steps++;
        }
        return finalStep;
    }

    void printGrid() {
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                System.out.print(grid.get(new Position(x, y)).energyLevel);
            }
            System.out.println();
        }
        System.out.println();
    }

    class Octopus {
        Position position;
        int energyLevel;
        int numberOfFlashes;
        boolean flashed;

        public Octopus(Position position, int energyLevel) {
            this.position = position;
            this.energyLevel = energyLevel;
        }

        void checkFlash() {
            if (!flashed) {
                if (energyLevel > 9) {
                    numberOfFlashes++;
                    energyLevel = 0;
                    flashed = true;
                    for (Position adjacent : position.allAdjacentIncludingDiagonal()) {
                        if (grid.containsKey(adjacent)) {
                            Octopus adjacentOctopus = grid.get(adjacent);
                            if (!adjacentOctopus.flashed) {
                                adjacentOctopus.energyLevel++;
                            }
                            adjacentOctopus.checkFlash();
                        }
                    }
                }
            }
        }
    }
}
