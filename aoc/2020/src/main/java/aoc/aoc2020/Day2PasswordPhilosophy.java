package aoc.aoc2020;

import com.google.common.base.CharMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Day2PasswordPhilosophy {
    final List<PasswordAndPolicy> passwordAndPolicies = new ArrayList<>();

    Day2PasswordPhilosophy(List<String> inputLines) {
        var pattern = Pattern.compile("^(\\d+)-(\\d+) (\\w): (\\w+)$");

        for (String line : inputLines) {
            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                passwordAndPolicies.add(new PasswordAndPolicy(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        matcher.group(3).charAt(0),
                        matcher.group(4))
                );
            }
        }
    }

    long problem1() {
        return passwordAndPolicies.stream().filter(PasswordAndPolicy::validPassword).count();
    }

    long problem2() {
        return passwordAndPolicies.stream().filter(PasswordAndPolicy::validPasswordNewPolicy).count();
    }

    record PasswordAndPolicy(int min, int max, char letter, String password) {

        boolean validPassword() {
            int frequency = CharMatcher.is(letter).countIn(password);
            return (frequency >= min && frequency <= max);
        }

        boolean validPasswordNewPolicy() {
            boolean firstPos = (password.charAt(min - 1) == letter);
            boolean secondPos = (password.charAt(max - 1) == letter);
            return ((firstPos && !secondPos) || (!firstPos && secondPos));
        }
    }
}
