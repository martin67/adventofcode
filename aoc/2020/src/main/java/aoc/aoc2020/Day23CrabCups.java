package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day23CrabCups {
    final int start;

    Day23CrabCups(int start) {
        this.start = start;
    }

    int problem1(int moves) {
        var circle = new Circle(0);

        for (int move = 0; move < moves; move++) {
            log.info("-- move {} --", move + 1);
            log.info("cups: {}", circle);
            var threeCups = circle.pickUpThreeCups();
            log.info("pick up: {}, {}, {}", threeCups.get(0), threeCups.get(1), threeCups.get(2));
            var destinationCup = circle.selectDestinationCup(threeCups);
            log.info("destination: {}", destinationCup.value);
            circle.insertThreeCups(destinationCup, threeCups);
            circle.selectNewCurrentCup();
        }
        return Integer.parseInt(circle.finalOrder());
    }

    long problem2(int moves) {
        var circle = new Circle(1000000);
        for (int move = 0; move < moves; move++) {
            var threeCups = circle.pickUpThreeCups();
            var destinationCup = circle.selectDestinationCup(threeCups);
            circle.insertThreeCups(destinationCup, threeCups);
            circle.selectNewCurrentCup();
        }

        return circle.getFinalResult();
    }

    static class Cup {
        final int value;
        Cup nextCup;

        Cup(int value) {
            this.value = value;
        }
    }

    class Circle {
        final Map<Integer, Cup> cups = new HashMap<>();
        int size;
        int lowestCupValue = Integer.MAX_VALUE;
        int highestCupValue = Integer.MIN_VALUE;
        private Cup current = null;

        Circle(int extraCups) {
            Cup insert = null;

            for (char c : Integer.toString(start).toCharArray()) {
                int value = Character.getNumericValue(c);
                if (value < lowestCupValue) {
                    lowestCupValue = value;
                }
                if (value > highestCupValue) {
                    highestCupValue = value;
                }
                var cup = new Cup(Character.getNumericValue(c));
                cups.put(cup.value, cup);
                if (current == null) {
                    current = cup;
                    current.nextCup = cup;
                    insert = cup;
                } else {
                    insert.nextCup = cup;
                    insert = cup;
                    cup.nextCup = current;
                }
                size++;
            }

            if (extraCups > 0) {
                for (int i = Integer.toString(start).length() + 1; i <= extraCups; i++) {
                    var cup = new Cup(i);
                    cups.put(cup.value, cup);
                    insert.nextCup = cup;
                    insert = cup;
                    cup.nextCup = current;
                }
                size = extraCups;
                highestCupValue = extraCups;
            }
        }

        List<Integer> pickUpThreeCups() {
            List<Integer> threeCups = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                var cup = current.nextCup;
                cups.remove(cup.value);
                threeCups.add(cup.value);
                // remove cup
                current.nextCup = cup.nextCup;
            }
            return threeCups;
        }

        Cup selectDestinationCup(List<Integer> cupsPickedUp) {
            int label = current.value - 1;
            if (label < lowestCupValue) {
                label = highestCupValue;
            }
            while (cupsPickedUp.contains(label)) {
                label--;
                if (label < lowestCupValue) {
                    label = highestCupValue;
                }
            }

            return cups.get(label);
        }

        void insertThreeCups(Cup destinationCup, List<Integer> cupsPickedUp) {
            var last = destinationCup.nextCup;
            var first = new Cup(cupsPickedUp.getFirst());
            cups.put(first.value, first);
            destinationCup.nextCup = first;
            var second = new Cup(cupsPickedUp.get(1));
            cups.put(second.value, second);
            first.nextCup = second;
            var third = new Cup(cupsPickedUp.get(2));
            cups.put(third.value, third);
            second.nextCup = third;
            third.nextCup = last;
        }

        void selectNewCurrentCup() {
            current = current.nextCup;
        }

        long getFinalResult() {
            var start = cups.get(1);
            return (long) start.nextCup.value * start.nextCup.nextCup.value;
        }

        String finalOrder() {
            StringBuilder sb = new StringBuilder();
            var start = current;
            while (start.value != 1) {
                start = start.nextCup;
            }
            for (int i = 0; i < size - 1; i++) {
                sb.append(start.nextCup.value);
                start = start.nextCup;
            }
            return sb.toString();
        }

        @Override
        public String toString() {
            var c = current;
            StringBuilder sb = new StringBuilder();
            while (c.nextCup != current) {
                sb.append(c.value).append(" ");
                c = c.nextCup;
            }
            sb.append(c.value);
            sb.append("  current: ").append(current.value);
            return sb.toString();
        }
    }
}
