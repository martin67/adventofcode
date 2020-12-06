package aoc.aoc2020;

import java.util.*;

public class Day6CustomCustoms {
    List<String> answers;

    public Day6CustomCustoms(List<String> inputLines) {
        answers = inputLines;
    }

    int questionsWithYes() {
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

    int questionsWithEveryoneYes() {
        int passwordsOk = 0;
        Map<Character, Integer> questions = new HashMap<>();
        int personsInGroup = 0;
        for (String line : answers) {
            if (line.isEmpty()) {
                for (Character c : questions.keySet()) {
                    if (questions.get(c) == personsInGroup) {
                        passwordsOk++;
                    }
                }
                questions.clear();
                personsInGroup = 0;
            } else {
                for (char c : line.toCharArray()) {
                    if (questions.containsKey(c)) {
                        questions.put(c, questions.get(c) + 1);
                    } else {
                        questions.put(c, 1);
                    }
                }
                personsInGroup++;
            }
        }
        for (Character c : questions.keySet()) {
            if (questions.get(c) == personsInGroup) {
                passwordsOk++;
            }
        }

        return passwordsOk;
    }
}
