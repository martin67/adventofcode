package aoc.aoc2024;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static aoc.common.Direction.*;

@Slf4j
public class Day4CeresSearch {

    static final Set<Direction> ALL_DIRECTIONS = Set.of(North, South, East, West, NorthEast, NorthWest, SouthEast, SouthWest);
    static final Set<Direction> DIAGONALS = Set.of(NorthEast, NorthWest, SouthEast, SouthWest);

    Map<Position, Character> map = new HashMap<>();

    Day4CeresSearch(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                map.put(new Position(x, y), c);
                x++;
            }
            y++;
        }
    }

    int problem1() {
        int hits = 0;
        for (Map.Entry<Position, Character> entry : map.entrySet()) {
            if (entry.getValue() == 'X') {
                for (Direction dir : ALL_DIRECTIONS) {
                    if (map.getOrDefault(entry.getKey().adjacent(dir), '-') == 'M' &&
                            map.getOrDefault(entry.getKey().adjacent(dir).adjacent(dir), '-') == 'A' &&
                            map.getOrDefault(entry.getKey().adjacent(dir).adjacent(dir).adjacent(dir), '-') == 'S') {
                        hits++;
                    }
                }
            }
        }
        return hits;
    }

    int problem2() {
        int hits = 0;
        for (Map.Entry<Position, Character> entry : map.entrySet()) {
            if (entry.getValue() == 'A') {
                int localHits = 0;
                for (Direction dir : DIAGONALS) {
                    if (map.getOrDefault(entry.getKey().adjacent(dir), '-') == 'M' &&
                            map.getOrDefault(entry.getKey().adjacent(dir.opposite()), '-') == 'S') {
                        localHits++;
                    }
                }
                if (localHits == 2) {
                    hits++;
                }
            }
        }
        return hits;
    }
}
