package aoc.aoc2017;

import java.util.HashSet;
import java.util.Set;

public class Day4HighEntropyPassphrases {
    boolean validPassphrase(String input) {
        Set<String> phrases = new HashSet<>();
        for (String s : input.split("\\s")) {
            if (phrases.contains(s)) {
                return false;
            }
            phrases.add(s);
        }
        return true;
    }

    int numberOfValidPassphrases(String input) {
        Set<String> phrases = new HashSet<>();
        for (String s : input.split("\\s")) {
            if (phrases.contains(s)) {
                return false;
            }
            phrases.add(s);
        }
        return true;
    }
    }
}
