import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day6TrashCompactor {
    List<List<Integer>> numbers = new ArrayList<>();
    List<String> operators = new ArrayList<>();

    public Day6TrashCompactor(List<String> inputLines) {
        for (String line : inputLines) {
            String[] s = line.trim().split("\\s+");
            if (s[0].matches("\\d+")) {
                List<Integer> nums = new ArrayList<>();
                for (String numStr : s) {
                    nums.add(Integer.parseInt(numStr));
                }
                numbers.add(nums);
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

        for (var row : numbers) {
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
        return 0;
    }
}
