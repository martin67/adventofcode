package aoc.aoc2016;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day21ScrambledLettersAndHash {
    List<String> instructions;

    public Day21ScrambledLettersAndHash(List<String> inputLines) {
        this.instructions = inputLines;
    }

    String scramble(String input) {

        // swap position X with position Y
        // swap letter X with letter Y
        // rotate left/right X steps
        // rotate based on position of letter X
        // reverse positions X through Y
        // move position X to position Y

        Pattern pattern;
        Matcher matcher;
        String password = input;

        log.info("Starting with: {}", input);
        for (String instruction : instructions) {

            pattern = Pattern.compile("^swap position (\\d+) with position (\\d+)$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));

                char[] chars = password.toCharArray();
                char temp = chars[y];
                chars[y] = chars[x];
                chars[x] = temp;
                password = new String(chars);
                log.info("{}: {}", instruction, password);
                continue;
            }

            pattern = Pattern.compile("^swap letter (\\w) with letter (\\w)$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                char x = matcher.group(1).charAt(0);
                char y = matcher.group(2).charAt(0);
                int xpos = password.indexOf(x);
                int ypos = password.indexOf(y);

                char[] chars = password.toCharArray();
                char temp = chars[ypos];
                chars[ypos] = chars[xpos];
                chars[xpos] = temp;
                password = new String(chars);
                log.info("{}: {}", instruction, password);
                continue;
            }

            pattern = Pattern.compile("^rotate (left|right) (\\d+) step(s)?$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                String direction = matcher.group(1);
                int x = Integer.parseInt(matcher.group(2));
                int size = password.length();
                StringBuilder sb = new StringBuilder();
                if (direction.equals("right")) {
                    for (int i = 0; i < password.length(); i++) {
                        sb.append(password.charAt((i + size - x) % size));
                    }
                } else {
                    for (int i = 0; i < password.length(); i++) {
                        sb.append(password.charAt((i + x) % size));
                    }
                }
                password = sb.toString();
                log.info("{}: {}", instruction, password);
                continue;
            }

            pattern = Pattern.compile("^rotate based on position of letter (\\w)$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                String letter = matcher.group(1);
                int index = password.indexOf(letter);
                int size = password.length();
                int numberOfRotations = 1 + index;
                if (index >= 4) {
                    numberOfRotations++;
                    numberOfRotations = numberOfRotations % size;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < password.length(); i++) {
                    sb.append(password.charAt((i + size - numberOfRotations) % size));
                }
                password = sb.toString();
                log.info("{}: {}", instruction, password);
                continue;
            }

            pattern = Pattern.compile("^reverse positions (\\d+) through (\\d+)$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));

                String before = password.substring(0, x);
                String reverse = password.substring(x, y + 1);
                String after = (y < password.length() - 1) ? password.substring(y + 1) : "";

                StringBuilder sb = new StringBuilder(reverse).reverse();
                password = before + sb.toString() + after;
                log.info("{}: {}", instruction, password);
                continue;
            }

            pattern = Pattern.compile("^move position (\\d+) to position (\\d+)$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));

                String before = password.substring(0, x);
                String letter = password.substring(x, x + 1);
                String after = password.substring(x + 1);

                String tempPassword = before + after;
                before = tempPassword.substring(0, y);
                after = (y < tempPassword.length()) ? tempPassword.substring(y) : "";

                password = before + letter + after;
                log.info("{}: {}", instruction, password);
            }
        }
        return password;
    }
}

