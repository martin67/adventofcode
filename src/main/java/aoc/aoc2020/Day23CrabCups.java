package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Day23CrabCups {
    int start;

    public Day23CrabCups(int start) {
        this.start = start;
    }

    int problem1(int moves) {
        List<Integer> crabCups = new ArrayList<>();

        for (char c : Integer.toString(start).toCharArray()) {
            crabCups.add(Character.getNumericValue(c));
        }
        int currentCupLabel = crabCups.get(0);

        for (int move = 0; move < moves; move++) {
            log.info("-- move {} --", move + 1);
            log.info("cups: {}, current: {}", crabCups, currentCupLabel);
            int cup1 = crabCups.remove((crabCups.indexOf(currentCupLabel) + 1) % crabCups.size());
            int cup2 = crabCups.remove((crabCups.indexOf(currentCupLabel) + 1) % crabCups.size());
            int cup3 = crabCups.remove((crabCups.indexOf(currentCupLabel) + 1) % crabCups.size());
            log.info("pick up: {}, {}, {}", cup1, cup2, cup3);

            int destinationCupLabel = currentCupLabel - 1;

            while (destinationCupLabel == cup1 || destinationCupLabel == cup2 || destinationCupLabel == cup3) {
                destinationCupLabel--;
            }
            int lowestCupLevel = Collections.min(crabCups);
            int highestCupLabel = Collections.max(crabCups);
            if (destinationCupLabel < lowestCupLevel) {
                destinationCupLabel = highestCupLabel;
            }

            int destinationCup = crabCups.indexOf(destinationCupLabel);
            log.info("destination: {}", destinationCupLabel);

            // Don't wrap if on end position
            if (destinationCup == crabCups.size() - 1) {
                crabCups.add(cup1);
                crabCups.add(cup2);
                crabCups.add(cup3);
            } else {
                crabCups.add((destinationCup + 1) % crabCups.size(), cup3);
                crabCups.add((destinationCup + 1) % crabCups.size(), cup2);
                crabCups.add((destinationCup + 1) % crabCups.size(), cup1);
            }

            currentCupLabel = crabCups.get((crabCups.indexOf(currentCupLabel) + 1) % crabCups.size());
        }

        // Done
        int firstPos = crabCups.indexOf(1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < crabCups.size() - 1; i++) {

            sb.append(crabCups.get((firstPos + i + 1) % crabCups.size()));
        }

        return Integer.parseInt(sb.toString());
    }

    long problem2(int moves) {
        List<Integer> crabCups = new ArrayList<>();

        for (char c : Integer.toString(start).toCharArray()) {
            crabCups.add(Character.getNumericValue(c));
        }
        for (int i = crabCups.size(); i < 1000000; i++) {
            crabCups.add(i);
        }
        int currentCupLabel = crabCups.get(0);

        for (int move = 0; move < moves; move++) {
            int cup1 = crabCups.remove((crabCups.indexOf(currentCupLabel) + 1) % crabCups.size());
            int cup2 = crabCups.remove((crabCups.indexOf(currentCupLabel) + 1) % crabCups.size());
            int cup3 = crabCups.remove((crabCups.indexOf(currentCupLabel) + 1) % crabCups.size());

            int destinationCupLabel = currentCupLabel - 1;

            while (destinationCupLabel == cup1 || destinationCupLabel == cup2 || destinationCupLabel == cup3) {
                destinationCupLabel--;
            }
            int lowestCupLevel = Collections.min(crabCups);
            int highestCupLabel = Collections.max(crabCups);
            if (destinationCupLabel < lowestCupLevel) {
                destinationCupLabel = highestCupLabel;
            }

            int destinationCup = crabCups.indexOf(destinationCupLabel);

            // Don't wrap if on end position
            if (destinationCup == crabCups.size() - 1) {
                crabCups.add(cup1);
                crabCups.add(cup2);
                crabCups.add(cup3);
            } else {
                crabCups.add((destinationCup + 1) % crabCups.size(), cup3);
                crabCups.add((destinationCup + 1) % crabCups.size(), cup2);
                crabCups.add((destinationCup + 1) % crabCups.size(), cup1);
            }

            currentCupLabel = crabCups.get((crabCups.indexOf(currentCupLabel) + 1) % crabCups.size());
        }

        // Done
        return (long) crabCups.get(crabCups.indexOf(1) + 1) * crabCups.get(crabCups.indexOf(1) + 2);
    }
}
