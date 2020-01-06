package aoc.aoc2019;

import aoc.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day24PlanetOfDiscord {

    Map<Position, Character> map = new HashMap<>();
    final Set<String> previousMaps = new HashSet<>();

    public Day24PlanetOfDiscord(List<String> inputLines) {
        int x;
        int y = 0;
        for (String line : inputLines) {
            x = 0;
            for (char c : line.toCharArray()) {
                map.put(new Position(x, y), c);
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
}
