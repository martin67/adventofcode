package aoc.aoc2018;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
class Day5AlchemicalReduction {

    final Set<String> units = new HashSet<>();

    public Day5AlchemicalReduction() {
        for (char i = 'a'; i <= 'z'; i++) {
            units.add(i + String.valueOf(i).toUpperCase());
            units.add(String.valueOf(i).toUpperCase() + i);
        }
    }

    String reducePolymer(String input) {
        String output = input;
        for (String unit : units) {
            output = output.replace(unit, "");
        }
        return output;
    }

    int getPolymerLength(String input) {

        String in = input.trim();
        String out = null;
        boolean diff = true;

        while (diff) {
            out = reducePolymer(in);
            //log.info("Reducing from {} to {}", in.length(), out.length());
            diff = !out.equals(in);
            in = out;
        }

        return out.length();
    }


    int findShortestPolymer(String input) {

        int shortestPolymer = getPolymerLength(input);

        // Loop through a-z
        for (char i = 'a'; i <= 'z'; i++) {
            // Strip polymer
            String reduced = input.replaceAll(Character.toString(i), "");
            reduced = reduced.replaceAll(Character.toString(i).toUpperCase(), "");
            int length = getPolymerLength(reduced);
            //log.info("Char: " + i + " length: " + length);
            if (length < shortestPolymer) {
                shortestPolymer = length;
            }
        }
        return shortestPolymer;
    }
}
