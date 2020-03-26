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
        int maxLevel = grids.keySet().stream().mapToInt(v -> v).max().orElse(0);
        newGrids.put(-maxLevel - 1, newGrid());
        newGrids.put(maxLevel + 1, newGrid());

        for (Map.Entry<Integer, Character[][]> gridEntry : grids.entrySet()) {
            int currentLevel = gridEntry.getKey();

            Character[][] currentGrid = grids.get(currentLevel);
            Character[][] outerGrid = grids.getOrDefault(currentLevel - 1, newGrids.get(currentLevel - 1));
            Character[][] innerGrid = grids.getOrDefault(currentLevel + 1, newGrids.get(currentLevel + 1));
            newGrids.put(currentLevel, growGrid(currentGrid, outerGrid, innerGrid));
        }
        return newGrids;
    }

    Character[][] growGrid(Character[][] currentGrid, Character[][] outerGrid, Character[][] innerGrid) {
        Character[][] result = new Character[5][5];

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {

                Character[] neighbours = {};

                // up, right, down, left

                if (x == 0 && y == 0) {
                    // outer left upper corner (A)
                    neighbours = new Character[]{
                            outerGrid[2][1],    // 8
                            currentGrid[1][0],
                            currentGrid[0][1],
                            outerGrid[1][2]};   // 12

                } else if (x == 4 && y == 0) {
                    // outer right upper corner (E)
                    neighbours = new Character[]{
                            outerGrid[2][1],    // 8
                            outerGrid[3][2],    // 14
                            currentGrid[4][1],
                            currentGrid[3][0]};

                } else if (x == 0 && y == 4) {
                    // outer left bottom corner (U)
                    neighbours = new Character[]{
                            currentGrid[0][3],
                            currentGrid[1][4],
                            outerGrid[2][3],    // 18
                            outerGrid[1][2]};   // 12

                } else if (x == 4 && y == 4) {
                    // outer right bottom corner (Y)
                    neighbours = new Character[]{
                            currentGrid[4][3],
                            outerGrid[3][2],    // 14
                            outerGrid[2][3],    // 18
                            currentGrid[3][4]};

                } else if ((x > 0 && x < 4) && y == 0) {
                    // top row (B,C,D)
                    neighbours = new Character[]{
                            outerGrid[2][1],    // 8
                            currentGrid[x + 1][0],
                            currentGrid[x][1],
                            currentGrid[x - 1][0]};

                } else if ((x > 0 && x < 4) && y == 4) {
                    // bottom row (V,W,X)
                    neighbours = new Character[]{
                            currentGrid[x][3],
                            currentGrid[x + 1][4],
                            outerGrid[2][3],    // 18
                            currentGrid[x - 1][4]};

                } else if (x == 0 && (y > 0 && y < 4)) {
                    // left column (F,K,P)
                    neighbours = new Character[]{
                            currentGrid[0][y - 1],
                            currentGrid[1][y],
                            currentGrid[0][y + 1],
                            outerGrid[1][2]};   // 12

                } else if (x == 4 && (y > 0 && y < 4)) {
                    // right column (J,O,T)
                    neighbours = new Character[]{
                            currentGrid[4][y - 1],
                            outerGrid[3][2],    // 14
                            currentGrid[4][y + 1],
                            currentGrid[3][y]};

                } else if ((x == 1 && y == 1) || (x == 3 && y == 1) || (x == 1 && y == 3) || (x == 3 && y == 3)) {
                    // inner corners (G,I,Q,S)
                    neighbours = new Character[]{
                            currentGrid[x][y - 1],
                            currentGrid[x + 1][y],
                            currentGrid[x][y + 1],
                            currentGrid[x - 1][y]};

                } else if (x == 2 && y == 1) {
                    // top inner (H)
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
                    // right inner (N)
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
                    // bottom inner (R)
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
                    // left inner (L)
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
                    result[x][y] = '.';
                } else if (currentTile == '.' && (numberOfBugs == 1 || numberOfBugs == 2)) {
                    result[x][y] = '#';
                } else {
                    result[x][y] = currentTile;
                }
            }
        }
        return result;
    }

    int bugsPresent(int minutes) {
        grids.get(0)[2][2] = '?';
        grids.put(-1, newGrid());
        grids.put(1, newGrid());

        for (int minute = 0; minute < minutes; minute++) {
            //System.out.println("----------------- minute " + minute + " --------------");
            grids = grow(grids);
            //printTwoGrids(grids, newGrids);
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
