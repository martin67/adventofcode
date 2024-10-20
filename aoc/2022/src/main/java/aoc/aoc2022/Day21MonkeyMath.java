package aoc.aoc2022;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class Day21MonkeyMath {
    final Map<String, Monkey> monkeys = new HashMap<>();

    Day21MonkeyMath(List<String> inputLines) {
        Pattern pattern1 = Pattern.compile("(\\w+): (\\w+) (\\W) (\\w+)");
        Pattern pattern2 = Pattern.compile("(\\w+): (\\d+)");

        for (String line : inputLines) {
            var matcher = pattern1.matcher(line);
            if (matcher.find()) {
                final String monkeyName = matcher.group(1);
                var start = monkeys.computeIfAbsent(monkeyName, m -> new Monkey(monkeyName));
                final String monkeyA = matcher.group(2);
                start.monkeyA = monkeys.computeIfAbsent(monkeyA, m -> new Monkey(monkeyA));
                final String monkeyB = matcher.group(4);
                start.monkeyB = monkeys.computeIfAbsent(monkeyB, m -> new Monkey(monkeyB));
                start.operand = matcher.group(3);
            }
            matcher = pattern2.matcher(line);
            if (matcher.find()) {
                final String monkeyName = matcher.group(1);
                var start = monkeys.computeIfAbsent(monkeyName, m -> new Monkey(monkeyName));
                start.number = Long.parseLong(matcher.group(2));
            }
        }
    }

    long problem1() {
        var root = monkeys.get("root");
        return root.yell();
    }

    long problem2() {

        //long x = bisection(0, Long.MAX_VALUE); // ok for problem1
        long x = bisection(0, Long.MAX_VALUE / 10000); // ok for problem2
        // Don't really understand why the end values has to be different. Is it something with the rounding,
        // as we are not using floating numbers??
        validate(x);
        return x;
    }

    void validate(long x) {
        var monkeyA = monkeys.get("root").monkeyA;
        var monkeyB = monkeys.get("root").monkeyB;
        var humn = monkeys.get("humn");
        humn.number = x;

        log.info("Check. monkey A yell({}) = {}", x, monkeyA.yell());
        log.info("Check. monkey B yell({}) = {}", x, monkeyB.yell());
    }

    long f(long x) {
        var monkeyA = monkeys.get("root").monkeyA;
        var monkeyB = monkeys.get("root").monkeyB;
        var humn = monkeys.get("humn");
        humn.number = x;
        return monkeyA.yell() - monkeyB.yell();
    }

    long bisection(long a, long b) {
        long maxIterations = 10000;
        long iterations = 0;
        while (iterations < maxIterations) {
            long c = (a + b) / 2;
            long fc = f(c);
            if (fc == 0) {
                log.info("A f({}) = 0, {} iterations", c, iterations);
                return c;
            }

            iterations++;
            if (fc > 0) {
                a = c;
            } else {
                b = c;
            }
        }
        return -1;
    }

    static class Monkey {
        final String name;
        long number;
        Monkey monkeyA;
        Monkey monkeyB;
        String operand;

        public Monkey(String name) {
            this.name = name;
        }

        long yell() {
            if (monkeyA == null && monkeyB == null) {
                return number;
            }
            long a = monkeyA.yell();
            long b = monkeyB.yell();
            return switch (operand) {
                case "+" -> Math.addExact(a, b);
                case "-" -> Math.subtractExact(a, b);
                case "*" -> Math.multiplyExact(a, b);
                case "/" -> a / b;
//                case "+" -> a + b;
//                case "-" -> a - b;
//                case "*" -> a * b;
//                case "/" -> a / b;
                default -> throw new IllegalArgumentException("Invalid operand: " + operand);
            };
        }
    }

}

