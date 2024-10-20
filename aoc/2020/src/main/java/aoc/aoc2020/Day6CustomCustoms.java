package aoc.aoc2020;

import java.util.*;

public class Day6CustomCustoms {
    final List<String> answers;

    Day6CustomCustoms(List<String> inputLines) {
        answers = inputLines;
    }

    int problem1() {
        int passwordsOk = 0;
        Set<Character> questions = new HashSet<>();
        for (String line : answers) {
            if (line.isEmpty()) {
                passwordsOk += questions.size();
                questions.clear();
            } else {
                for (char c : line.toCharArray()) {
                    questions.add(c);
                }
            }
        }
        passwordsOk += questions.size();

        return passwordsOk;
    }

    int problem2() {
        Map<Character, Integer> questions = new HashMap<>();
        int passwordsOk = 0;
        int personsInGroup = 0;

        for (String line : answers) {
            if (line.isEmpty()) {
                for (char c : questions.keySet()) {
                    if (questions.get(c) == personsInGroup) {
                        passwordsOk++;
                    }
                }
                questions.clear();
                personsInGroup = 0;
            } else {
                for (char c : line.toCharArray()) {
                    questions.putIfAbsent(c, 0);
                    questions.put(c, questions.get(c) + 1);
                }
                personsInGroup++;
            }
        }
        for (char c : questions.keySet()) {
            if (questions.get(c) == personsInGroup) {
                passwordsOk++;
            }
        }

        return passwordsOk;
    }
}
