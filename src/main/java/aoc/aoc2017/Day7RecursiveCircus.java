package aoc.aoc2017;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day7RecursiveCircus {

    static class Program {
        String name;
        int weight;
        int weightOfChildren;
        // child, weight
        Map<Program, Integer> children;

        public Program(String name, int weight) {
            this.name = name;
            this.weight = weight;
            this.children = new HashMap<>();
        }

        public Program(String name) {
            this.name = name;
            this.children = new HashMap<>();
        }
    }

    final private Map<String, Program> tower;

    public Day7RecursiveCircus(List<String> input) {
        tower = new HashMap<>();

        for (String row : input) {
            String[] splitRow = row.split(" ");
            String name = splitRow[0];
            int weight = Integer.parseInt(splitRow[1].substring(1, splitRow[1].length() - 1));
            Program program;

            if (tower.containsKey(name)) {
                program = tower.get(name);
                program.weight = weight;
            } else {
                program = new Program(name, weight);
                tower.put(name, program);
            }

            if (row.contains("->")) {
                String[] children = row.substring(row.indexOf("->") + 2).split(",");
                for (String childName : children) {
                    Program child;
                    childName = childName.trim();
                    if (tower.containsKey(childName)) {
                        child = tower.get(childName);
                    } else {
                        child = new Program(childName);
                        tower.put(childName, child);
                    }
                    program.children.put(child, 0);
                }
            }
        }
    }

    public String bottomProgramName() {
        Program bottom = null;
        int maxChildren = 0;
        for (Program program : tower.values()) {
            int children = numberOfChildren(program);
            if (children > maxChildren) {
                bottom = program;
                maxChildren = children;
            }
        }
        assert bottom != null;
        return bottom.name;
    }

    private int numberOfChildren(Program program) {
        int children = 0;
        if (!program.children.isEmpty()) {
            for (Program child : program.children.keySet()) {
                children += 1 + numberOfChildren(child);
            }
        }
        return children;
    }

    private int recursiveWeight(Program program) {
        int totalWeight = 0;

        for (Program child : program.children.keySet()) {
            int weight = recursiveWeight(child);
            program.children.put(child, weight);
            totalWeight += weight;
        }
        program.weightOfChildren = totalWeight;
        totalWeight += program.weight;

        return totalWeight;
    }

    private int checkforImbalance() {
        int lowestWeight = Integer.MAX_VALUE;
        int changedWeight = 0;

        for (Program program : tower.values()) {
            if (!program.children.isEmpty()) {
                int min = program.children.values().stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new);
                int max = program.children.values().stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
                if (min != max) {
                    log.info("Program {} has an imbalance", program.name);
                    long minCount = program.children.values().stream().filter(v -> v == min).count();
                    long maxCount = program.children.values().stream().filter(v -> v == max).count();
                    for (Program child : program.children.keySet()) {
                        if (minCount == 1L && program.children.get(child) == min) {
                            log.info("Found min {} for child {}", min, child.name);
                            int change = child.weight + (max - min);
                            log.info("{} should change from {} to {} to balance", child.name, child.weight, change);
                            if (max < lowestWeight) {
                                lowestWeight = min;
                                changedWeight = change;
                            }
                        }
                        if (maxCount == 1L && program.children.get(child) == max) {
                            log.info("Found max {} for child {}", max, child.name);
                            int change = child.weight - (max - min);
                            log.info("{} should change from {} to {} to balance", child.name, child.weight, change);
                            if (max < lowestWeight) {
                                lowestWeight = max;
                                changedWeight = change;
                            }
                        }
                    }
                }
            }
        }
        return changedWeight;
    }

    public int computeWeights() {
        recursiveWeight(tower.get(bottomProgramName()));
        return checkforImbalance();
    }

}
