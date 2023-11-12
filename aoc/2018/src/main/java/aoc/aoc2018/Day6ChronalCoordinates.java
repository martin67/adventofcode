package aoc.aoc2018;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
class Day6ChronalCoordinates {

    private final List<Point> pointList = new ArrayList<>();

    private void readData(String input) {
        // Split string into a list
        List<String> inputStrings = Arrays.stream(input.trim().split("\\n+")).toList();

        for (String inputString : inputStrings) {
            pointList.add(new Point(
                    Integer.parseInt(StringUtils.substringBefore(inputString, ",").trim()),
                    Integer.parseInt(StringUtils.substringAfter(inputString, ",").trim())));
        }
        log.info("Reading " + pointList.size() + " points");
    }

    private Edges findEdges() {

        // Find the points that are on the edges
        Edges edges = new Edges();

        // Set an initial value;
        edges.setLeft(pointList.get(0).getX());
        edges.setRight(pointList.get(0).getX());
        edges.setUpper(pointList.get(0).getY());
        edges.setLower(pointList.get(0).getY());


        for (Point point : pointList) {
            if (point.getX() < edges.getLeft()) {
                edges.setLeft(point.getX());
            }
            if (point.getX() > edges.getRight()) {
                edges.setRight(point.getX());
            }
            if (point.getY() < edges.getUpper()) {
                edges.setUpper(point.getY());
            }
            if (point.getY() > edges.getLower()) {
                edges.setLower(point.getY());
            }
        }
        log.info("Edges at " + edges);

//        for (Point point : pointList) {
//            if (point.getX() == edges.getLeft() || point.getX() == edges.getRight() ||
//                    point.getY() == edges.getUpper() || point.getY() == edges.getLower()) {
//                point.setInfinite(true);
//            }
//        }
        return edges;
    }

    int largestArea(String input) {
        readData(input);
        Edges edges = findEdges();

        // Loop through all coordinates and find the closest Point.
        for (int y = edges.getUpper(); y < edges.getLower() + 1; y++) {
            for (int x = edges.getLeft(); x < edges.getRight() + 1; x++) {
                // compute the distance to all points
                int shortestDistance = -1;
                Point closestPoint = null;
                for (Point point : pointList) {
                    int distance = Math.abs(x - point.getX()) + Math.abs(y - point.getY());
                    // log.info("Distance from " + x + "," + y + " to point " + point + ": " + distance);

                    if (shortestDistance == -1 || distance < shortestDistance) {
                        shortestDistance = distance;
                        closestPoint = point;
                    } else if (distance == shortestDistance) {
                        // equal distance, no point is shorter
                        closestPoint = null;
                    }
                }
                //log.info("Closest point: " + closestPoint);

                // if the coordinate is on the edge, the closest point will be infinite
                if (x == edges.getLeft() || x == edges.getRight() ||
                        y == edges.getUpper() || y == edges.getLower()) {
                    //log.info("coordinate on the edge, " + x + ", " + y);
                    //log.info("setting point " + closestPoint + " to infinite");
                    if (closestPoint != null) {
                        closestPoint.setInfinite(true);
                    }

                }
                // Increase the area if the point isn't on the edge
                if (closestPoint != null && !closestPoint.isInfinite()) {
                    closestPoint.setArea(closestPoint.getArea() + 1);
                }
            }
        }

        // Find the point with biggest area
        return pointList.stream().filter(p -> !p.isInfinite()).mapToInt(Point::getArea).max().orElse(0);
    }

    int sizeOfRegion(String input, int totalDistance) {
        readData(input);
        Edges edges = findEdges();

        int regionSize = 0;

        // Loop through all coordinates and find the closest Point.
        for (int y = edges.getUpper(); y < edges.getLower() + 1; y++) {
            for (int x = edges.getLeft(); x < edges.getRight() + 1; x++) {
                // compute the distance to all points
                int distance = 0;
                for (Point point : pointList) {
                    distance += Math.abs(x - point.getX()) + Math.abs(y - point.getY());

                }
                //log.info("coordinate " + x + ", " + y + " distance " + distance);
                if (distance < totalDistance) {
                    regionSize++;
                }
            }
        }

        return regionSize;
    }

    @Data
    @RequiredArgsConstructor
    static class Point {
        final int x;
        final int y;
        boolean infinite = false;
        int area = 0;
    }

    @Data
    @NoArgsConstructor
    static class Edges {
        int upper;
        int lower;
        int left;
        int right;
    }
}
