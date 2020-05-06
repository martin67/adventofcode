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
        int corner = (int) Math.pow(layer * 2 + 1, 2);

        while ((corner - dataLocation) > sideLength - 1) {
            corner -= sideLength - 1;
        }
        int axisPosition = corner - layer;
        int distanceToAxis = Math.abs(dataLocation - axisPosition);
        return layer + distanceToAxis;
    }
}
