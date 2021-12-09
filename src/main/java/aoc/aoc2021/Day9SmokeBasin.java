package aoc.aoc2021;

import aoc.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day9SmokeBasin {

    Map<Position, Integer> map = new HashMap<>();
    Set<Position> lowPoints = new HashSet<>();

    public Day9SmokeBasin(List<String> inputLines) {
        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                map.put(new Position(x, y), Integer.parseInt(String.valueOf(c)));
                x++;
            }
            y++;
        }
    }

    int problem1() {
        int riskLevel = 0;
        for (Position p : map.keySet()) {
            boolean lowPoint = true;
            for (Position adj : p.allAdjacent()) {
                if (map.containsKey(adj) && map.get(p) >= map.get(adj)) {
                    lowPoint = false;
                }
            }
            if (lowPoint) {
                log.debug("Low point at {}", p);
                riskLevel += map.get(p) + 1;
                lowPoints.add(p);
            }
        }
        return riskLevel;
    }

    int problem2() {
        problem1();

        Set<Position> alreadyCounted = new HashSet<>();
        List<Integer> sizes = new ArrayList<>();
        for (Position p : lowPoints) {
            int size = getLowPoints(p, alreadyCounted) + 1;
            sizes.add(size);
            log.debug("Size of low point at {}: {}", p, size);
        }
        sizes.sort(Collections.reverseOrder());
        int result = 1;
        for (int i = 0; i < 3; i++) {
            result *= sizes.get(i);
        }
        return result;
    }

    private int getLowPoints(Position p, Set<Position> alreadyCounted) {
        int size = 0;
        for (Position adj : p.allAdjacent()) {
            if (map.containsKey(adj) && !alreadyCounted.contains(adj) && map.get(adj) != 9 && map.get(adj) > map.get(p)) {
                size += 1;
                size += getLowPoints(adj, alreadyCounted);
                alreadyCounted.add(adj);
            }
        }
        return size;
    }
}
