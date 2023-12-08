package aoc.aoc2023;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import aoc.common.WrappingCounter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static aoc.common.AocMath.lcm;

@Slf4j
class Day8HauntedWasteland {
    String instructions;
    final List<Node> nodes = new ArrayList<>();

    public Day8HauntedWasteland(List<String> inputLines) {
        var pattern = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");

        for (String line : inputLines) {
            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                var node = nodes.stream().filter(n -> n.id.equals(matcher.group(1))).findFirst().orElseGet(() -> {
                    var n = new Node(matcher.group(1));
                    nodes.add(n);
                    return n;
                });
                node.left = nodes.stream().filter(n1 -> n1.id.equals(matcher.group(2))).findFirst().orElseGet(() -> {
                    var n = new Node(matcher.group(2));
                    nodes.add(n);
                    return n;
                });
                node.right = nodes.stream().filter(n -> n.id.equals(matcher.group(3))).findFirst().orElseGet(() -> {
                    var n = new Node(matcher.group(3));
                    nodes.add(n);
                    return n;
                });
            } else {
                if (!line.isEmpty()) {
                    instructions = line;
                }
            }
        }
    }

    public int problem1() {
        var start = nodes.stream().filter(n -> n.id.equals("AAA")).findFirst().orElseThrow();
        var end = nodes.stream().filter(n -> n.id.equals("ZZZ")).findFirst().orElseThrow();
        var instructionPointer = new WrappingCounter("Instruction", instructions.length());
        int steps = 0;

        while (!start.equals(end)) {
            start = start.next(instructions.charAt(instructionPointer.value()));
            instructionPointer.increment();
            steps++;
        }
        return steps;
    }

    long problem2() {
        var starts = nodes.stream().filter(n -> n.id.endsWith("A")).toList();
        var ends = nodes.stream().filter(n -> n.id.endsWith("Z")).toList();
        List<Long> intervals = new ArrayList<>();

        for (var start : starts) {
            var instructionPointer = new WrappingCounter("Instruction", instructions.length());
            long steps = 0;

            while (!ends.contains(start)) {
                start = start.next(instructions.charAt(instructionPointer.value()));
                instructionPointer.increment();
                steps++;
            }
            intervals.add(steps);
        }
        return lcm(intervals);
    }

    @Data
    static class Node {
        final String id;
        Node left;
        Node right;

        Node next(char direction) {
            return switch (direction) {
                case 'L' -> left;
                case 'R' -> right;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
