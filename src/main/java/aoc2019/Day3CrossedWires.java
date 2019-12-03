package aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Day3CrossedWires {

    @Data
    static class Wire {
        List<Position> positions = new ArrayList<>();
    }

    List<Wire> wires = new ArrayList<>();

    int distanceToClosestIntersection(List<String> inputLines) {
        for (String line : inputLines) {
            Wire wire = new Wire();
            wires.add(wire);

            Position pos = new Position(0, 0);
            wire.positions.add(pos);

            for (String step : line.split(",")) {
                String direction = step.substring(0, 1);
                int distance = Integer.parseInt(step.substring(1));

                for (int i = 0; i < distance; i++) {
                    switch (direction) {
                        case "U":
                            pos = new Position(pos.getX(), pos.getY() - 1);
                            break;
                        case "D":
                            pos = new Position(pos.getX(), pos.getY() + 1);
                            break;
                        case "R":
                            pos = new Position(pos.getX() + 1, pos.getY());
                            break;
                        case "L":
                            pos = new Position(pos.getX() - 1, pos.getY());
                            break;
                    }
                    wire.positions.add(pos);
                }
            }
        }

        // Find crossings
        List<Position> crossings = new ArrayList<>();
        for (Position posLine1 : wires.get(0).positions) {
            for (Position posLine2 : wires.get(1).positions) {
                if (posLine1.equals(posLine2)) {
                    crossings.add(posLine1);
                }
            }
        }

        // Order by distance
        Position closest = crossings.stream()
                .sorted(Comparator.comparingInt(p -> p.distance(new Position(0, 0))))
                .collect(Collectors.toList()).get(1);
        return closest.distance(new Position(0,0));
    }

    int stepsToClosestIntersection(List<String> inputLines) {

        return 0;
    }
}
