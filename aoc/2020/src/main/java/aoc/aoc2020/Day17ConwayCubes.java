package aoc.aoc2020;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day17ConwayCubes {
    Set<Position3D> space3D = new HashSet<>();
    Set<Position4D> space4D = new HashSet<>();

    Day17ConwayCubes(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == '#') {
                    space3D.add(new Position3D(x, y, 0));
                    space4D.add(new Position4D(x, y, 0, 0));
                }
                x++;
            }
            y++;
        }
    }

    int problem1() {
        for (int cycle = 0; cycle < 6; cycle++) {
            Set<Position3D> nextSpace = new HashSet<>();
            Set<Position3D> spaceToCheck = new HashSet<>();

            for (var position : space3D) {
                spaceToCheck.addAll(position.allAdjacent());
            }

            for (var position : spaceToCheck) {
                var neighbours = position.allAdjacent();
                long activeNeighbours = neighbours.stream().filter(p -> space3D.contains(p)).count();
                if (space3D.contains(position)) {
                    if (activeNeighbours == 2 || activeNeighbours == 3) {
                        nextSpace.add(position);
                    }
                } else {
                    if (activeNeighbours == 3) {
                        nextSpace.add(position);
                    }
                }
            }
            space3D = nextSpace;
        }
        return space3D.size();
    }

    int problem2() {
        for (int cycle = 0; cycle < 6; cycle++) {
            Set<Position4D> nextSpace = new HashSet<>();
            Set<Position4D> spaceToCheck = new HashSet<>();

            for (var position : space4D) {
                spaceToCheck.addAll(position.allAdjacent());
            }

            for (var position : spaceToCheck) {
                var neighbours = position.allAdjacent();
                long activeNeighbours = neighbours.stream().filter(p -> space4D.contains(p)).count();
                if (space4D.contains(position)) {
                    if (activeNeighbours == 2 || activeNeighbours == 3) {
                        nextSpace.add(position);
                    }
                } else {
                    if (activeNeighbours == 3) {
                        nextSpace.add(position);
                    }
                }
            }
            space4D = nextSpace;
        }
        return space4D.size();
    }


    record Position3D(int x, int y, int z) {
        Set<Position3D> allAdjacent() {
            Set<Position3D> adjacent = new HashSet<>();

            for (int dx = -1; dx < 2; dx++) {
                for (int dy = -1; dy < 2; dy++) {
                    for (int dz = -1; dz < 2; dz++) {
                        if (!(dx == 0 && dy == 0 && dz == 0)) {
                            adjacent.add(new Position3D(x + dx, y + dy, z + dz));
                        }
                    }
                }
            }
            return adjacent;
        }
    }

    record Position4D(int x, int y, int z, int w) {
        Set<Position4D> allAdjacent() {
            Set<Position4D> adjacent = new HashSet<>();

            for (int dx = -1; dx < 2; dx++) {
                for (int dy = -1; dy < 2; dy++) {
                    for (int dz = -1; dz < 2; dz++) {
                        for (int dw = -1; dw < 2; dw++) {
                            if (!(dx == 0 && dy == 0 && dz == 0 && dw == 0)) {
                                adjacent.add(new Position4D(x + dx, y + dy, z + dz, w + dw));
                            }
                        }
                    }
                }
            }
            return adjacent;
        }
    }
}
