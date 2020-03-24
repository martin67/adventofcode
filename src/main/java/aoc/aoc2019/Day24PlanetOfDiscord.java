package aoc.aoc2019;

import aoc.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day24PlanetOfDiscord {

    Map<Position, Character> map = new HashMap<>();
    final Set<String> previousMaps = new HashSet<>();
    Map<Integer, Character[][]> grids = new TreeMap<>();

    public Day24PlanetOfDiscord(List<String> inputLines) {
        int x;
        int y = 0;
        Character[][] grid = new Character[5][5];
        grids.put(0, grid);

        for (String line : inputLines) {
            x = 0;
            for (char c : line.toCharArray()) {
                map.put(new Position(x, y), c);
                grid[x][y] = c;
                x++;
            }
            y++;
        }
    }

    String grow() {
        log.info("growing");
        Map<Position, Character> newMap = new HashMap<>();

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Position pos = new Position(x, y);
                int n = 0;
                for (Position p : pos.allAdjacent()) {
                    if (map.containsKey(p) && map.get(p).equals('#')) {
                        n++;
                    }
                }
                // A bug dies (becoming an empty space) unless there is exactly one bug adjacent to it.
                if (map.get(pos).equals('#') && n != 1) {
                    newMap.put(pos, '.');
                } else if (map.get(pos).equals('.') && (n == 1 || n == 2)) {
                    newMap.put(pos, '#');
                } else {
                    newMap.put(pos, map.get(pos));
                }
            }
        }
        map = newMap;
        return map.toString();
    }

    void printMap() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Position pos = new Position(x, y);
                sb.append(map.get(pos));
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    void printGrid(Character[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                sb.append(grid[x][y]);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    void printTwoGrid(Character[][] before, Character[][] after) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (x == 2 && y == 2) {
                    sb.append("?");
                } else {
                    sb.append(before[x][y]);
                }
            }
            sb.append("        ");
            for (int x = 0; x < 5; x++) {
                if (x == 2 && y == 2) {
                    sb.append("?");
                } else {
                    sb.append(after[x][y]);
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    void printGrids(Map<Integer, Character[][]> grids) {
        for (Map.Entry<Integer, Character[][]> gridEntry : grids.entrySet()) {
            System.out.println("Level: " + gridEntry.getKey());
            printGrid(gridEntry.getValue());
            System.out.println();
        }
    }

    void printTwoGrids(Map<Integer, Character[][]> beforeGrids, Map<Integer, Character[][]> afterGrids) {
        for (Map.Entry<Integer, Character[][]> gridEntry : beforeGrids.entrySet()) {
            int level = gridEntry.getKey();
            System.out.println("Level: " + gridEntry.getKey());
            printTwoGrid(beforeGrids.get(level), afterGrids.get(level));
            System.out.println();
        }
    }

    Character[][] newGrid() {
        Character[][] newGrid = new Character[5][5];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                newGrid[x][y] = '.';
            }
        }
        return newGrid;
    }

    int biodiversityRating() {
        printMap();
        String m = grow();
        printMap();
        while (!previousMaps.contains(m)) {
            previousMaps.add(m);
            m = grow();
            printMap();
        }

        log.info("Found duplicate");
        printMap();

        int n = 0;
        int rating = 0;
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Position pos = new Position(x, y);
                if (map.get(pos).equals('#')) {
                    rating += Math.pow(2, n);
                }
                n++;
            }
        }

        return rating;
    }

    Map<Integer, Character[][]> grow(Map<Integer, Character[][]> grids) {
        Map<Integer, Character[][]> newGrids = new TreeMap<>();
        for (Map.Entry<Integer, Character[][]> gridEntry : grids.entrySet()) {
            Character[][] newGrid = new Character[5][5];
            int currentLevel = gridEntry.getKey();

            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    //log.info("x: {}, y: {}, grids.size: {}, newgrids.size: {}", x, y, grids.size(), newGrids.size());
                    newGrid[x][y] = growTile(grids, newGrids, currentLevel, x, y);
                }
            }
            newGrids.put(currentLevel, newGrid);
            //log.info("After: grids.size: {}, newgrids.size: {}", grids.size(), newGrids.size());
            //log.info("---------------------------------");
        }
        return newGrids;
    }

    Character growTile(Map<Integer, Character[][]> grids, Map<Integer, Character[][]> newGrids, int currentLevel, int x, int y) {
        //String neighbours;
        Character[] neighbours = {};
        Character[][] currentGrid = grids.get(currentLevel);
        Character[][] outerGrid;
        Character[][] innerGrid;

        // add upper and/or lower level if they don't exist
        if (grids.containsKey(currentLevel - 1)) {
            outerGrid = grids.get(currentLevel - 1);
        } else {
            if (newGrids.containsKey(currentLevel - 1)) {
                outerGrid = newGrids.get(currentLevel - 1);
            } else {
                //log.info("Adding new outer grid, level: {}", currentLevel - 1);
                outerGrid = newGrid();
                newGrids.put(currentLevel - 1, outerGrid);
            }
        }

        if (grids.containsKey(currentLevel + 1)) {
            innerGrid = grids.get(currentLevel + 1);
        } else {
            if (newGrids.containsKey(currentLevel + 1)) {
                innerGrid = newGrids.get(currentLevel + 1);
            } else {
                //log.info("Adding new inner grid, level: {}", currentLevel + 1);
                innerGrid = newGrid();
                newGrids.put(currentLevel + 1, innerGrid);
            }
        }

        // up, right, down, left

        if (x == 0 && y == 0) {
            // outer left upper corner
            neighbours = new Character[]{
                    outerGrid[2][1],    // 8
                    currentGrid[1][0],
                    currentGrid[0][1],
                    outerGrid[1][2]};   // 12

        } else if (x == 4 && y == 0) {
            // outer right upper corner
            neighbours = new Character[]{
                    outerGrid[2][1],    // 8
                    outerGrid[3][2],    // 14
                    currentGrid[4][1],
                    currentGrid[3][0]};

        } else if (x == 0 && y == 4) {
            // outer left bottom corner
            neighbours = new Character[]{
                    currentGrid[0][3],
                    currentGrid[1][4],
                    outerGrid[2][3],    // 18
                    outerGrid[1][2]};   // 12

        } else if (x == 4 && y == 4) {
            // outer right bottom corner
            neighbours = new Character[]{
                    currentGrid[4][3],
                    outerGrid[3][2],    // 14
                    outerGrid[2][3],    // 18
                    currentGrid[3][4]};

        } else if ((x > 0 && x < 4) && y == 0) {
            // top row
            neighbours = new Character[]{
                    outerGrid[2][1],    // 8
                    currentGrid[x + 1][0],
                    currentGrid[x][1],
                    currentGrid[x - 1][0]};

        } else if ((x > 0 && x < 4) && y == 4) {
            // bottom row
            neighbours = new Character[]{
                    currentGrid[x][3],
                    currentGrid[x + 1][4],
                    outerGrid[2][3],    // 18
                    currentGrid[x - 1][4]};

        } else if (x == 0 && (y > 0 && y < 4)) {
            // left column
            neighbours = new Character[]{
                    currentGrid[0][y - 1],
                    currentGrid[1][y],
                    currentGrid[0][y + 1],
                    outerGrid[1][2]};   // 12

        } else if (x == 4 && (y > 0 && y < 4)) {
            // right column
            neighbours = new Character[]{
                    currentGrid[4][y - 1],
                    outerGrid[3][2],    // 14
                    currentGrid[4][y + 1],
                    currentGrid[3][y]};

        } else if ((x == 1 && y == 1) || (x == 3 && y == 1) || (x == 1 && y == 3) || (x == 3 && y == 3)) {
            // inner corners
            neighbours = new Character[]{
                    currentGrid[x][y - 1],
                    currentGrid[x + 1][y],
                    currentGrid[x][y + 1],
                    currentGrid[x - 1][y]};

        } else if (x == 2 && y == 1) {
            // top inner
            neighbours = new Character[]{
                    currentGrid[2][0],
                    currentGrid[3][1],
                    innerGrid[0][0],
                    innerGrid[1][0],
                    innerGrid[2][0],
                    innerGrid[3][0],
                    innerGrid[4][0],
                    currentGrid[1][1]};

        } else if (x == 3 && y == 2) {
            // right inner
            neighbours = new Character[]{
                    currentGrid[3][1],
                    currentGrid[4][2],
                    currentGrid[3][3],
                    innerGrid[4][0],
                    innerGrid[4][1],
                    innerGrid[4][2],
                    innerGrid[4][3],
                    innerGrid[4][4]};

        } else if (x == 2 && y == 3) {
            // bottom inner
            neighbours = new Character[]{
                    innerGrid[0][4],
                    innerGrid[1][4],
                    innerGrid[2][4],
                    innerGrid[3][4],
                    innerGrid[4][4],
                    currentGrid[3][3],
                    currentGrid[2][4],
                    currentGrid[1][3]};

        } else if (x == 1 && y == 2) {
            // left inner
            neighbours = new Character[]{
                    currentGrid[1][1],
                    innerGrid[0][0],
                    innerGrid[0][1],
                    innerGrid[0][2],
                    innerGrid[0][3],
                    innerGrid[0][4],
                    currentGrid[1][3],
                    currentGrid[0][2]};
        }

        int numberOfBugs = 0;
        for (Character neighbour : neighbours) {
            if (neighbour == '#') {
                numberOfBugs++;
            }
        }

        char currentTile = currentGrid[x][y];
        if (currentTile == '#' && numberOfBugs != 1) {
            return '.';
        } else if (currentTile == '.' && (numberOfBugs == 1 || numberOfBugs == 2)) {
            return '#';
        } else {
            return currentTile;
        }

    }

    int bugsPresent(int minutes) {
        Map<Integer, Character[][]> newGrids;
        grids.get(0)[2][2] = '?';

        for (int minute = 0; minute < minutes; minute++) {
            System.out.println("----------------- minute " + minute + " --------------");
            //log.info("Start growing for minute {}", minute);
            newGrids = grow(grids);
            printTwoGrids(grids, newGrids);
            grids = newGrids;
        }

        int numberOfBugs = 0;
        for (Character[][] grid : grids.values()) {
            for (Character[] row : grid) {
                for (Character c : row) {
                    if (c == '#') {
                        numberOfBugs++;
                    }
                }
            }
        }
        return numberOfBugs;
    }
}
