package aoc.aoc2021;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day8SevenSegmentSearch {
    static int SEGMENT_TOP = 0;
    static int SEGMENT_UPPER_LEFT = 1;
    static int SEGMENT_UPPER_RIGHT = 2;
    static int SEGMENT_MIDDLE = 3;
    static int SEGMENT_LOWER_LEFT = 4;
    static int SEGMENT_LOWER_RIGHT = 5;
    static int SEGMENT_BOTTOM = 6;

    List<DigitEntry> entries = new ArrayList<>();

    public Day8SevenSegmentSearch(List<String> inputLines) {
        inputLines.forEach(line -> {
            String[] s = line.split("\\|");
            String[] patterns = s[0].trim().split(" ");
            String[] outputs = s[1].trim().split(" ");
            DigitEntry digitEntry = new DigitEntry();
            for (String pattern : patterns) {
                char[] sortedPattern = pattern.toCharArray();
                Arrays.sort(sortedPattern);
                digitEntry.patterns.add(new String(sortedPattern));
            }
            for (String output : outputs) {
                char[] sortedOutput = output.toCharArray();
                Arrays.sort(sortedOutput);
                digitEntry.outputs.add(new String(sortedOutput));
            }
            entries.add(digitEntry);
        });
    }

    int problem1() {
        int digits = 0;
        for (DigitEntry digitEntry : entries) {
            for (String output : digitEntry.outputs) {
                if (output.length() == 2 || output.length() == 3 || output.length() == 4 | output.length() == 7) {
                    digits++;
                }
            }
        }
        return digits;
    }

    int problem2() {
        int totalSum = 0;
        Set<Integer> number_zero = new HashSet<>();
        number_zero.add(SEGMENT_TOP);
        number_zero.add(SEGMENT_UPPER_LEFT);
        number_zero.add(SEGMENT_UPPER_RIGHT);
        number_zero.add(SEGMENT_LOWER_LEFT);
        number_zero.add(SEGMENT_LOWER_RIGHT);
        number_zero.add(SEGMENT_BOTTOM);
        Set<Integer> number_one = new HashSet<>();
        number_one.add(SEGMENT_UPPER_RIGHT);
        number_one.add(SEGMENT_LOWER_RIGHT);
        Set<Integer> number_two = new HashSet<>();
        number_two.add(SEGMENT_TOP);
        number_two.add(SEGMENT_UPPER_RIGHT);
        number_two.add(SEGMENT_MIDDLE);
        number_two.add(SEGMENT_LOWER_LEFT);
        number_two.add(SEGMENT_BOTTOM);
        Set<Integer> number_three = new HashSet<>();
        number_three.add(SEGMENT_TOP);
        number_three.add(SEGMENT_UPPER_RIGHT);
        number_three.add(SEGMENT_MIDDLE);
        number_three.add(SEGMENT_LOWER_RIGHT);
        number_three.add(SEGMENT_BOTTOM);
        Set<Integer> number_four = new HashSet<>();
        number_four.add(SEGMENT_UPPER_LEFT);
        number_four.add(SEGMENT_UPPER_RIGHT);
        number_four.add(SEGMENT_MIDDLE);
        number_four.add(SEGMENT_LOWER_RIGHT);
        Set<Integer> number_five = new HashSet<>();
        number_five.add(SEGMENT_TOP);
        number_five.add(SEGMENT_UPPER_LEFT);
        number_five.add(SEGMENT_MIDDLE);
        number_five.add(SEGMENT_LOWER_RIGHT);
        number_five.add(SEGMENT_BOTTOM);
        Set<Integer> number_six = new HashSet<>();
        number_six.add(SEGMENT_TOP);
        number_six.add(SEGMENT_UPPER_LEFT);
        number_six.add(SEGMENT_MIDDLE);
        number_six.add(SEGMENT_LOWER_LEFT);
        number_six.add(SEGMENT_LOWER_RIGHT);
        number_six.add(SEGMENT_BOTTOM);
        Set<Integer> number_seven = new HashSet<>();
        number_seven.add(SEGMENT_TOP);
        number_seven.add(SEGMENT_UPPER_RIGHT);
        number_seven.add(SEGMENT_LOWER_RIGHT);
        Set<Integer> number_eight = new HashSet<>();
        number_eight.add(SEGMENT_TOP);
        number_eight.add(SEGMENT_UPPER_LEFT);
        number_eight.add(SEGMENT_UPPER_RIGHT);
        number_eight.add(SEGMENT_MIDDLE);
        number_eight.add(SEGMENT_LOWER_LEFT);
        number_eight.add(SEGMENT_LOWER_RIGHT);
        number_eight.add(SEGMENT_BOTTOM);
        Set<Integer> number_nine = new HashSet<>();
        number_nine.add(SEGMENT_TOP);
        number_nine.add(SEGMENT_UPPER_LEFT);
        number_nine.add(SEGMENT_UPPER_RIGHT);
        number_nine.add(SEGMENT_MIDDLE);
        number_nine.add(SEGMENT_LOWER_RIGHT);
        number_nine.add(SEGMENT_BOTTOM);

        Map<Set<Integer>, Integer> allNumbers = new HashMap<>();
        allNumbers.put(number_zero, 0);
        allNumbers.put(number_one, 1);
        allNumbers.put(number_two, 2);
        allNumbers.put(number_three, 3);
        allNumbers.put(number_four, 4);
        allNumbers.put(number_five, 5);
        allNumbers.put(number_six, 6);
        allNumbers.put(number_seven, 7);
        allNumbers.put(number_eight, 8);
        allNumbers.put(number_nine, 9);

        for (DigitEntry digitEntry : entries) {

            Map<Integer, Map<String, Integer>> probs = new HashMap<>();
            for (int i = 0; i < 7; i++) {
                probs.put(i, new HashMap<>());
            }
            for (String pattern : digitEntry.patterns) {
                switch (pattern.length()) {
                    case 2: // 1
                        for (int i = 0; i < 3; i++) {
                            probs.put(SEGMENT_UPPER_RIGHT, addProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                            probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));
                        }
                        break;

                    case 3: // 7
                        for (int i = 0; i < 3; i++) {
                            probs.put(SEGMENT_TOP, addProbs(probs.get(SEGMENT_TOP), pattern));
                            probs.put(SEGMENT_UPPER_RIGHT, addProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                            probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));
                        }
                        break;

                    case 4: // 4
                        for (int i = 0; i < 3; i++) {

                            probs.put(SEGMENT_UPPER_LEFT, addProbs(probs.get(SEGMENT_UPPER_LEFT), pattern));
                            probs.put(SEGMENT_MIDDLE, addProbs(probs.get(SEGMENT_MIDDLE), pattern));
                            probs.put(SEGMENT_UPPER_RIGHT, addProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                            probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));
                        }
                        break;

                    case 5: // 2, 3 or 5
                        //  2
                        probs.put(SEGMENT_TOP, addProbs(probs.get(SEGMENT_TOP), pattern));
                        probs.put(SEGMENT_UPPER_RIGHT, addProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                        probs.put(SEGMENT_MIDDLE, addProbs(probs.get(SEGMENT_MIDDLE), pattern));
                        probs.put(SEGMENT_LOWER_LEFT, addProbs(probs.get(SEGMENT_LOWER_LEFT), pattern));
                        probs.put(SEGMENT_BOTTOM, addProbs(probs.get(SEGMENT_BOTTOM), pattern));

                        // 3
                        probs.put(SEGMENT_TOP, addProbs(probs.get(SEGMENT_TOP), pattern));
                        probs.put(SEGMENT_UPPER_RIGHT, addProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                        probs.put(SEGMENT_MIDDLE, addProbs(probs.get(SEGMENT_MIDDLE), pattern));
                        probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));
                        probs.put(SEGMENT_BOTTOM, addProbs(probs.get(SEGMENT_BOTTOM), pattern));

                        // 5
                        probs.put(SEGMENT_TOP, addProbs(probs.get(SEGMENT_TOP), pattern));
                        probs.put(SEGMENT_UPPER_LEFT, addProbs(probs.get(SEGMENT_UPPER_LEFT), pattern));
                        probs.put(SEGMENT_MIDDLE, addProbs(probs.get(SEGMENT_MIDDLE), pattern));
                        probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));
                        probs.put(SEGMENT_BOTTOM, addProbs(probs.get(SEGMENT_BOTTOM), pattern));
                        break;

                    case 6: // 0, 6 or 9
                        // 0
                        probs.put(SEGMENT_TOP, addProbs(probs.get(SEGMENT_TOP), pattern));
                        probs.put(SEGMENT_UPPER_LEFT, addProbs(probs.get(SEGMENT_UPPER_LEFT), pattern));
                        probs.put(SEGMENT_UPPER_RIGHT, addProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                        probs.put(SEGMENT_LOWER_LEFT, addProbs(probs.get(SEGMENT_LOWER_LEFT), pattern));
                        probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));
                        probs.put(SEGMENT_BOTTOM, addProbs(probs.get(SEGMENT_BOTTOM), pattern));

                        // 6
                        probs.put(SEGMENT_TOP, addProbs(probs.get(SEGMENT_TOP), pattern));
                        probs.put(SEGMENT_UPPER_LEFT, addProbs(probs.get(SEGMENT_UPPER_LEFT), pattern));
                        probs.put(SEGMENT_MIDDLE, addProbs(probs.get(SEGMENT_MIDDLE), pattern));
                        probs.put(SEGMENT_LOWER_LEFT, addProbs(probs.get(SEGMENT_LOWER_LEFT), pattern));
                        probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));
                        probs.put(SEGMENT_BOTTOM, addProbs(probs.get(SEGMENT_BOTTOM), pattern));

                        // 9
                        probs.put(SEGMENT_TOP, addProbs(probs.get(SEGMENT_TOP), pattern));
                        probs.put(SEGMENT_UPPER_LEFT, addProbs(probs.get(SEGMENT_UPPER_LEFT), pattern));
                        probs.put(SEGMENT_UPPER_RIGHT, addProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                        probs.put(SEGMENT_MIDDLE, addProbs(probs.get(SEGMENT_MIDDLE), pattern));
                        probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));
                        probs.put(SEGMENT_BOTTOM, addProbs(probs.get(SEGMENT_BOTTOM), pattern));
                        break;

                    case 7: // 8
                        for (int i = 0; i < 3; i++) {
                            probs.put(SEGMENT_TOP, addProbs(probs.get(SEGMENT_TOP), pattern));
                            probs.put(SEGMENT_UPPER_LEFT, addProbs(probs.get(SEGMENT_UPPER_LEFT), pattern));
                            probs.put(SEGMENT_UPPER_RIGHT, addProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                            probs.put(SEGMENT_MIDDLE, addProbs(probs.get(SEGMENT_MIDDLE), pattern));
                            probs.put(SEGMENT_LOWER_LEFT, addProbs(probs.get(SEGMENT_LOWER_LEFT), pattern));
                            probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));
                            probs.put(SEGMENT_BOTTOM, addProbs(probs.get(SEGMENT_BOTTOM), pattern));
                        }
                        break;
                }
            }

            Map<String, Integer> answer = new HashMap<>();

            for (int x = 0; x < 7; x++) {
                int biggest_prob = Integer.MIN_VALUE;
                String biggest_prob_key = null;
                int biggest_segment = -1;

                for (int i = 0; i < 7; i++) {
                    // find biggest prob:
                    if (!probs.get(i).isEmpty()) {
                        Map.Entry<String, Integer> hej = Collections.max(probs.get(i).entrySet(), Map.Entry.comparingByValue());
                        if (hej.getValue() > biggest_prob) {
                            biggest_prob = hej.getValue();
                            biggest_prob_key = hej.getKey();
                            biggest_segment = i;
                        }
                    }
                }
                log.debug("Biggest key {}, prob {}, segment {}", biggest_prob_key, biggest_prob, biggest_segment);
                answer.put(biggest_prob_key, biggest_segment);
                // remove key from all probs
                for (int i = 0; i < 7; i++) {
                    probs.get(i).remove(biggest_prob_key);
                }
                // empty selected prob
                probs.get(biggest_segment).clear();
            }

            // Now we know
            // b = segment 5
            // d = segment 0
            // c = segment 6
            // a = segment 2
            // f = segment 3
            // e = segment 1
            // g = segment 4
            // number ab => segment 2,3
            // number gcdfa = > segment 4,6,0,3,2
            // kolla nu vilka siffror som mappar 1-1 mot dessa segment
            Map<String, Integer> solutions = new HashMap<>();
            for (String pattern : digitEntry.patterns) {
                Set<Integer> segmentPattern = new HashSet<>();
                for (char c : pattern.toCharArray()) {
                    segmentPattern.add(answer.get(String.valueOf(c)));
                }
                // compare segmentPattern with all fixed patterns
                for (Set<Integer> digitPattern : allNumbers.keySet()) {
                    if (digitPattern.equals(segmentPattern)) {
                        log.debug("Pattern {} is number {}", pattern, allNumbers.get(digitPattern));
                        solutions.put(pattern, allNumbers.get(digitPattern));
                    }
                }
            }
            int sum = 1000 * (solutions.get(digitEntry.outputs.get(0))) +
                    100 * (solutions.get(digitEntry.outputs.get(1))) +
                    10 * (solutions.get(digitEntry.outputs.get(2))) +
                    (solutions.get(digitEntry.outputs.get(3)));
            log.debug("Number: {}", sum);
            totalSum += sum;
        }
        return totalSum;
    }


    private Map<String, Integer> addProbs(Map<String, Integer> probMap, String pattern) {
        for (char c : pattern.toCharArray()) {
            probMap.merge(String.valueOf(c), 1, Integer::sum);
        }
        return probMap;
    }

    static class DigitEntry {
        List<String> patterns = new ArrayList<>();
        List<String> outputs = new ArrayList<>();
    }
}
