package aoc2018;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
class Day10TheStarsAlign {

    private final Sky sky = new Sky();

    @Data
    @AllArgsConstructor
    class Point {
        int xpos;
        int ypos;
        int xvel;
        int yvel;
    }

    @Data
    class Sky {
        final List<Point> pointList = new ArrayList<>();

        void addPoint(Point point) {
            pointList.add(point);
        }

        Sky movePointsForward() {
            // Move all points
            pointList.forEach(p -> {
                p.setXpos(p.getXpos() + p.getXvel());
                p.setYpos(p.getYpos() + p.getYvel());
            });
            return this;
        }

        Sky movePointsBackward() {
            // Move all points
            pointList.forEach(p -> {
                p.setXpos(p.getXpos() - p.getXvel());
                p.setYpos(p.getYpos() - p.getYvel());
            });
            return this;
        }

        Box getSkyBoundary() {
            // Loop and find the highest and lowest of xpos and ypos. This gives the box
            Box theSize = new Box();

            int lowX = pointList.stream().min(Comparator.comparingInt(Point::getXpos)).get().getXpos();
            int highX = pointList.stream().max(Comparator.comparingInt(Point::getXpos)).get().getXpos();
            int lowY = pointList.stream().min(Comparator.comparingInt(Point::getYpos)).get().getYpos();
            int highY = pointList.stream().max(Comparator.comparingInt(Point::getYpos)).get().getYpos();

            theSize.setStartx(lowX);
            theSize.setStarty(lowY);
            theSize.setWidth(highX - lowX + 1);
            theSize.setHeight(highY - lowY + 1);

            return theSize;
        }

        long getSize() {
            Box boundary = getSkyBoundary();

            return boundary.getHeight() * boundary.getWidth();
        }

        void print() {
            Box box = getSkyBoundary();

            System.out.println("Printing box, size: " + getSize());
            // create an empty sky
            List<List<Character>> printout = new ArrayList<>();
            for (int y = 0; y < box.getHeight(); y++) {
                List<Character> row = new ArrayList<>();
                for (int x = 0; x < box.getWidth(); x++) {
                    row.add('.');
                }
                printout.add(row);
            }

            // Go through all points and paint the sky!
            for (Point p : pointList) {
                printout.get(p.getYpos() - box.getStarty()).set(p.getXpos() - box.getStartx(), '#');
            }

            // print it
            for (List<Character> row : printout) {
                for (Character c : row) {
                    System.out.print(c);
                }
                System.out.println();
            }
            System.out.println();
        }
    }


    @Data
    class Box {
        int startx;
        int starty;
        int width;
        int height;
    }


    @Data
    class Result {
        String message;
        int time;
    }


    private void readData(String input) {
        log.debug("Reading data");

        // Split string into a list
        List<String> inputStrings = Arrays.stream(input.trim().split("\\n")).collect(Collectors.toList());


        for (String s : inputStrings) {
            String s1 = StringUtils.substringBetween(s, "position=<", ">");
            String s2 = StringUtils.substringBetween(s, "velocity=<", ">");
            sky.addPoint(new Point(
                    Integer.parseInt(StringUtils.substringBefore(s1, ",").trim()),
                    Integer.parseInt(StringUtils.substringAfter(s1, ",").trim()),
                    Integer.parseInt(StringUtils.substringBefore(s2, ",").trim()),
                    Integer.parseInt(StringUtils.substringAfter(s2, ",").trim())));
        }
    }


    Result getMessage(String input) {
        readData(input);

        // Assume that the message is when the box is as small as possible
        boolean keepGoing = true;
        int waitingTime = 0;
        while (keepGoing) {
            Box box = sky.getSkyBoundary();
            Box nextBox = sky.movePointsForward().getSkyBoundary();
            if (nextBox.getHeight() > box.getHeight() || nextBox.getWidth() > box.getWidth()) {
                keepGoing = false;
            }
            waitingTime++;
        }

        // Back up one step
        sky.movePointsBackward().print();
        waitingTime--;
        log.info("Box, width: " + sky.getSkyBoundary().getWidth() + ", height: " + sky.getSkyBoundary().getHeight());
        log.info("time " + waitingTime);

        Result result = new Result();
        result.setMessage("HI");
        result.setTime(waitingTime);
        return result;
    }
}
