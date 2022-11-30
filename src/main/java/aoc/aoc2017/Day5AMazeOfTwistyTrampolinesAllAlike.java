package aoc.aoc2017;

import java.util.List;
import java.util.stream.Collectors;

public class Day5AMazeOfTwistyTrampolinesAllAlike {

    final List<Integer> instructions;

    public Day5AMazeOfTwistyTrampolinesAllAlike(List<String> input) {
        instructions = input.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    int countSteps() {
        int current = 0;
        int steps = 0;

        while (current >= 0 && current < instructions.size()) {
            int offset = instructions.get(current);
            int next = current + offset;
            instructions.set(current, offset + 1);
            current = next;
            steps++;
        }
        return steps;
    }

    int countStepsBackwards() {
        int current = 0;
        int steps = 0;

        while (current >= 0 && current < instructions.size()) {
            int offset = instructions.get(current);
            int next = current + offset;
            if (offset >= 3) {
                instructions.set(current, offset - 1);
            } else {
                instructions.set(current, offset + 1);
            }
            current = next;
            steps++;
        }
        return steps;
    }
}
