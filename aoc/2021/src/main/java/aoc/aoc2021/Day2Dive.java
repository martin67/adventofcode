package aoc.aoc2021;

import java.util.ArrayList;
import java.util.List;

public class Day2Dive {

    private final List<Course> courses = new ArrayList<>();

    public Day2Dive(List<String> inputLines) {
        inputLines.forEach(line -> {
            String[] s = line.split("\\s");
            courses.add(new Course(s[0], Integer.parseInt(s[1])));
        });
    }

    public int problem1() {
        int horizontalPosition = 0;
        int depth = 0;

        for (Course course : courses) {
            switch (course.direction) {
                case "forward" -> horizontalPosition += course.distance;
                case "down" -> depth += course.distance;
                case "up" -> depth -= course.distance;
            }
        }
        return horizontalPosition * depth;
    }

    public int problem2() {
        int horizontalPosition = 0;
        int depth = 0;
        int aim = 0;

        for (Course course : courses) {
            switch (course.direction) {
                case "forward" -> {
                    horizontalPosition += course.distance;
                    depth += aim * course.distance;
                }
                case "down" -> aim += course.distance;
                case "up" -> aim -= course.distance;
            }
        }
        return horizontalPosition * depth;
    }

    record Course(String direction, int distance) {
    }
}
