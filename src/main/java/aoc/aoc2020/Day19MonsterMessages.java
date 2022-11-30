package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day19MonsterMessages {
    final Map<Integer, String> rules = new HashMap<>();
    final List<String> messages = new ArrayList<>();

    public Day19MonsterMessages(List<String> inputLines) {
        Pattern rulePattern = Pattern.compile("^(\\d+): (.*)$");
        Pattern messagePattern = Pattern.compile("^(\\w+)$");
        Matcher matcher;

        for (String line : inputLines) {
            matcher = rulePattern.matcher(line);
            if (matcher.find()) {
                rules.put(Integer.parseInt(matcher.group(1)), matcher.group(2).replaceAll("\"", ""));
            }
            matcher = messagePattern.matcher(line);
            if (matcher.find()) {
                messages.add(matcher.group(1));
            }
        }
    }

    long problem1() {
        String regex = rules.get(0);
        while (!regex.matches("^[a-z |()]+$")) {
            StringBuilder builder = new StringBuilder();
            for (String part : regex.split(" ")) {
                if (part.matches("[0-9]+")) {
                    builder.append("( ").append(rules.get(Integer.parseInt(part))).append(" )");
                } else {
                    builder.append(part).append(' ');
                }
            }
            regex = builder.toString();
        }
        String pattern = "^" + regex.replaceAll(" ", "") + "$";

        return messages.stream().filter(a -> a.matches(pattern)).count();
    }

    long problem2() {
        rules.put(8, "42 | 42 8");
        rules.put(11, "42 31 | 42 11 31");

        String regex = rules.get(0);
        long prev = 0;
        while (true) {
            StringBuilder builder = new StringBuilder();
            for (String part : regex.split(" ")) {
                if (part.matches("[0-9]+")) {
                    builder.append("( ").append(rules.get(Integer.parseInt(part))).append(" )");
                } else {
                    builder.append(part).append(' ');
                }
            }

            regex = builder.toString();

            String pattern = "^" + regex.replaceAll("( )|42|31", "") + "$";

            long count = messages.stream().filter(a -> a.matches(pattern)).count();
            if (count > 0 && count == prev) {
                return count;
            }

            prev = count;
        }

    }

}