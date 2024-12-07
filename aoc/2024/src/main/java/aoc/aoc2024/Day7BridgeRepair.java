package aoc.aoc2024;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day7BridgeRepair {

    List<Equation> equations = new ArrayList<>();

    Day7BridgeRepair(List<String> inputLines) {
        for (String line : inputLines) {
            String[] parts = line.split(":");
            Equation equation = new Equation(Long.parseLong(parts[0]));
            for (String s : parts[1].split(" ")) {
                if (!s.isEmpty()) {
                    equation.numbers.add(Integer.parseInt(s));
                }
            }
            equations.add(equation);
        }
    }

    long problem1() {
        long sum = 0;
        for (Equation equation : equations) {
            Node node = buildTree(equation);
            if (node.evaluate1(equation.testValue, 0)) {
                sum += equation.testValue;
            }
        }
        return sum;
    }

    long problem2() {
        long sum = 0;
        for (Equation equation : equations) {
            Node node = buildTree(equation);
            if (node.evaluate2(equation.testValue, 0)) {
                sum += equation.testValue;
            }
        }
        return sum;
    }

    Node buildTree(Equation equation) {
        Node node = new Node();
        node.createChildren(equation, 0);
        return node;
    }

    static class Equation {
        long testValue;
        List<Integer> numbers = new ArrayList<>();

        public Equation(long testValue) {
            this.testValue = testValue;
        }
    }

    static class Node {
        int value;
        Node sum;
        Node mul;
        Node cat;

        void createChildren(Equation equation, int level) {
            value = equation.numbers.get(level);
            if (level < equation.numbers.size() - 1) {
                sum = new Node();
                sum.createChildren(equation, level + 1);
                mul = new Node();
                mul.createChildren(equation, level + 1);
                cat = new Node();
                cat.createChildren(equation, level + 1);
            }
        }

        boolean evaluate1(long target, long v) {
            long sumValue = v + value;
            long mulValue = v * value;
            if (sum == null) {
                return (sumValue == target || mulValue == target);
            } else {
                return (sum.evaluate1(target, v + value) || mul.evaluate1(target, v * value));
            }
        }

        boolean evaluate2(long target, long v) {
            long sumValue = v + value;
            long mulValue = v * value;
            long catValue = Long.parseLong(v + String.valueOf(value));
            if (sum == null) {
                return (sumValue == target || mulValue == target || catValue == target);
            } else {
                return (sum.evaluate2(target, sumValue) || mul.evaluate2(target, mulValue) || cat.evaluate2(target, catValue));
            }
        }
    }
}
