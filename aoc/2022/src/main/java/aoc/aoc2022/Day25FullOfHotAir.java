package aoc.aoc2022;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day25FullOfHotAir {
    final List<String> snafus;

    public Day25FullOfHotAir(List<String> inputLines) {
        snafus = inputLines;
    }

    String problem1() {
        long sum = 0;
        for (String snafu : snafus) {
            sum += fromSnafu(snafu);
        }
        return toSnafu(sum);
    }

    long fromSnafu(String snafu) {
        long result = 0;
        Map<Character, Integer> snafuMap = Map.of('2', 2, '1', 1, '0', 0, '-', -1, '=', -2);
        for (int i = 0; i < snafu.length(); i++) {
            long a = (long) Math.pow(5, i);
            long b = snafuMap.get(snafu.charAt(snafu.length() - i - 1));
            result += a * b;
        }
        return result;
    }

    String toSnafu(long s) {
        String result = Long.toString(s, 5);
        Deque<String> res = new ArrayDeque<>();
        int nextDigit = 0;
        for (int i = 0; i < result.length(); i++) {
            int digit = Character.getNumericValue(result.charAt(result.length() - i - 1)) + nextDigit;
            if (digit < 3) {
                res.add(Integer.toString(digit));
                nextDigit = 0;
            } else if (digit == 3) {
                res.add("=");
                nextDigit = 1;
            } else if (digit == 4) {
                res.add("-");
                nextDigit = 1;
            } else {
                res.add("0");
                nextDigit = 1;
            }
        }
        if (nextDigit != 0) {
            res.add(Integer.toString(nextDigit));
        }
        StringBuilder sb = new StringBuilder();
        while (!res.isEmpty()) {
            sb.append(res.removeLast());
        }

        return sb.toString();
    }
}
