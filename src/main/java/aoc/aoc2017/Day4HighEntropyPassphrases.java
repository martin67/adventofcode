package aoc.aoc2017;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day4HighEntropyPassphrases {
    boolean validPassphrase(String input) {
        Set<String> phrases = new HashSet<>();
        for (String phrase : input.split("\\s")) {
            if (phrases.contains(phrase)) {
                return false;
            }
            phrases.add(phrase);
        }
        return true;
    }

    boolean validAnagramPassphrase(String input) {
        Set<String> phrases = new HashSet<>();
        for (String phrase : input.split("\\s")) {
            if (phrases.contains(phrase)) {
                return false;
            }
            phrases.add(phrase);
        }
        return true;
    }

    int numberOfValidPassphrases(List<String> input) {
        int numberOfValidPassphrases = 0;
        for (String line : input) {
            if (validPassphrase(line)) {
                numberOfValidPassphrases++;
            }
        }
        return numberOfValidPassphrases;
    }
}
