package aoc2018;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

@Slf4j
class Day5AlchemicalReduction {

    private String reducePolymer(String input) {

        StringBuilder output = new StringBuilder();

        // loop through until a reduction is made
        for (int i = 0; i < input.length() - 1; i++) {
            char c = input.charAt(i);
            char nc = input.charAt(i + 1);

            if ((Character.toLowerCase(c) == Character.toLowerCase(nc)) &&
                    ((isUpperCase(c) && isLowerCase(nc)) || (isLowerCase(c) && isUpperCase(nc)))) {
                // Skip one iteration if it is a reaction
                // skip c and nc, append the rest of the string and exit
                output.append(input.substring(i + 2));
                //log.info("in: " + input + ", out " + output);
                return output.toString();
            } else {
                output.append(c);
            }
        }

        output.append(input.charAt(input.length() - 1));

        //log.info("in: " + input + ", out " + output + " exit");
        return output.toString();
    }


    int getPolymerLength(String input) {

        String in = input.trim();
        String out = null;
        boolean diff = true;

        while (diff) {
            out = reducePolymer(in);
            //log.debug("Reducing from " + in.length() + " to "+ out.length());
            diff = !out.equals(in);
            in = out;
        }

        return out.length();
    }


    int findShortestPolymer(String input) {

        int shortestPolymer = getPolymerLength(input);

        // Loop through a-z
        for (char i = 'a'; i < 'z'; i++) {
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
