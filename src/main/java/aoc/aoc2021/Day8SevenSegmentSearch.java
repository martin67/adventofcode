package aoc.aoc2021;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Stream;

@Slf4j
public class Day8SevenSegmentSearch {
    static int SEGMENT_TOP = 0;
    static int SEGMENT_UPPER_LEFT = 1;
    static int SEGMENT_UPPER_RIGHT = 2;
    static int SEGMENT_MIDDLE = 3;
    static int SEGMENT_LOWER_LEFT = 4;
    static int SEGMENT_LOWER_RIGHT = 5;
    static int SEGMENT_BOTTOM = 6;

    enum Segment {
        TOP, UPPER_LEFT, UPPER_RIGHT, MIDDLE, LOWER_LEFT, LOWER_RIGHT, BOTTOM
    }

    static final Set<Segment> NUMBER_ZERO = new HashSet<>();
    static final Set<Segment> NUMBER_ONE = new HashSet<>();
    static final Set<Segment> NUMBER_TWO = new HashSet<>();
    static final Set<Segment> NUMBER_THREE = new HashSet<>();
    static final Set<Segment> NUMBER_FOUR = new HashSet<>();
    static final Set<Segment> NUMBER_FIVE = new HashSet<>();
    static final Set<Segment> NUMBER_SIX = new HashSet<>();
    static final Set<Segment> NUMBER_SEVEN = new HashSet<>();
    static final Set<Segment> NUMBER_EIGHT = new HashSet<>();
    static final Set<Segment> NUMBER_NINE = new HashSet<>();

    static final Map<Set<Segment>, Integer> segmentNumberMap = new HashMap<>();

    List<DigitEntry> entries = new ArrayList<>();
    String bobbaHighest;
    Segment bobbaSegment;

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

        NUMBER_ZERO.add(Segment.TOP);
        NUMBER_ZERO.add(Segment.UPPER_LEFT);
        NUMBER_ZERO.add(Segment.UPPER_RIGHT);
        NUMBER_ZERO.add(Segment.LOWER_LEFT);
        NUMBER_ZERO.add(Segment.LOWER_RIGHT);
        NUMBER_ZERO.add(Segment.BOTTOM);
        segmentNumberMap.put(NUMBER_ZERO, 0);

        NUMBER_ONE.add(Segment.UPPER_LEFT);
        NUMBER_ONE.add(Segment.LOWER_RIGHT);
        segmentNumberMap.put(NUMBER_ONE, 1);

        NUMBER_TWO.add(Segment.TOP);
        NUMBER_TWO.add(Segment.UPPER_RIGHT);
        NUMBER_TWO.add(Segment.MIDDLE);
        NUMBER_TWO.add(Segment.LOWER_LEFT);
        NUMBER_TWO.add(Segment.BOTTOM);
        segmentNumberMap.put(NUMBER_TWO, 2);

        NUMBER_THREE.add(Segment.TOP);
        NUMBER_THREE.add(Segment.UPPER_RIGHT);
        NUMBER_THREE.add(Segment.MIDDLE);
        NUMBER_THREE.add(Segment.LOWER_RIGHT);
        NUMBER_THREE.add(Segment.BOTTOM);
        segmentNumberMap.put(NUMBER_THREE, 3);

        NUMBER_FOUR.add(Segment.UPPER_LEFT);
        NUMBER_FOUR.add(Segment.UPPER_RIGHT);
        NUMBER_FOUR.add(Segment.MIDDLE);
        NUMBER_FOUR.add(Segment.LOWER_RIGHT);
        segmentNumberMap.put(NUMBER_FOUR, 4);

        NUMBER_FIVE.add(Segment.TOP);
        NUMBER_FIVE.add(Segment.UPPER_LEFT);
        NUMBER_FIVE.add(Segment.MIDDLE);
        NUMBER_FIVE.add(Segment.LOWER_RIGHT);
        NUMBER_FIVE.add(Segment.BOTTOM);
        segmentNumberMap.put(NUMBER_FIVE, 5);

        NUMBER_SIX.add(Segment.TOP);
        NUMBER_SIX.add(Segment.UPPER_LEFT);
        NUMBER_SIX.add(Segment.MIDDLE);
        NUMBER_SIX.add(Segment.LOWER_LEFT);
        NUMBER_SIX.add(Segment.LOWER_RIGHT);
        NUMBER_SIX.add(Segment.BOTTOM);
        segmentNumberMap.put(NUMBER_SIX, 6);

