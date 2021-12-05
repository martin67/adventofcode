package aoc.aoc2018;

import aoc.Position;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day17ReservoirResearch {

    private Set<Position> walls = new HashSet<>();
    private Set<Position> running = new HashSet<>();
    private Set<Position> still = new HashSet<>();
    private int minX = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxY = Integer.MIN_VALUE;

    private void readData(String input) {
        List<String> inputStrings = Arrays.stream(input.split("\\r\\n+"))
                .collect(Collectors.toList());
        // x=495, y=2..7
        for (String row : inputStrings) {
            if (row.startsWith("x")) {
                int x = Integer.parseInt(StringUtils.substringBetween(row, "x=", ","));
                if (x < minX) {
                    minX = x;
                }
                if (x > maxX) {
                    maxX = x;
                }
                int starty = Integer.parseInt(StringUtils.substringBetween(row, "y=", ".."));
                if (starty < minY) {
                    minY = starty;
                }
                int endy = Integer.parseInt(StringUtils.substringAfter(row, ".."));
                if (endy > maxY) {
                    maxY = endy;
                }
                for (int y = starty; y <= endy; y++) {
                    walls.add(new Position(x, y));
                }
            } else {
                int y = Integer.parseInt(StringUtils.substringBetween(row, "y=", ","));
                if (y < minY) {
                    minY = y;
                }
                if (y > maxY) {
                    maxY = y;
                }
                int startx = Integer.parseInt(StringUtils.substringBetween(row, "x=", ".."));
                if (startx < minX) {
                    minX = startx;
                }
                int endx = Integer.parseInt(StringUtils.substringAfter(row, ".."));
                if (endx > maxX) {
                    maxX = endx;
                }
                for (int x = startx; x <= endx; x++) {
                    walls.add(new Position(x, y));
                }

            }
        }
    }

    private void printMap() {
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Position pos = new Position(x, y);
                if (walls.contains(pos)) {
                    System.out.print("#");
                } else if (running.contains(pos)) {
                    System.out.print("|");
                } else if (still.contains(pos)) {
                    System.out.print("~");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println("X: (" + minX + "-" + maxX + "), Y: (" + minY + "-" + maxY + ")");
    }

    int numberOfWaterTiles(String input) {
        readData(input);
        // Put the well right over minY (otherwise water inbetween should be skipped)
        running.add(new Position(500,minY-1));
        int lastNumberOfRunningWater = 0;
        int lastNumberOfStillWater = 0;

        while (running.size() != lastNumberOfRunningWater || still.size() !=lastNumberOfStillWater) {
            lastNumberOfRunningWater = running.size();
            lastNumberOfStillWater = still.size();
            //System.out.println("Iterating - running: " + running.size() + ", still: " + still.size());
            //printMap();
            Set<Position> runningClone = new HashSet<>(running);
            for (Position tile : runningClone) {
                // if the position below is empty, fill it with running water
                Position below = new Position(tile.getX(), tile.getY() + 1);
                if (isEmpty(below)) {
                    // also check that we are not outside the field
                    if (below.getY() <= maxY) {
                        running.add(below);
                    }
                } else {
                    // if the tile below is wall or still water,
                    // expand the running water to the left and right if it's empty
                    if (walls.contains(below) || still.contains(below)) {
                        Position left = new Position(tile.getX() - 1, tile.getY());
                        Position right = new Position(tile.getX() + 1, tile.getY());
                        if (isEmpty(left)) {
                            running.add(left);
                        }
                        if (isEmpty(right)) {
                            running.add(right);
                        }
                    }
                }
            }
            // Check if there is a full row with running water, blocked by walls in both ends
            // in that case, convert it to still water
            for (Position tile : runningClone) {
                Position left = new Position(tile.getX() - 1, tile.getY());
                if (walls.contains(left)) {
                    // traverse right
                    Position right = new Position(tile.getX() + 1, tile.getY());
                    while (running.contains(right)) {
                        right = new Position(right.getX() + 1, right.getY());
                    }
                    if (walls.contains(right)) {
                        // found a wall on the right side. Convert row to still water
                        for (int x = tile.getX(); x < right.getX(); x++) {
                            Position pos = new Position(x, tile.getY());
                            still.add(pos);
                            running.remove(pos);
                        }
                    }
                }
            }
        }
        // Subtract the well
        //printMap();
        return running.size() + still.size() - 1;
    }

    int numberOfRemainingWater(String input) {
        numberOfWaterTiles(input);
        return still.size();
    }

    private boolean isEmpty(Position position) {
        return !walls.contains(position) && !running.contains(position) && !still.contains(position);
    }
}
