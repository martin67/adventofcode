package aoc.aoc2022;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class Day6TuningTrouble {

    String line;

    public Day6TuningTrouble(List<String> inputLines) {
        line = inputLines.get(0);
    }

    int problem(int length) {
        for (int i = 0; i < line.length() - length; i++) {
            Set<Character> chars = new HashSet<>();
            for (int j = i; j < i + length; j++) {
                chars.add(line.charAt(j));
            }
            if (chars.size() == length) {
                return i + length;
            }
        }
        return -1;
    }
}
