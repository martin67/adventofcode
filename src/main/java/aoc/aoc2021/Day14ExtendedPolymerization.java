package aoc.aoc2021;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day14ExtendedPolymerization {

    String polymer;
    Map<String, Rule> rules = new HashMap<>();

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

    long problem1(int steps) {
        String p = polymer;
        StringBuilder nextPolymer = new StringBuilder();
        for (int i = 0; i < steps; i++) {
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


    String problem2() {
        Map<Character, BigInteger> frequencies = new HashMap<>();
        Map<String, Map<Character, BigInteger>> patternCache = new HashMap<>();
        for (int j = 0; j < polymer.length() - 1; j++) {
            String pair = polymer.substring(j, j + 2);
            frequencies = finder(pair, frequencies, patternCache, 0);
        }

        //BigInteger max = frequencies.values().stream().map(BigInteger::toString).max().orElseThrow(NoSuchElementException::new);
        //BigInteger min = frequencies.values().stream().map(BigInteger::toString).min().orElseThrow(NoSuchElementException::new);
        BigInteger max = null;
        BigInteger min = null;
        for (BigInteger bigInteger : frequencies.values()) {
            if (max == null) {
                max = bigInteger;
            }
            if (min == null) {
                min = bigInteger;
            }
            if (bigInteger.compareTo(max) > 0) {
                max = bigInteger;
            }
            if (bigInteger.compareTo(min) < 0) {
                min = bigInteger;
            }
        }
        return max.subtract(min).toString();
        //return "";
    }

    String problem22() {
        Map<Character, BigInteger> frequencies = new HashMap<>();
        Map<String, Map<Character, BigInteger>> patternCache = new HashMap<>();
        Map<String, String> cache = new HashMap<>();
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < polymer.length() - 1; j++) {
            String pair = polymer.substring(j, j + 2);
            //log.info("####################### {}", pair);
            result.append(finder2(pair, cache, 1));
        }
        //log.info("********************** {}", result);
        Map<Character, Long> frequency = new HashMap<>();
        for (char c : result.toString().toCharArray()) {
            frequency.merge(c, 1L, Long::sum);
        }
        long max = frequency.values().stream().mapToLong(v -> v).max().orElseThrow(NoSuchElementException::new);
        long min = frequency.values().stream().mapToLong(v -> v).min().orElseThrow(NoSuchElementException::new);
        return String.valueOf(max - min);
    }

    Map<Character, BigInteger> finder(String input, Map<Character, BigInteger> frequencies, Map<String, Map<Character, BigInteger>> patternCache, int depth) {
        log.info("finder - in; {}, freq: {}, cache: {}, depth: {}", input, frequencies, patternCache, depth);
        Map<Character, BigInteger> newFrequencies = new HashMap<>();
        String cacheKey = input + "-" + depth;
        log.info("Checking cache for {}", cacheKey);
        if (patternCache.containsKey(cacheKey)) {
            log.info("Cache hit for {}, {}", cacheKey, patternCache.get(cacheKey));
            return patternCache.get(cacheKey);
        }
        if (depth < 5) {
            depth++;
        } else {
            //Map<Character, BigInteger> newFrequencies = new HashMap<>();
            for (char c : input.toCharArray()) {
                //newFrequencies.merge(c, 1L, Long::sum);
                if (newFrequencies.containsKey(c)) {
                    newFrequencies.put(c, newFrequencies.get(c).add(new BigInteger("1")));
                } else {
                    newFrequencies.put(c, new BigInteger("1"));
                }
            }
            log.info("reached bottom, returning {}", newFrequencies);
            return newFrequencies;
        }

        String newInput = "" + input.charAt(0) + rules.get(input.substring(0, 2)).output + input.charAt(1);
        Map<Character, BigInteger> left = finder(newInput.substring(0, 2), new HashMap<>(frequencies), patternCache, depth);
        Map<Character, BigInteger> right = finder(newInput.substring(1, 3), new HashMap<>(frequencies), patternCache, depth);

        if (left != null) {
            //patternCache.put(newInput.substring(0, 2), left);
            left.forEach(
                    (key, value) -> frequencies.merge(key, value, BigInteger::add)
            );
        }
        if (right != null) {
            //patternCache.put(newInput.substring(1, 3), right);
            right.forEach(
                    (key, value) -> frequencies.merge(key, value, BigInteger::add)
            );
        }
        cacheKey = newInput + "-" + (depth - 1);

        log.info("adding {}, {} to cache", cacheKey, frequencies);
        patternCache.put(cacheKey, frequencies);

        return frequencies;
    }

    String finder2(String input, Map<String, String> cache, int depth) {
        //log.info("finder - in; {}, cache: {}, depth: {}", input, cache, depth);
        String cacheKey = input + "-" + depth;
        //log.info("Checking cache for {}", cacheKey);
        if (cache.containsKey(cacheKey)) {
            log.info("Cache hit for {}, {}", cacheKey, cache.get(cacheKey));
            return cache.get(cacheKey);
        }
        if (depth < 41) {
            depth++;
        } else {
            //log.info("reached bottom, returning {}", input);
            return input;
        }

        String newInput = "" + input.charAt(0) + rules.get(input.substring(0, 2)).output + input.charAt(1);
        String leftIn = newInput.substring(0, 2);
        String rightIn = newInput.substring(1, 3);

        //Map<Character, BigInteger> left = finder(newInput.substring(0, 2), new HashMap<>(frequencies), patternCache, depth);
        //Map<Character, BigInteger> right = finder(newInput.substring(1, 3), new HashMap<>(frequencies), patternCache, depth);
        String left = finder2(leftIn, cache, depth);
        String right = finder2(rightIn, cache, depth);

        cacheKey = newInput + "-" + (depth - 1);
        String cacheValue = left + right.substring(1);
        if (!cache.containsKey(cacheKey)) {
            log.info("adding {}, {} to cache", cacheKey, cacheValue);
            cache.put(cacheKey, cacheValue);
        }

        return cacheValue;
    }

    String problem23() {
        Map<Character, Long> result = new HashMap<>();
        Map<String, Map<Character, Long>> cache = new HashMap<>();
        for (int j = 0; j < polymer.length() - 1; j++) {
            String pair = polymer.substring(j, j + 2);
            log.info("####################### {}", pair);
            Map<Character, Long> r = finder3(pair, cache, 1);
            r.forEach(
                    (key, value) -> result.merge(key, value, Long::sum)
            );
        }
        log.info("********************** {}", result);
        long max = result.values().stream().mapToLong(v -> v).max().orElseThrow(NoSuchElementException::new);
        long min = result.values().stream().mapToLong(v -> v).min().orElseThrow(NoSuchElementException::new);
        return String.valueOf(max - min);
    }

    Map<Character, Long> finder3(String input, Map<String, Map<Character, Long>> cache, int depth) {
        //log.info("finder - in; {}, cache: {}, depth: {}", input, cache, depth);
        String cacheKey = input + "-" + (depth - 1);
//        if (cache.size() % 1000 == 0) {
//            log.info("cache size: {}", cache.size());
//        }
        //log.info("Checking cache for {}", cacheKey);
        if (depth < 41) {
            depth++;
        } else {
            //log.info("reached bottom, returning {}", input);
            Map<Character, Long> result = new HashMap<>();
            for (char c : input.toCharArray()) {
                result.merge(c, 1L, Long::sum);
            }
            return result;
        }

        char middle = rules.get(input.substring(0, 2)).output;
        String newInput = "" + input.charAt(0) + middle + input.charAt(1);
        String leftIn = newInput.substring(0, 2);
        String rightIn = newInput.substring(1, 3);

        Map<Character, Long> left = finder3(leftIn, cache, depth);
        Map<Character, Long> right = finder3(rightIn, cache, depth);

        cacheKey = newInput + "-" + (depth - 1);
        left.forEach(
                (key, value) -> right.merge(key, value, Long::sum)
        );
        // subtract middle
        right.put(middle, right.get(middle) - 1);
        if (!cache.containsKey(cacheKey)) {
            //log.info("adding {}, {} to cache", cacheKey, right);
            cache.put(cacheKey, right);
        }
        return right;
    }

    static class Rule {
        String input;
        char output;

        public Rule(String input, char output) {
            this.input = input;
            this.output = output;
        }
    }
}
