package aoc.aoc2015;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day12JSAbacusFramework {
    final List<String> inputLines;

    public Day12JSAbacusFramework(List<String> inputLines) {
        this.inputLines = inputLines;
    }

    public int sumOfAllNumbers() {
        int sumOfAllNumbers = 0;
        Pattern pattern = Pattern.compile("(-?\\d+)");
        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                sumOfAllNumbers += Integer.parseInt(matcher.group(1));
            }
        }
        return sumOfAllNumbers;
    }

    public int sumOfAllNumbersIgnoringRed() {
        Result result = null;
        switch (inputLines.get(0).charAt(0)) {
            case '[':
                result = parseArray(inputLines.get(0).substring(1));
                break;
            case '{':
                result = parseObject(inputLines.get(0).substring(1));
                break;
            default:
                assert false;
                break;
        }
        return result.sum;
    }


    Result parseArray(String in) {
        Result result = new Result();
        int index = 0;
        StringBuilder sb = new StringBuilder();
        while (in.charAt(index) != ']') {
            Result r;
            switch (in.charAt(index)) {
                case '[' -> {
                    r = parseArray(in.substring(index + 1));
                    index += r.index;
                    result.sum += r.sum;
                }
                case '{' -> {
                    r = parseObject(in.substring(index + 1));
                    index += r.index;
                    result.sum += r.sum;
                }
                default -> {
                    sb.append(in.charAt(index));
                    index++;
                }
            }
        }
        Pattern pattern = Pattern.compile("(-?\\d+)");
        Matcher matcher = pattern.matcher(sb);
        while (matcher.find()) {
            result.sum += Integer.parseInt(matcher.group(1));
        }
        log.debug("Array sum: {}, string: {}", result.sum, sb);
        result.index = index + 2;
        return result;
    }

    Result parseObject(String in) {
        Result result = new Result();
        int index = 0;
        StringBuilder sb = new StringBuilder();
        while (in.charAt(index) != '}') {
            Result r;
            switch (in.charAt(index)) {
                case '[' -> {
                    r = parseArray(in.substring(index + 1));
                    index += r.index;
                    result.sum += r.sum;
                }
                case '{' -> {
                    r = parseObject(in.substring(index + 1));
                    index += r.index;
                    result.sum += r.sum;
                }
                default -> {
                    sb.append(in.charAt(index));
                    index++;
                }
            }
        }
        if (sb.toString().contains("red")) {
            result.sum = 0;
        } else {
            Pattern pattern = Pattern.compile("(-?\\d+)");
            Matcher matcher = pattern.matcher(sb);
            while (matcher.find()) {
                result.sum += Integer.parseInt(matcher.group(1));
            }
        }
        log.debug("Object sum: {}, string: {}", result.sum, sb);
        result.index = index + 2;
        return result;
    }

    static class Result {
        int index;
        int sum;
    }
}
