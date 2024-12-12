package aoc.aoc2024;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static aoc.common.Direction.*;

@Slf4j
public class Day12GardenGroups {

    Map<Position, Character> map = new HashMap<>();
    Map<Character, Set<Set<Position>>> regionMap = new HashMap<>();

    Day12GardenGroups(List<String> inputLines) {
        Set<Character> plants = new HashSet<>();

        int y = 0;
        for (String line : inputLines) {
            int x = 0;
            for (char c : line.toCharArray()) {
                map.put(new Position(x, y), c);
                plants.add(c);
                x++;
            }
            y++;
        }

        for (Character plant : plants) {
            Set<Set<Position>> regions = new HashSet<>();
            findRegions(plant, regions);
            regionMap.put(plant, regions);
        }
    }

    int problem1() {
        int price = 0;
        for (Set<Set<Position>> regions : regionMap.values()) {
            for (Set<Position> region : regions) {
                int perimeter = findPerimeter(region);
                price += perimeter * region.size();
            }
        }
        return price;
    }

    int problem2() {
        int price = 0;
        for (Set<Set<Position>> regions : regionMap.values()) {
            for (Set<Position> region : regions) {
                int sides = findSides(region);
                price += sides * region.size();
            }
        }
        return price;
    }

    void findRegions(char plant, Set<Set<Position>> regions) {
        for (Position p : map.keySet()) {
            if (map.getOrDefault(p, '-') == plant) {
                Set<Position> region = new HashSet<>();
                // add all adjacent
                Set<Position> connected = new HashSet<>();
                getConnectedPlants(plant, p, connected);
                if (connected.isEmpty()) {
                    region.add(p);
                } else {
                    region.addAll(connected);
                }
                regions.add(region);
            }
        }
    }

    void getConnectedPlants(char plant, Position position, Set<Position> connected) {
        for (Position adjacent : position.allAdjacent()) {
            char p = map.getOrDefault(adjacent, '-');
            if (!connected.contains(adjacent) && p == plant) {
                connected.add(adjacent);
                getConnectedPlants(plant, adjacent, connected);
            }
        }
    }

    int findPerimeter(Set<Position> region) {
        int perimeter = 0;
        for (Position p : region) {
            for (Position adjacent : p.allAdjacent()) {
                if (!region.contains(adjacent)) {
                    perimeter++;
                }
            }
        }
        return perimeter;
    }

    int findSides(Set<Position> region) {
        Set<Direction> directions = Set.of(North, West, South, East);
        Set<Set<Position>> checked = new HashSet<>();

        int perimeter = findPerimeter(region);

        for (Position p : region) {
            // if the adjacent plant has an empty side in the same direction, then it is a side
            for (Position adjacent : p.allAdjacent()) {
                boolean alreadychecked = false;
                for (Set<Position> check : checked) {
                    if (check.contains(adjacent) && check.contains(p)) {
                        alreadychecked = true;
                        break;
                    }
                }
                if (region.contains(adjacent) && !alreadychecked) {
                    for (Direction direction : directions) {
                        if (!region.contains(p.adjacent(direction)) && !region.contains(adjacent.adjacent(direction))) {
                            perimeter--;
                        }
                    }
                    checked.add(Set.of(p, adjacent));
                }
            }
        }
        return perimeter;
    }
}
