import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day6TrashCompactor {
    List<List<String>> columns = new ArrayList<>();
    List<String> operators = new ArrayList<>();

    public Day6TrashCompactor(List<String> inputLines) {
        int start = 0;
        int pos = 0;
        var firstLine = inputLines.getFirst();

        while (pos < firstLine.length()) {
            char ch = firstLine.charAt(pos);
            // continue until we see a space or if it's the end of the line
            if (pos == firstLine.length() - 1) {
                // Now we have a column. But we don't know the width of the column. Should be the
                // longest string in the column.
                var column = new ArrayList<String>();
                for (int i = 0; i < inputLines.size() - 1; i++) {
                    column.add(inputLines.get(i).substring(start));
                }
                // get the longest line in the column
                int longestLineLength = column.stream()
                        .mapToInt(String::length)
                        .max()
                        .orElseThrow();
                // trim all lines to the longest line length
                column.replaceAll(string -> Strings.padEnd(string, longestLineLength, ' '));
                columns.add(column);
                break;
            }

            if (Character.isWhitespace(ch)) {
                // Check if all columns below are spaces
                boolean allBelowAreSpaces = true;
                for (int i = 1; i < inputLines.size() - 1; i++) {
                    char belowCh = inputLines.get(i).charAt(pos);
                    if (!Character.isWhitespace(belowCh)) {
                        allBelowAreSpaces = false;
                        break;
                    }
                }
                if (allBelowAreSpaces) {
                    // Now we have a column
                    var column = new ArrayList<String>();
                    for (int i = 0; i < inputLines.size() - 1; i++) {
                        column.add(inputLines.get(i).substring(start, pos));
                    }
                    columns.add(column);
                    start = pos + 1;
                }
            }
            pos++;
        }

        // Last line is operators
        Collections.addAll(operators, inputLines.getLast().split("\\s+"));
    }

    public long problem1() {
        List<Long> results = new ArrayList<>();

        // Init results
        for (String operator : operators) {
            switch (operator) {
                case "+" -> results.add(0L);
                case "*" -> results.add(1L);
                default -> log.warn("Unknown operator: {}", operator);
            }
        }

        for (int i = 0; i < columns.size(); i++) {
            log.info("Column: {}", columns.get(i));
            for (String s : columns.get(i)) {
                switch (operators.get(i)) {
                    case "+" -> results.set(i, results.get(i) + Integer.parseInt(s.trim()));
                    case "*" -> results.set(i, results.get(i) * Integer.parseInt(s.trim()));
                    default -> log.warn("Unknown operator: {}", operators.get(i));
                }
            }
        }

        return results.stream().mapToLong(Long::longValue).sum();
    }

    public long problem2() {
        List<Long> results = new ArrayList<>();

        for (String operator : operators) {
            switch (operator) {
                case "+" -> results.add(0L);
                case "*" -> results.add(1L);
                default -> log.warn("Unknown operator: {}", operator);
            }
        }

        for (int i = 0; i < columns.size(); i++) {
            var column = columns.get(i);
            List<Integer> newValues = new ArrayList<>();

            if (i % 2 == 0) {
                for (int j = 0; j < column.getFirst().length(); j++) {
                    var sb = new StringBuilder();
                    for (var s : column) {
                        sb.append(s.charAt(j));
                    }
                    newValues.add(Integer.parseInt(sb.toString().trim()));
                }
            } else {
                for (int j = column.getFirst().length() - 1; j >= 0; j--) {
                    var sb = new StringBuilder();
                    for (var s : column) {
                        sb.append(s.charAt(j));
                    }
                    newValues.add(Integer.parseInt(sb.toString().trim()));
                }
            }

            for (int value : newValues) {
                switch (operators.get(i)) {
                    case "+" -> results.set(i, results.get(i) + value);
                    case "*" -> results.set(i, results.get(i) * value);
                    default -> log.warn("Unknown operator: {}", operators.get(i));
                }
            }
        }

        return results.stream().mapToLong(Long::longValue).sum();
    }
}
