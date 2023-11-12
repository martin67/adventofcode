package aoc.aoc2021;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day14ExtendedPolymerization {

    private final Map<String, Rule> rules = new HashMap<>();
    private String polymer;

    public Day14ExtendedPolymerization(List<String> inputLines) {
        Pattern polymerPattern = Pattern.compile("^(\\w+)$");
        Pattern rulePattern = Pattern.compile("(\\w+) -> (\\w)");
        Matcher matcher;
        for (String line : inputLines) {
            matcher = polymerPattern.matcher(line);
            if (matcher.find()) {
                polymer = matcher.group(1);
            }
            matcher = rulePattern.matcher(line);
            if (matcher.find()) {
                rules.put(matcher.group(1), new Rule(matcher.group(1), matcher.group(2).charAt(0)));
            }
        }
    }

    public long problem1() {
        String p = polymer;
        StringBuilder nextPolymer = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < p.length() - 1; j++) {
                String pair = p.substring(j, j + 2);
                if (rules.containsKey(pair)) {
                    nextPolymer.append(p.charAt(j)).append(rules.get(pair).output);
                }
            }
            nextPolymer.append(p.substring(p.length() - 1));
            p = nextPolymer.toString();
            nextPolymer.setLength(0);
            //log.info("After step {}: {}", i, p);
            log.info("After step {}, length: {}", i, p.length());
        }
        Map<Character, Long> frequency = new HashMap<>();
        for (char c : p.toCharArray()) {
            frequency.merge(c, 1L, Long::sum);
        }
        long max = frequency.values().stream().mapToLong(v -> v).max().orElseThrow(NoSuchElementException::new);
        long min = frequency.values().stream().mapToLong(v -> v).min().orElseThrow(NoSuchElementException::new);
        return max - min;
    }

    public long problem2() {
        Map<String, Long> freq = new HashMap<>();
        for (int j = 0; j < polymer.length() - 1; j++) {
            String pair = polymer.substring(j, j + 2);
            freq.merge(pair, 1L, Long::sum);
        }

        Map<Character, Long> result = new HashMap<>();
        for (int i = 0; i < 41; i++) {
            Map<String, Long> newFreq = new HashMap<>();
            result.clear();
            for (String key : rules.keySet()) {
                char elem = rules.get(key).output;
                String left = "" + key.charAt(0) + elem;
                String right = "" + elem + key.charAt(1);
                long val = freq.getOrDefault(key, 0L);
                if (val > 0) {
                    result.put(key.charAt(0), result.getOrDefault(key.charAt(0), 0L) + val);
                    newFreq.put(left, newFreq.getOrDefault(left, 0L) + val);
                    newFreq.put(right, newFreq.getOrDefault(right, 0L) + val);
                }
            }
            freq = newFreq;
        }
        char lastChar = polymer.charAt(polymer.length() - 1);
        result.put(lastChar, result.get(lastChar) + 1);

        long max = result.values().stream().mapToLong(v -> v).max().orElseThrow(NoSuchElementException::new);
        long min = result.values().stream().mapToLong(v -> v).min().orElseThrow(NoSuchElementException::new);
        return max - min;
    }

    record Rule(String input, char output) {
    }
}
