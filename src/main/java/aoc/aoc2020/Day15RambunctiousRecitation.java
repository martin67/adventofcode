package aoc.aoc2020;

import java.util.HashMap;
import java.util.Map;

public class Day15RambunctiousRecitation {
    // number, round
    Map<Integer, Integer> lastSpoken = new HashMap<>();
    Map<Integer, Integer> lastSpokenBeforeThen = new HashMap<>();
    int spokeLast;

    public Day15RambunctiousRecitation(String numbers) {
        int index = 1;
        for (String s : numbers.split(",")) {
            int number = Integer.parseInt(s);
            lastSpoken.put(number, index);
            lastSpokenBeforeThen.put(number, -index);
            spokeLast = number;
            index++;
        }
    }

    int numberSpoken(int rounds) {
        int lastNumberSpoken;
        for (int turn = lastSpoken.size() + 1; turn < rounds + 1; turn++) {

            // last number spoken?
            lastNumberSpoken = spokeLast;
            if (lastSpokenBeforeThen.get(lastNumberSpoken) < 0) {
                // Speak 0
                if (lastSpoken.containsKey(0)) {
                    lastSpokenBeforeThen.put(0, lastSpoken.get(0));
                } else {
                    lastSpokenBeforeThen.put(0, - turn);
                }
                lastSpoken.put(0, turn);
                spokeLast = 0;
            } else {
                // speak difference
                int difference = lastSpoken.get(lastNumberSpoken) - lastSpokenBeforeThen.get(lastNumberSpoken);
                if (lastSpoken.containsKey(difference)) {
                    lastSpokenBeforeThen.put(difference, lastSpoken.get(difference));
                } else {
                    lastSpokenBeforeThen.put(difference, - turn);
                }
                lastSpoken.put(difference, turn);
                spokeLast = difference;
            }
        }
        return spokeLast;
    }
}
