package aoc.aoc2015;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day5DoesntHeHaveInternElvesForThis {
    boolean isNice(String input) {
        if (input.matches(".*(ab|cd|pq|xy).*")) {
            return false;
        }

        Set<Character> vowels = new HashSet<>();
        boolean twiceInARow = false;
        char previousChar = '0';

        for (char c : input.toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowels.add(c);
            }
            if (c == previousChar) {
                twiceInARow = true;
            }
            previousChar = c;
        }
        return (vowels.size() > 2 && twiceInARow);
    }

    int isNice(List<String> inputLines) {
        int niceCount = 0;
        for (String line : inputLines) {
            if (isNice(line)) {
                niceCount++;
            }
        }
        return niceCount;
    }
}

// 158 too low