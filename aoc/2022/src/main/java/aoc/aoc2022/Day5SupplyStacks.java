package aoc.aoc2022;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day5SupplyStacks {

    final Map<Integer, Deque<Character>> stacks = new HashMap<>();
    final List<Move> moves = new ArrayList<>();

    public Day5SupplyStacks(List<String> inputLines) {
        Pattern pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
        Matcher matcher;

        for (String line : inputLines) {
            if (line.contains("[")) {
                int index = 0;
                for (Character c : line.toCharArray()) {
                    if (c >= 'A' && c <= 'Z') {
                        int stackNumber = (index - 1) / 4 + 1;
                        if (stacks.containsKey(stackNumber)) {
                            stacks.get(stackNumber).add(c);
                        } else {
                            stacks.put(stackNumber, new ArrayDeque<>());
                            stacks.get(stackNumber).add(c);
                        }
                    }
                    index++;
                }
            } else if (line.startsWith("move")) {
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    moves.add(new Move(Integer.parseInt(matcher.group(1)),
                            Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(3))));
                }
            }
        }
    }

    String problem1() {
        for (Move move : moves) {
            Deque<Character> fromStack = stacks.get(move.from);
            Deque<Character> toStack = stacks.get(move.to);
            for (int i = 0; i < move.amount; i++) {
                toStack.push(fromStack.pop());
            }
        }
        return getAnswer();
    }

    String problem2() {
        for (Move move : moves) {
            Deque<Character> fromStack = stacks.get(move.from);
            Deque<Character> toStack = stacks.get(move.to);
            Deque<Character> tempStack = new ArrayDeque<>();
            for (int i = 0; i < move.amount; i++) {
                tempStack.push(fromStack.pop());
            }
            for (int i = 0; i < move.amount; i++) {
                toStack.push(tempStack.pop());
            }
        }
        return getAnswer();
    }

    @NotNull
    private String getAnswer() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stacks.size(); i++) {
            sb.append(stacks.get(i + 1).peek());
        }
        return sb.toString();
    }

    record Move(int amount, int from, int to) {
    }
}
