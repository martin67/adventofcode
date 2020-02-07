package aoc.aoc2015;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
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

    int isNice(List<String> inputLines) {
        int niceCount = 0;
        for (String line : inputLines) {
            if (isNice(line)) {
                niceCount++;
            }
        }
        return niceCount;
    }

    boolean isNiceNewRule(String input) {

        boolean pairOk = false;
        boolean gapOk = false;
        char[] charArray = input.toCharArray();
        Set<String> pairs = new HashSet<>();
        String previousPair = "";
        int okCount = 0;

        log.info("## Looking at {}", input);
        for (int i = 1; i < charArray.length; i++) {
            String pair = new String(charArray, i - 1, 2);
            if (pairs.contains(pair) && !pair.equals(previousPair)) {
                log.info("Found pair {}", pair);
                okCount++;
            } else {
                pairs.add(pair);
                previousPair = pair;
            }
        }
        if (okCount == 1) {
            pairOk = true;
        }

        for (int i = 2; i < charArray.length; i++) {
            if (charArray[i] == charArray[i - 2]) {
                log.info("Found match {}, pos {}", charArray[i], i);
                gapOk = true;
               // break;
            }
        }
        log.info("{} : {} - {}", input, pairOk, gapOk);

        return (pairOk && gapOk);
    }


    int isNiceNewRule(List<String> inputLines) {
        int niceCount = 0;
        for (String line : inputLines) {
            if (isNiceNewRule(line)) {
                niceCount++;
            }
            log.info("{} : {}", line, niceCount);
        }
        return niceCount;
    }
}

// 158 too low
// 68 not correct, not 71, not 60