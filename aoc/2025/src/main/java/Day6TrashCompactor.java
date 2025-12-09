import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day6TrashCompactor {
    List<List<Integer>> rows = new ArrayList<>();
    List<List<String>> rows2 = new ArrayList<>();
    List<String> operators = new ArrayList<>();

    public Day6TrashCompactor(List<String> inputLines) {
        for (String line : inputLines) {
            // odd column
            // read all spaces and digits. skip one space
            // even column

            String[] s = line.trim().split("\\s+");
            if (s[0].matches("\\d+")) {
                List<Integer> nums = new ArrayList<>();
                for (String numStr : s) {
                    nums.add(Integer.parseInt(numStr));
                }
                rows.add(nums);
            } else if (Pattern.matches("[+\\-*/]", s[0])) {
                Collections.addAll(operators, s);
            }
        }
    }

    public long problem1() {
        int numberOfColumns = operators.size();
        List<Long> results = new ArrayList<>();

        // Init results
        for (String operator : operators) {
            switch (operator) {
                case "+" -> results.add(0L);
                case "*" -> results.add(1L);
                default -> log.warn("Unknown operator: {}", operator);
            }
        }

        for (var row : rows) {
            for (int i = 0; i < numberOfColumns; i++) {
                switch (operators.get(i)) {
                    case "+" -> results.set(i, results.get(i) + row.get(i));
                    case "*" -> results.set(i, results.get(i) * row.get(i));
                    default -> log.warn("Unknown operator: {}", operators.get(i));
                }
            }
        }

        return results.stream().mapToLong(Long::longValue).sum();
    }

    public long problem2() {
        int numberOfColumns = operators.size();
        List<List<Integer>> columns = new ArrayList<>();
        List<Long> results = new ArrayList<>();

        for (String operator : operators) {
            switch (operator) {
                case "+" -> results.add(0L);
                case "*" -> results.add(1L);
                default -> log.warn("Unknown operator: {}", operator);
            }
            columns.add(new ArrayList<>());
        }

        for (var row : rows) {
            for (int i = 0; i < numberOfColumns; i++) {
                columns.get(i).add(row.get(i));
            }
        }

        for (int i = 0; i < columns.size(); i++) {
            var column = columns.get(i);
            int longestNumber = String.valueOf(column.stream()
                            .mapToInt(Integer::intValue)
                            .max()
                            .orElseThrow())
                    .length();

            List<Integer> newValues = new ArrayList<>();
            if (i % 2 == 0) {
                for (int j = longestNumber - 1; j > -1; j--) {
                    var sb = new StringBuilder();
                    for (int number : column) {
                        String numberStr = String.valueOf(number);
                        //int index = longestNumber - numberStr.length() - j;
                        int index = j;
                        if (index < numberStr.length()) {
                            sb.append(numberStr.charAt(Math.abs(index)));
                        }
                    }
                    newValues.add(Integer.parseInt(sb.toString()));
                }
            } else {
                for (int j = 0; j < longestNumber; j++) {
                    var sb = new StringBuilder();
                    for (int number : column) {
                        String numberStr = String.valueOf(number);
                        if (j < numberStr.length()) {
                            sb.append(numberStr.charAt(j));
                        }
                    }
                    newValues.add(Integer.parseInt(sb.toString()));
                }
            }

            log.info("Column {}, values {}", i, newValues);
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

// 8944563019736
// too high