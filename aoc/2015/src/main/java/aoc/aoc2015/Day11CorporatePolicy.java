package aoc.aoc2015;

import java.util.HashSet;
import java.util.Set;

public class Day11CorporatePolicy {

    public boolean validPassword(String password) {
        if (password.contains("i") || password.contains("o") || password.contains("l")) {
            return false;
        }
        boolean sequencePresent = false;
        int indexOfLastPair = 0;
        Set<String> pairs = new HashSet<>();

        for (int i = 0; i < password.length(); i++) {

            char c = password.charAt(i);
            if (i > 1) {
                if (password.charAt(i - 1) == c - 1 && password.charAt(i - 2) == c - 2) {
                    sequencePresent = true;
                }
            }
            if (i > 0) {
                if (c == password.charAt(i - 1)) {
                    String pair = password.substring(i - 1, i + 1);
                    if (i != indexOfLastPair + 1) {
                        pairs.add(pair);
                        indexOfLastPair = i;
                    }
                }
            }
        }
        return sequencePresent & (pairs.size() > 1);
    }

    String increasePassword(String password) {
        int position = password.length() - 1;
        boolean done = false;

        do {
            if (password.charAt(position) != 'z') {
                password = password.substring(0, position) + (char) (password.charAt(position) + 1) + password.substring(position + 1);
                done = true;
            } else {
                password = password.substring(0, position) + 'a' + password.substring(position + 1);
                position--;
            }
        } while (!done);
        return password;
    }

    public String nextPassword(String password) {
        do {
            password = increasePassword(password);
        } while (!validPassword(password));
        return password;
    }
}
