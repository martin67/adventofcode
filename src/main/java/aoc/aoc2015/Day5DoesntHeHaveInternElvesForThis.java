package aoc.aoc2015;

import java.util.List;

public class Day5DoesntHeHaveInternElvesForThis {
    boolean isNice(String input) {
        if (input.matches(".*(ab|cd|pq|xy).*")) {
            return false;
        }

        int vowels = 0;
        boolean twiceInARow = false;
        char previousChar = '0';

        for (char c : input.toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowels++;
            }
            if (c == previousChar) {
                twiceInARow = true;
            }
            previousChar = c;
        }
        return (vowels > 2 && twiceInARow);
    }

    boolean isNiceNewRule(String input) {
        if (input.matches(".*(ab|cd|pq|xy).*")) {
            return false;
        }

        int vowels = 0;
        boolean twiceInARow = false;
        char previousChar = '0';

        for (char c : input.toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowels++;
            }
            if (c == previousChar) {
                twiceInARow = true;
            }
            previousChar = c;
        }
        return (vowels > 2 && twiceInARow);
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

    int isNiceNewRule(List<String> inputLines) {
        int niceCount = 0;
        for (String line : inputLines) {
            if (isNiceNewRule(line)) {
                niceCount++;
            }
        }
        return niceCount;
    }
}

// 158 too low