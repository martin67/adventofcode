package aoc.aoc2021;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

@Slf4j
public class Day10 {

    List<String> lines;

    public Day10(List<String> inputLines) {
        lines = inputLines;
    }

    int problem1() {
        int points = 0;
        for (String line : lines) {
            points += checkLine(line);
        }
        return points;
    }

    long problem2() {
        List<Long> points = new ArrayList<>();
        for (String line : lines) {
            if (checkLine(line) == 0) {
                points.add(completeLine(line));
            }
        }
        Collections.sort(points);
        int middle = points.size() / 2;
        return points.get(middle);
    }

    private int checkLine(String line) {
        char r;
        Stack<Character> stack = new Stack<>();
        for (char c : line.toCharArray()) {
            switch (c) {
                case '(':
                case '[':
                case '{':
                case '<':
                    stack.add(c);
                    break;

                case ')':
                    r = stack.pop();
                    if (r != '(') {
                        log.info("line {}, expected (, but found {} instead", line, r);
                        return 3;
                    }
                    break;
                case ']':
                    r = stack.pop();
                    if (r != '[') {
                        log.info("line {}, expected [, but found {} instead", line, r);
                        return 57;
                    }
                    break;
                case '}':
                    r = stack.pop();
                    if (r != '{') {
                        log.info("line {}, expected {, but found {} instead", line, r);
                        return 1197;
                    }
                    break;
                case '>':
                    r = stack.pop();
                    if (r != '<') {
                        log.info("line {}, expected <, but found {} instead", line, r);
                        return 25137;
                    }
                    break;
            }
        }
        return 0;
    }

    private long completeLine(String line) {
        Stack<Character> stack = new Stack<>();
        for (char c : line.toCharArray()) {
            switch (c) {
                case '(':
                case '[':
                case '{':
                case '<':
                    stack.add(c);
                    break;
                case ')':
                case ']':
                case '}':
                case '>':
                    stack.pop();
                    break;
            }
        }

        // pop rest of the stack
        long totalScore = 0;
        while (!stack.isEmpty()) {
            int score = 0;
            char c = stack.pop();
            switch (c) {
                case '(':
                    score = 1;
                    break;
                case '[':
                    score = 2;
                    break;
                case '{':
                    score = 3;
                    break;
                case '<':
                    score = 4;
                    break;
            }
            totalScore *= 5;
            totalScore += score;
        }
        log.debug("line {}, total score {}", line, totalScore);
        return totalScore;
    }
}