        NUMBER_SEVEN.add(Segment.TOP);
        NUMBER_SEVEN.add(Segment.UPPER_RIGHT);
        NUMBER_SEVEN.add(Segment.LOWER_RIGHT);
        segmentNumberMap.put(NUMBER_SEVEN, 7);

        NUMBER_EIGHT.add(Segment.TOP);
        NUMBER_EIGHT.add(Segment.UPPER_LEFT);
        NUMBER_EIGHT.add(Segment.UPPER_RIGHT);
        NUMBER_EIGHT.add(Segment.MIDDLE);
        NUMBER_EIGHT.add(Segment.LOWER_LEFT);
        NUMBER_EIGHT.add(Segment.LOWER_RIGHT);
        NUMBER_EIGHT.add(Segment.BOTTOM);
        segmentNumberMap.put(NUMBER_EIGHT, 8);

        NUMBER_NINE.add(Segment.TOP);
        NUMBER_NINE.add(Segment.UPPER_LEFT);
        NUMBER_NINE.add(Segment.UPPER_RIGHT);
        NUMBER_NINE.add(Segment.MIDDLE);
        NUMBER_NINE.add(Segment.LOWER_RIGHT);
        NUMBER_NINE.add(Segment.BOTTOM);
        segmentNumberMap.put(NUMBER_NINE, 9);

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

            //Map<Integer, Map<String, Integer>> probs = new HashMap<>();
            Map<Segment, Map<String, Integer>> probs2 = new HashMap<>();
//            for (int i = 0; i < 7; i++) {
//                probs.put(i, new HashMap<>());
//            }
            // Init maps
            for (Segment segment : Segment.values()) {
                probs2.put(segment, new HashMap<>());
            }
            // Add frequency of possible hits for each number to segment
            for (String pattern : digitEntry.patterns) {
                switch (pattern.length()) {
                    case 2: // 1
                        for (int i = 0; i < 3; i++) {
                            NUMBER_ONE.forEach(segment -> probs2.put(segment, addProbs2(probs2.get(segment), pattern)));
                        }
                        break;

                    case 3: // 7
                        for (int i = 0; i < 3; i++) {
                            NUMBER_SEVEN.forEach(segment -> probs2.put(segment, addProbs2(probs2.get(segment), pattern)));
                        }
                        break;

                    case 4: // 4
                        for (int i = 0; i < 3; i++) {
                            NUMBER_FOUR.forEach(segment -> probs2.put(segment, addProbs2(probs2.get(segment), pattern)));
                        }
                        break;

                    case 5: // 2, 3 or 5
                        NUMBER_TWO.forEach(segment -> probs2.put(segment, addProbs2(probs2.get(segment), pattern)));
                        NUMBER_THREE.forEach(segment -> probs2.put(segment, addProbs2(probs2.get(segment), pattern)));
                        NUMBER_FIVE.forEach(segment -> probs2.put(segment, addProbs2(probs2.get(segment), pattern)));
                        break;

                    case 6: // 0, 6 or 9
                        NUMBER_ZERO.forEach(segment -> probs2.put(segment, addProbs2(probs2.get(segment), pattern)));
                        NUMBER_SIX.forEach(segment -> probs2.put(segment, addProbs2(probs2.get(segment), pattern)));
                        NUMBER_NINE.forEach(segment -> probs2.put(segment, addProbs2(probs2.get(segment), pattern)));
                        break;

                    case 7: // 8
                        for (int i = 0; i < 3; i++) {
                            NUMBER_EIGHT.forEach(segment -> probs2.put(segment, addProbs2(probs2.get(segment), pattern)));
                        }
                        break;
                }
            }

            Map<String, Segment> answer = new HashMap<>();

