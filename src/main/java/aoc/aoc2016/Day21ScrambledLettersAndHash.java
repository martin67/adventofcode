package aoc.aoc2016;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day21ScrambledLettersAndHash {
    final List<String> instructions;

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
                password = swapPosition(password, Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                log.info("{}: {}", instruction, password);
                continue;
            }

            pattern = Pattern.compile("^swap letter (\\w) with letter (\\w)$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                password = swapLetter(password, matcher.group(1).charAt(0), matcher.group(2).charAt(0));
                log.info("{}: {}", instruction, password);
                continue;
            }

            pattern = Pattern.compile("^rotate (left|right) (\\d+) step(s)?$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                password = rotate(password, matcher.group(1), Integer.parseInt(matcher.group(2)));
                log.info("{}: {}", instruction, password);
                continue;
            }

            pattern = Pattern.compile("^rotate based on position of letter (\\w)$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                password = rotateLetter(password, matcher.group(1));
                log.info("{}: {}", instruction, password);
                continue;
            }

            pattern = Pattern.compile("^reverse positions (\\d+) through (\\d+)$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                password = reverse(password, Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                log.info("{}: {}", instruction, password);
                continue;
            }

            pattern = Pattern.compile("^move position (\\d+) to position (\\d+)$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                password = move(password, Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                log.info("{}: {}", instruction, password);
            }
        }
        return password;
    }

    private String swapPosition(String input, int x, int y) {
        char[] chars = input.toCharArray();
        char temp = chars[y];
        chars[y] = chars[x];
        chars[x] = temp;
        return new String(chars);
    }

    private String swapLetter(String input, char x, char y) {
        int xpos = input.indexOf(x);
        int ypos = input.indexOf(y);

        char[] chars = input.toCharArray();
        char temp = chars[ypos];
        chars[ypos] = chars[xpos];
        chars[xpos] = temp;
        return new String(chars);
    }

    private String rotate(String input, String direction, int x) {
        int size = input.length();
        StringBuilder sb = new StringBuilder();
        if (direction.equals("right")) {
            for (int i = 0; i < input.length(); i++) {
                sb.append(input.charAt((i + size - x) % size));
            }
        } else {
            for (int i = 0; i < input.length(); i++) {
                sb.append(input.charAt((i + x) % size));
            }
        }
        return sb.toString();
    }

    private String rotateLetter(String input, String x) {
        int index = input.indexOf(x);
        int size = input.length();
        int numberOfRotations = 1 + index;
        if (index >= 4) {
            numberOfRotations++;
            numberOfRotations = numberOfRotations % size;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            sb.append(input.charAt((i + size - numberOfRotations) % size));
        }
        return sb.toString();
    }

    private String reverseRotateLetter(String input, String x) {
        int index = input.indexOf(x);
        int size = input.length();

        int initialIndex;
        int[] indexMap = {7, 0, 4, 1, 5, 2, 6, 3};
        initialIndex = indexMap[index];

        int rotations = (size + (index - initialIndex)) % size;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            sb.append(input.charAt((i + rotations) % size));
        }
        return sb.toString();
    }

    private String reverse(String input, int x, int y) {
        String before = input.substring(0, x);
        String reverse = input.substring(x, y + 1);
        String after = (y < input.length() - 1) ? input.substring(y + 1) : "";
        StringBuilder sb = new StringBuilder(reverse).reverse();
        return before + sb + after;
    }

    private String move(String input, int x, int y) {
        String before = input.substring(0, x);
        String letter = input.substring(x, x + 1);
        String after = input.substring(x + 1);
        String tempPassword = before + after;
        before = tempPassword.substring(0, y);
        after = (y < tempPassword.length()) ? tempPassword.substring(y) : "";
        return before + letter + after;
    }

    String crack(String answer) {
        Pattern pattern;
        Matcher matcher;
        String password = answer;

        log.info("Starting with: {}", password);
        Collections.reverse(instructions);
        for (String instruction : instructions) {

            pattern = Pattern.compile("^swap position (\\d+) with position (\\d+)$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                password = swapPosition(password, Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(1)));
                log.info("{}: {}", instruction, password);
                continue;
            }

            pattern = Pattern.compile("^swap letter (\\w) with letter (\\w)$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                password = swapLetter(password, matcher.group(2).charAt(0), matcher.group(1).charAt(0));
                log.info("{}: {}", instruction, password);
                continue;
            }

            pattern = Pattern.compile("^rotate (left|right) (\\d+) step(s)?$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                if (matcher.group(1).equals("left")) {
                    password = rotate(password, "right", Integer.parseInt(matcher.group(2)));
                } else {
                    password = rotate(password, "left", Integer.parseInt(matcher.group(2)));
                }
                log.info("{}: {}", instruction, password);
                continue;
            }

            pattern = Pattern.compile("^rotate based on position of letter (\\w)$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                password = reverseRotateLetter(password, matcher.group(1));
                log.info("{}: {}", instruction, password);
                continue;
            }

            pattern = Pattern.compile("^reverse positions (\\d+) through (\\d+)$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                password = reverse(password, Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                log.info("{}: {}", instruction, password);
                continue;
            }

            pattern = Pattern.compile("^move position (\\d+) to position (\\d+)$");
            matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                password = move(password, Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(1)));
                log.info("{}: {}", instruction, password);
            }
        }
        return password;
    }
}

