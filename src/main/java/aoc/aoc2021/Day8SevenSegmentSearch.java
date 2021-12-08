package aoc.aoc2021;

import java.util.*;

public class Day8SevenSegmentSearch {
    static int SEGMENT_TOP = 0;
    static int SEGMENT_UPPER_LEFT = 1;
    static int SEGMENT_UPPER_RIGHT = 2;
    static int SEGMENT_MIDDLE = 3;
    static int SEGMENT_LOWER_LEFT = 4;
    static int SEGMENT_LOWER_RIGHT = 5;
    static int SEGMENT_BOTTOM = 6;

    List<Entry> entries = new ArrayList<>();

    public Day8SevenSegmentSearch(List<String> inputLines) {
        inputLines.forEach(line -> {
            String[] s = line.split("\\|");
            String[] p = s[0].trim().split(" ");
            String[] o = s[1].trim().split(" ");
            Entry entry = new Entry();
            entry.patterns.addAll(Arrays.asList(p));
            entry.outputs.addAll(Arrays.asList(o));
            entries.add(entry);
        });
    }

    int problem1() {
        int digits = 0;
        for (Entry entry : entries) {
            for (String output : entry.outputs) {
                if (output.length() == 2 || output.length() == 3 || output.length() == 4 | output.length() == 7) {
                    digits++;
                }
            }
        }
        return digits;
    }

    int problem2() {
        Map<Integer, Map<String, Integer>> probs = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            probs.put(i, new HashMap<>());
        }
        for (Entry entry : entries) {
            for (String pattern : entry.patterns) {
                switch (pattern.length()) {
                    case 2: // 1
                        for (int i = 0; i < 3; i++) {

                            probs.put(SEGMENT_UPPER_RIGHT, addProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                            probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));

                            probs.put(SEGMENT_TOP, addNegativeProbs(probs.get(SEGMENT_TOP), pattern));
                            probs.put(SEGMENT_UPPER_LEFT, addNegativeProbs(probs.get(SEGMENT_UPPER_LEFT), pattern));
                            probs.put(SEGMENT_MIDDLE, addNegativeProbs(probs.get(SEGMENT_MIDDLE), pattern));
                            probs.put(SEGMENT_LOWER_LEFT, addNegativeProbs(probs.get(SEGMENT_LOWER_LEFT), pattern));
                            probs.put(SEGMENT_BOTTOM, addNegativeProbs(probs.get(SEGMENT_BOTTOM), pattern));
                        }
                        break;

                    case 3: // 7
                        for (int i = 0; i < 3; i++) {
                            probs.put(SEGMENT_TOP, addProbs(probs.get(SEGMENT_TOP), pattern));
                            probs.put(SEGMENT_UPPER_RIGHT, addProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                            probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));

                            probs.put(SEGMENT_UPPER_LEFT, addNegativeProbs(probs.get(SEGMENT_UPPER_LEFT), pattern));
                            probs.put(SEGMENT_MIDDLE, addNegativeProbs(probs.get(SEGMENT_MIDDLE), pattern));
                            probs.put(SEGMENT_LOWER_LEFT, addNegativeProbs(probs.get(SEGMENT_LOWER_LEFT), pattern));
                            probs.put(SEGMENT_BOTTOM, addNegativeProbs(probs.get(SEGMENT_BOTTOM), pattern));
                        }
                        break;

                    case 4: // 4
                        for (int i = 0; i < 3; i++) {

                            probs.put(SEGMENT_UPPER_LEFT, addProbs(probs.get(SEGMENT_UPPER_LEFT), pattern));
                            probs.put(SEGMENT_MIDDLE, addProbs(probs.get(SEGMENT_MIDDLE), pattern));
                            probs.put(SEGMENT_UPPER_RIGHT, addProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                            probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));

                            probs.put(SEGMENT_TOP, addNegativeProbs(probs.get(SEGMENT_TOP), pattern));
                            probs.put(SEGMENT_LOWER_LEFT, addNegativeProbs(probs.get(SEGMENT_LOWER_LEFT), pattern));
                            probs.put(SEGMENT_BOTTOM, addNegativeProbs(probs.get(SEGMENT_BOTTOM), pattern));
                        }
                        break;

                    case 5: // 2, 3 or 5
                        //  2
                        probs.put(SEGMENT_TOP, addProbs(probs.get(SEGMENT_TOP), pattern));
                        probs.put(SEGMENT_UPPER_RIGHT, addProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                        probs.put(SEGMENT_MIDDLE, addProbs(probs.get(SEGMENT_MIDDLE), pattern));
                        probs.put(SEGMENT_LOWER_LEFT, addProbs(probs.get(SEGMENT_LOWER_LEFT), pattern));
                        probs.put(SEGMENT_BOTTOM, addProbs(probs.get(SEGMENT_BOTTOM), pattern));

                        probs.put(SEGMENT_UPPER_LEFT, addNegativeProbs(probs.get(SEGMENT_UPPER_LEFT), pattern));
                        probs.put(SEGMENT_LOWER_RIGHT, addNegativeProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));

