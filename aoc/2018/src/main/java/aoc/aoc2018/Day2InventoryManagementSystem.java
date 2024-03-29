package aoc.aoc2018;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Day2InventoryManagementSystem {

    Checksum computeRowChecksum(String input) {
        Checksum result = new Checksum();

        Map<Character, Integer> characterOccurrences = new HashMap<>();

        // Create set with the number of occurrences for each letter
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (!characterOccurrences.containsKey(c)) {
                characterOccurrences.put(c, 1);
            } else {
                characterOccurrences.put(c, characterOccurrences.get(c) + 1);
            }
        }

        // Check if any of occurrences = 2 or 3
        result.numberOfTwos = characterOccurrences.containsValue(2) ? 1 : 0;
        result.numberOfThrees = characterOccurrences.containsValue(3) ? 1 : 0;
        return result;
    }

    int computeChecksum(String input) {
        // Split string into a list
        List<String> inputStrings = Arrays.stream(input.trim().split("\\s+")).toList();

        int twos = 0, threes = 0;
        for (String string : inputStrings) {
            Checksum result = computeRowChecksum(string);
            twos += result.numberOfTwos;
            threes += result.numberOfThrees;
        }
        return twos * threes;
    }

    String findBoxes(String input) {
        // Split string into a list
        List<String> inputStrings = Arrays.stream(input.trim().split("\\s+")).toList();

        // loop through all strings
        // compare string char by char and count the number of differences
        for (String str1 : inputStrings) {
            for (String str2 : inputStrings) {
                // copy strings by char if they are equal. The one that differs one in length is the one
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < str1.length(); i++) {
                    if (str1.charAt(i) == str2.charAt(i)) {
                        result.append(str1.charAt(i));
                    }
                }
                if (result.length() == (str1.length() - 1)) {
                    return result.toString();
                }
            }
        }
        return null;
    }

    static class Checksum {
        int numberOfTwos;
        int numberOfThrees;
    }
}
