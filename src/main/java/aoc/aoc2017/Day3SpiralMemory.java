package aoc.aoc2017;

import aoc.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Day3SpiralMemory {

    int stepsRequired(int dataLocation) {
        // Check which layer. If it's greater than 1^2 ,3^2, 5^2 etc
        int layer = 0;
        while (dataLocation > Math.pow(layer * 2 + 1, 2)) {
            layer++;
        }

        // distance from axis
        int sideLength = layer * 2 + 1;
        int corner = (int) Math.pow(layer * 2 + 1, 2);

        while ((corner - dataLocation) > sideLength - 1) {
            corner -= sideLength - 1;
        }
        int axisPosition = corner - layer;
        int distanceToAxis = Math.abs(dataLocation - axisPosition);
        return layer + distanceToAxis;
    }

    int stressTest(int inputValue) {
        Map<Position, Integer> values = new HashMap<>();
        int value = 0;
        int square = 2;

        values.put(new Position(0, 0), 1);

        while (value < inputValue) {
            Position pos = getPosition(square);

            Set<Position> adjacent = pos.allAdjacentIncludingDiagonal();
            int newValue = 0;
            for (Position p : adjacent) {
                newValue += values.getOrDefault(p, 0);
            }
            values.put(pos, newValue);
            value = newValue;
            square++;
        }
        return value;
    }

    Position getPosition(int dataLocation) {
        int layer = 0;
        while (dataLocation > Math.pow(layer * 2 + 1, 2)) {
            layer++;
        }
        int sideLength = layer * 2 + 1;
        int lowerRightCorner = (int) Math.pow(layer * 2 + 1, 2);
        int lowerLeftCorner = lowerRightCorner - sideLength + 1;
        int upperLeftCorner = lowerLeftCorner - sideLength + 1;
        int upperRightCorner = upperLeftCorner - sideLength + 1;

        if (dataLocation > lowerLeftCorner) {
            // Bottom row
            return new Position(layer - (lowerRightCorner - dataLocation), -layer);
        } else if (dataLocation > upperLeftCorner) {
            // Left column
            return new Position(-layer, (lowerLeftCorner - dataLocation) - layer);
        } else if (dataLocation > upperRightCorner) {
            // Top row
            return new Position((upperLeftCorner - dataLocation) - layer, layer);
        } else {
            // Right column
            return new Position(layer, layer - (upperRightCorner - dataLocation));
        }
    }
}