                        // 3
                        probs.put(SEGMENT_TOP, addProbs(probs.get(SEGMENT_TOP), pattern));
                        probs.put(SEGMENT_UPPER_RIGHT, addProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                        probs.put(SEGMENT_MIDDLE, addProbs(probs.get(SEGMENT_MIDDLE), pattern));
                        probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));
                        probs.put(SEGMENT_BOTTOM, addProbs(probs.get(SEGMENT_BOTTOM), pattern));

                        probs.put(SEGMENT_UPPER_LEFT, addNegativeProbs(probs.get(SEGMENT_UPPER_LEFT), pattern));
                        probs.put(SEGMENT_LOWER_LEFT, addNegativeProbs(probs.get(SEGMENT_LOWER_LEFT), pattern));

                        // 5
                        probs.put(SEGMENT_TOP, addProbs(probs.get(SEGMENT_TOP), pattern));
                        probs.put(SEGMENT_UPPER_LEFT, addProbs(probs.get(SEGMENT_UPPER_LEFT), pattern));
                        probs.put(SEGMENT_MIDDLE, addProbs(probs.get(SEGMENT_MIDDLE), pattern));
                        probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));
                        probs.put(SEGMENT_BOTTOM, addProbs(probs.get(SEGMENT_BOTTOM), pattern));

                        probs.put(SEGMENT_UPPER_RIGHT, addNegativeProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                        probs.put(SEGMENT_LOWER_LEFT, addNegativeProbs(probs.get(SEGMENT_LOWER_LEFT), pattern));
                        break;

                    case 6: // 0, 6 or 9
                        // 0
                        probs.put(SEGMENT_TOP, addProbs(probs.get(SEGMENT_TOP), pattern));
                        probs.put(SEGMENT_UPPER_LEFT, addProbs(probs.get(SEGMENT_UPPER_LEFT), pattern));
                        probs.put(SEGMENT_UPPER_RIGHT, addProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                        probs.put(SEGMENT_LOWER_LEFT, addProbs(probs.get(SEGMENT_LOWER_LEFT), pattern));
                        probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));
                        probs.put(SEGMENT_BOTTOM, addProbs(probs.get(SEGMENT_BOTTOM), pattern));

                        probs.put(SEGMENT_MIDDLE, addNegativeProbs(probs.get(SEGMENT_MIDDLE), pattern));

                        // 6
                        probs.put(SEGMENT_TOP, addProbs(probs.get(SEGMENT_TOP), pattern));
                        probs.put(SEGMENT_UPPER_LEFT, addProbs(probs.get(SEGMENT_UPPER_LEFT), pattern));
                        probs.put(SEGMENT_MIDDLE, addProbs(probs.get(SEGMENT_MIDDLE), pattern));
                        probs.put(SEGMENT_LOWER_LEFT, addProbs(probs.get(SEGMENT_LOWER_LEFT), pattern));
                        probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));
                        probs.put(SEGMENT_BOTTOM, addProbs(probs.get(SEGMENT_BOTTOM), pattern));

                        probs.put(SEGMENT_UPPER_RIGHT, addNegativeProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));

                        // 9
                        probs.put(SEGMENT_TOP, addProbs(probs.get(SEGMENT_TOP), pattern));
                        probs.put(SEGMENT_UPPER_LEFT, addProbs(probs.get(SEGMENT_UPPER_LEFT), pattern));
                        probs.put(SEGMENT_UPPER_RIGHT, addProbs(probs.get(SEGMENT_UPPER_RIGHT), pattern));
                        probs.put(SEGMENT_MIDDLE, addProbs(probs.get(SEGMENT_MIDDLE), pattern));
                        probs.put(SEGMENT_LOWER_RIGHT, addProbs(probs.get(SEGMENT_LOWER_RIGHT), pattern));
                        probs.put(SEGMENT_BOTTOM, addProbs(probs.get(SEGMENT_BOTTOM), pattern));

                        probs.put(SEGMENT_LOWER_LEFT, addNegativeProbs(probs.get(SEGMENT_LOWER_LEFT), pattern));
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
        }
        return 0;
    }

    private Map<String, Integer> addProbs(Map<String, Integer> probMap, String pattern) {
        for (char c : pattern.toCharArray()) {
            probMap.merge(String.valueOf(c), 1, Integer::sum);
        }
        return probMap;
    }

    private Map<String, Integer> addNegativeProbs(Map<String, Integer> probMap, String pattern) {
        String antiPattern = "abcdefg";
        antiPattern = antiPattern.replaceAll(pattern, "");

        for (char c : antiPattern.toCharArray()) {
            probMap.merge(String.valueOf(c), 1, Integer::sum);
        }
        return probMap;
    }

    static class Entry {
        List<String> patterns = new ArrayList<>();
        List<String> outputs = new ArrayList<>();
    }
}
