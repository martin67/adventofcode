package aoc.aoc2021;

import aoc.Direction;
import aoc.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Day20TrenchMap {

    String algorithm;
    Set<Position> map = new HashSet<>();

    public Day20TrenchMap(List<String> inputLines) {
        algorithm = inputLines.get(0);


        for (int y = 0; y < inputLines.size() - 2; y++) {
            String line = inputLines.get(y + 2);
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == '#') {
                    map.add(new Position(x, y));
                }
                x++;
            }
        }
    }

    private void printMap(Set<Position> map) {
        int xMin = map.stream().mapToInt(Position::getX).min().orElseThrow(NoSuchElementException::new);
        int xMax = map.stream().mapToInt(Position::getX).max().orElseThrow(NoSuchElementException::new);
        int yMin = map.stream().mapToInt(Position::getY).min().orElseThrow(NoSuchElementException::new);
        int yMax = map.stream().mapToInt(Position::getY).max().orElseThrow(NoSuchElementException::new);

        System.out.printf("Map, x: <%d,%d>, y: <%d,%d>%n", xMin, xMax, yMin, yMax);
        for (int y = yMin; y < yMax + 1; y++) {
            for (int x = xMin; x < xMax + 1; x++) {
                if (map.contains(new Position(x, y))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public int problem1() {
        printMap(map);
        log.info("Step 0: {} pixels", map.size());
        Set<Position> newMap = enhance(map);
        log.info("Step 1: {} pixels", newMap.size());
        printMap(newMap);
        newMap = enhance(newMap);
        log.info("Step 2: {} pixels", newMap.size());
        printMap(newMap);
        newMap = newMap.stream()
                .filter(p -> p.getX() > -12)
                .filter(p -> p.getX() < 112)
                .filter(p -> p.getY() > -12)
                .filter(p -> p.getY() < 112)
                .collect(Collectors.toSet());
        printMap(newMap);
        return newMap.size();
    }

    // 5065 too low
    public int problem2() {
        Set<Position> m = map;
        //printMap(m);
        for (int i = 0; i < 50; i++) {
            log.info("Step {}: {} pixels", i, m.size());
            m = enhance(m);
            //printMap(m);
            if (i % 2 == 1) {
                // cut borders
                log.info("Strip, i: {}", i);
                int finalI = i;
                m = m.stream()
                        .filter(p -> p.getX() > -2 - finalI)
                        .filter(p -> p.getX() < 101 + finalI)
                        .filter(p -> p.getY() > -2 - finalI)
                        .filter(p -> p.getY() < 101 + finalI)
                        .collect(Collectors.toSet());
                //printMap(m);
            }
        }
        return m.size();
    }

    private Set<Position> enhance(Set<Position> input) {
        Set<Position> newMap = new HashSet<>();
        Set<Position> checkedPositions = new HashSet<>();
        for (Position p : input) {
            Set<Position> grid = p.square(16);
            for (Position gridPos : grid) {
                if (!checkedPositions.contains(gridPos)) {
                    int binaryNumber = binaryNumber(input, gridPos);
                    checkedPositions.add(gridPos);
                    if (algorithm.charAt(binaryNumber) == '#') {
                        newMap.add(gridPos);
                    }
                }
            }
        }
        return newMap;
    }

    private int binaryNumber(Set<Position> map, Position pos) {
        int result = 0;

        // 1 2 4 8 16 32 64 128 256
        if (map.contains(pos.adjacent(Direction.NorthWest))) {
            result += 256;
        }
        if (map.contains(pos.adjacent(Direction.North))) {
            result += 128;
        }
        if (map.contains(pos.adjacent(Direction.NorthEast))) {
            result += 64;
        }
        if (map.contains(pos.adjacent(Direction.West))) {
            result += 32;
        }
        if (map.contains(pos)) {
            result += 16;
        }
        if (map.contains(pos.adjacent(Direction.East))) {
            result += 8;
        }
        if (map.contains(pos.adjacent(Direction.SouthWest))) {
            result += 4;
        }
        if (map.contains(pos.adjacent(Direction.South))) {
            result += 2;
        }
        if (map.contains(pos.adjacent(Direction.SouthEast))) {
            result += 1;
        }

        return result;
    }
}

