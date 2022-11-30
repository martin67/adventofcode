package aoc.aoc2015;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class Day5DoesntHeHaveInternElvesForThis {
    public boolean isNice(String input) {
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

    public int isNice(List<String> inputLines) {
        int niceCount = 0;
        for (String line : inputLines) {
            if (isNice(line)) {
                niceCount++;
            }
        }
        return niceCount;
    }

    public boolean isNiceNewRule(String input) {

        boolean pairOk = false;
        boolean gapOk = false;
        char[] charArray = input.toCharArray();
        Set<String> pairs = new HashSet<>();
        int okCount = 0;

        for (int i = 1; i < charArray.length; i++) {
            String pair = new String(charArray, i - 1, 2);
            if (pairs.contains(pair)) {
                if (i == 2 && charArray[2] == charArray[0]) {
                    log.debug("no hit! - three first, {}", i);
                } else if (charArray[i] == charArray[i - 1] && charArray[i] == charArray[i - 2] && charArray[i] != charArray[i - 3]) {
                    log.debug("no hit! - pos {}", i);
                } else {
                    okCount++;
                }
            } else {
                pairs.add(pair);
            }
        }
        if (okCount > 0) {
            pairOk = true;
        }

        for (int i = 2; i < charArray.length; i++) {
            if (charArray[i] == charArray[i - 2]) {
                gapOk = true;
                break;
            }
        }

        return (pairOk && gapOk);
    }


    public int isNiceNewRule(List<String> inputLines) {
        int niceCount = 0;
        for (String line : inputLines) {
            if (isNiceNewRule(line)) {
                niceCount++;
            }
        }
        return niceCount;
    }
}
