package aoc.aoc2019;

import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day3CrossedWires {

    private final List<Wire> wires = new ArrayList<>();

    public Day3CrossedWires(List<String> inputLines) {
        for (String line : inputLines) {
            Wire wire = new Wire();
            wires.add(wire);

            Position pos = new Position(0, 0);
            int totalDistance = 0;

            for (String step : line.split(",")) {
                String direction = step.substring(0, 1);
                int distance = Integer.parseInt(step.substring(1));

                for (int i = 0; i < distance; i++) {
                    switch (direction) {
                        case "U" -> pos = new Position(pos.getX(), pos.getY() - 1);
                        case "D" -> pos = new Position(pos.getX(), pos.getY() + 1);
                        case "R" -> pos = new Position(pos.getX() + 1, pos.getY());
                        case "L" -> pos = new Position(pos.getX() - 1, pos.getY());
                    }
                    totalDistance++;
                    if (!wire.positions.containsKey(pos)) {
                        wire.positions.put(pos, totalDistance);
                    }
                }
            }
        }
    }

    int distanceToClosestIntersection() {
        // Find crossings
        Map<Position, Integer> crossings = new HashMap<>();
        for (Position pos : wires.get(0).positions.keySet()) {
            if (wires.get(1).positions.containsKey(pos)) {
                crossings.put(pos, pos.distance(new Position(0, 0)));
            }
        }
        return Collections.min(crossings.values());
    }

    int stepsToClosestIntersection() {
        // Find crossings
        Map<Position, Integer> crossings = new HashMap<>();
        for (Position pos : wires.get(0).positions.keySet()) {
            if (wires.get(1).positions.containsKey(pos)) {
                crossings.put(pos, wires.get(0).positions.get(pos) + wires.get(1).positions.get(pos));
            }
        }
        return Collections.min(crossings.values());
    }

    static class Wire {
        final Map<Position, Integer> positions = new HashMap<>();
    }
}
