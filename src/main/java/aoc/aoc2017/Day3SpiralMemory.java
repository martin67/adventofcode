package aoc.aoc2017;

public class Day3SpiralMemory {

    int stepsRequired(int dataLocation) {
        // Check which layer. If it's greater than 1^2 ,3^2, 5^2 etc
        int layer = 0;
        while (dataLocation > Math.pow(layer * 2 + 1, 2)) {
            layer++;
        }

        // distance from axis
        int sideLength = layer * 2 + 1;
        int distance = (int) (Math.pow(layer * 2 + 1, 2) - dataLocation);

        while (distance > sideLength) {
            distance -= sideLength;
        }
        int distanceToAxis = Math.abs(distance - layer);
        return layer + distanceToAxis;
    }
}