            for (int x = 0; x < 7; x++) {
                log.info("Checking round {}", x);
                int biggest_prob = Integer.MIN_VALUE;
                String biggest_prob_key = null;
                Segment biggest_segment = null;
                Map<String, Integer> mostFrequentSet = null;

                //for (int i = 0; i < 7; i++) {
                for (Segment segment : Segment.values()) {
                    log.info("Checking which letter that is most common in segment {}", segment);
                    // find biggest prob
                    // if the prob is equal to another, check the second highest
                    if (!probs2.get(segment).isEmpty()) {
                        mostFrequentSet = compareSegmentSets(probs2.get(segment), mostFrequentSet, segment);
                        Map.Entry<String, Integer> hej = Collections.max(probs2.get(segment).entrySet(), Map.Entry.comparingByValue());
                        if (hej.getValue() == biggest_prob) {
                            log.info("Equal size for {} and {}", hej.getKey(), biggest_prob_key);
                        } else if (hej.getValue() > biggest_prob) {
                            log.info("{} is bigger than {}", hej.getValue(), biggest_prob);
                            biggest_prob = hej.getValue();
                            biggest_prob_key = hej.getKey();
                            biggest_segment = segment;
                        }
                    }
                }
                log.info("Biggest key 1 {}, prob {}, segment {}. Removing it from frequency set", biggest_prob_key, biggest_prob, biggest_segment);
                //Map.Entry<String, Integer> bobba = Collections.max(mostFrequentSet.entrySet(), Map.Entry.comparingByValue());
                log.info("Biggest key 2 {}, prob {}, segment {}. Removing it from frequency set", bobbaHighest, mostFrequentSet.get(bobbaHighest), bobbaSegment);

                answer.put(biggest_prob_key, biggest_segment);
                // remove found key from all possible segments
                //for (int i = 0; i < 7; i++) {
                for (Segment segment : Segment.values()) {
                    probs2.get(segment).remove(biggest_prob_key);
                }
                // remove found segment
                probs2.get(biggest_segment).clear();
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
                Set<Segment> segmentPattern = new HashSet<>();
                for (char c : pattern.toCharArray()) {
                    segmentPattern.add(answer.get(String.valueOf(c)));
                }
                // compare segmentPattern with all fixed patterns
                for (Set<Segment> digitPattern : segmentNumberMap.keySet()) {
                    log.info("Comparing {} ({}) with {} ({})", digitPattern, segmentNumberMap.get(digitPattern), segmentPattern, segmentNumberMap.get(segmentPattern));
                    if (digitPattern.equals(segmentPattern)) {
                        log.info("Pattern {} is number {}", pattern, segmentNumberMap.get(digitPattern));
                        solutions.put(pattern, segmentNumberMap.get(digitPattern));
                        break;
                    }
                }
            }
            int sum = 1000 * (solutions.get(digitEntry.outputs.get(0))) +
                    100 * (solutions.get(digitEntry.outputs.get(1))) +
                    10 * (solutions.get(digitEntry.outputs.get(2))) +
                    (solutions.get(digitEntry.outputs.get(3)));
            log.info("Number: {}", sum);
            totalSum += sum;
        }
        return totalSum;
    }

    private Map<String, Integer> compareSegmentSets(Map<String, Integer> setA, Map<String, Integer> setB, Segment segment) {
        // compare values.
        if (setB == null) {
            return setA;
        }
        Stream<Map.Entry<String, Integer>> sortedA =
                setA.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        Stream<Map.Entry<String, Integer>> sortedB =
                setB.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));

        Iterator<Map.Entry<String, Integer>> iteratorA = sortedA.iterator();
        Iterator<Map.Entry<String, Integer>> iteratorB = sortedB.iterator();
        bobbaSegment = segment;
        while (iteratorA.hasNext()) {
            Map.Entry<String, Integer> a = iteratorA.next();
            Map.Entry<String, Integer> b = iteratorB.next();
            if (a.getValue() > b.getValue()) {
                log.info("highest: {}", a.getKey());
                bobbaHighest = a.getKey();
                return setA;
            } else if (b.getValue() > a.getValue()) {
                log.info("highest: {}", b.getKey());
                bobbaHighest = b.getKey();
                return setB;
            } else {
                log.info("same values ({}), continuing comparison", a.getValue());
            }
        }
        // should not get here !
        log.error("Ooops...");
        return null;
    }

    private Map<String, Integer> addProbs2(Map<String, Integer> stringIntegerMap, String pattern) {
        for (char c : pattern.toCharArray()) {
            stringIntegerMap.merge(String.valueOf(c), 1, Integer::sum);
        }
        return stringIntegerMap;
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
