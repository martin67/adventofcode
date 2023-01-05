package aoc.aoc2022;

import aoc.common.SpacePosition;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class Day18BoilingBoulders {
    Set<SpacePosition> cubes = new HashSet<>();

    public Day18BoilingBoulders(List<String> inputLines) {
        for (String line : inputLines) {
            String[] s = line.split(",");
            cubes.add(new SpacePosition(s[0], s[1], s[2]));
        }
    }

    int problem1() {
        return freeSides(cubes);
    }

    int freeSides(Set<SpacePosition> c) {
        int totalFreeSides = 0;
        for (SpacePosition cubeA : c) {
            Set<SpacePosition> adjacent = cubeA.adjacent();
            int freeSides = 6;
            for (SpacePosition cubeB : c) {
                if (cubeA != cubeB) {
                    if (adjacent.contains(cubeB)) {
                        freeSides--;
                    }
                }
            }
            totalFreeSides += freeSides;
        }
        return totalFreeSides;
    }

    int problem2() {
        int xMin = cubes.stream().mapToInt(SpacePosition::getX).min().orElseThrow();
        int xMax = cubes.stream().mapToInt(SpacePosition::getX).max().orElseThrow();
        int yMin = cubes.stream().mapToInt(SpacePosition::getY).min().orElseThrow();
        int yMax = cubes.stream().mapToInt(SpacePosition::getY).max().orElseThrow();
        int zMin = cubes.stream().mapToInt(SpacePosition::getZ).min().orElseThrow();
        int zMax = cubes.stream().mapToInt(SpacePosition::getZ).max().orElseThrow();

        // Use a search area that is the cube maximum + 1 in all directions
        Set<SpacePosition> unexplored = new HashSet<>();
        Set<SpacePosition> explored = new HashSet<>();
        // start in lowest corner
        unexplored.add(new SpacePosition(xMin - 1, yMin - 1, zMin - 1));
        int outsideBorders = 0;

        while (unexplored.size() > 0) {
            SpacePosition sp = unexplored.iterator().next();
            unexplored.remove(sp);
            Set<SpacePosition> adjacent = sp.adjacent();
            // filter out those outside of the main cube
            adjacent.removeIf(s -> (s.getX() < xMin - 1 || s.getX() > xMax + 1 || s.getY() < yMin - 1 || s.getY() > yMax + 1 || s.getZ() < zMin - 1 || s.getZ() > zMax + 1));

            for (SpacePosition s : adjacent) {
                if (cubes.contains(s)) {
                    outsideBorders++;
                } else if (!explored.contains(s)) {
                    unexplored.add(s);
                }
            }
            explored.add(sp);
        }
        return outsideBorders;
    }

}
