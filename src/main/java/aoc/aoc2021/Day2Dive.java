package aoc.aoc2021;

import java.util.ArrayList;
import java.util.List;

public class Day2Dive {

    List<Course> courses = new ArrayList<>();

    public Day2Dive(List<String> inputLines) {
        inputLines.forEach(line -> {
            String[] s = line.split("\\s");
            courses.add(new Course(s[0], Integer.parseInt(s[1])));
        });
    }

    int problem1() {
        int horizontalPosition = 0;
        int depth = 0;

        for (Course course : courses) {
            switch (course.direction) {
                case "forward":
                    horizontalPosition += course.distance;
                    break;
                case "down":
                    depth += course.distance;
                    break;
                case "up":
                    depth -= course.distance;
                    break;
            }
        }
        return horizontalPosition * depth;
    }

    int problem2() {
        int horizontalPosition = 0;
        int depth = 0;
        int aim = 0;

        for (Course course : courses) {
            switch (course.direction) {
                case "forward":
                    horizontalPosition += course.distance;
                    depth += aim * course.distance;
                    break;
                case "down":
                    aim += course.distance;
                    break;
                case "up":
                    aim -= course.distance;
                    break;
            }
        }
        return horizontalPosition * depth;
    }

    static class Course {
        String direction;
        int distance;

        public Course(String direction, int distance) {
            this.direction = direction;
            this.distance = distance;
        }
    }
}
