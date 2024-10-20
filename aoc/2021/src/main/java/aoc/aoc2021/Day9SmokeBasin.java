package aoc.aoc2021;

import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day9SmokeBasin {
    final Map<Position, Integer> map = new HashMap<>();
    final Set<Position> lowPoints = new HashSet<>();

    Day9SmokeBasin(List<String> inputLines) {
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
        for (var position : map.keySet()) {
            boolean lowPoint = true;
            for (var adjacent : position.allAdjacent()) {
                if (map.containsKey(adjacent) && map.get(position) >= map.get(adjacent)) {
                    lowPoint = false;
                }
            }
            if (lowPoint) {
                log.debug("Low point at {}", position);
                riskLevel += map.get(position) + 1;
                lowPoints.add(position);
            }
        }
        return riskLevel;
    }

    int problem2() {
        problem1();

        Set<Position> alreadyCounted = new HashSet<>();
        List<Integer> sizes = new ArrayList<>();
        for (var position : lowPoints) {
            int size = getLowPoints(position, alreadyCounted) + 1;
            sizes.add(size);
            log.debug("Size of low point at {}: {}", position, size);
        }
        sizes.sort(Collections.reverseOrder());
        int result = 1;
        for (int i = 0; i < 3; i++) {
            result *= sizes.get(i);
        }
        return result;
    }

    int getLowPoints(Position position, Set<Position> alreadyCounted) {
        int size = 0;
        for (var adjacent : position.allAdjacent()) {
            if (map.containsKey(adjacent) && !alreadyCounted.contains(adjacent) && map.get(adjacent) != 9 && map.get(adjacent) > map.get(position)) {
                size += 1;
                size += getLowPoints(adjacent, alreadyCounted);
                alreadyCounted.add(adjacent);
            }
        }
        return size;
    }
}
