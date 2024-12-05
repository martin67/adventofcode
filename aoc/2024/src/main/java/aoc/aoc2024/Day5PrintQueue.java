package aoc.aoc2024;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day5PrintQueue {
    List<Rule> rules = new ArrayList<>();
    List<List<Integer>> updates = new ArrayList<>();
    List<List<Integer>> correctUpdates = new ArrayList<>();
    List<List<Integer>> incorrectUpdates = new ArrayList<>();

    Day5PrintQueue(List<String> inputLines) {
        Pattern rulePattern = Pattern.compile("(\\d+)\\|(\\d+)");
        Pattern updatePattern = Pattern.compile("(\\d+),?");
        for (String line : inputLines) {
            if (line.isEmpty()) continue;
            Matcher matcher = rulePattern.matcher(line);
            if (matcher.find()) {
                rules.add(new Rule(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
            } else {
                Matcher updateMatcher = updatePattern.matcher(line);
                List<Integer> update = new ArrayList<>();
                while (updateMatcher.find()) {
                    update.add(Integer.parseInt(updateMatcher.group(1)));
                }
                updates.add(update);
            }
        }

        for (List<Integer> update : updates) {
            boolean allPagesOk = true;
            for (int page : update) {
                if (!isInOrder(page, update)) {
                    allPagesOk = false;
                }
            }
            if (allPagesOk) {
                correctUpdates.add(update);
            } else {
                incorrectUpdates.add(update);
            }
        }

    }

    int problem1() {
        // find middle numbers
        int sum = 0;
        for (List<Integer> correctUpdate : correctUpdates) {
            sum += correctUpdate.get((correctUpdate.size() - 1) / 2);
        }
        return sum;
    }

    int problem2() {
        int sum = 0;
        for (List<Integer> incorrectUpdate : incorrectUpdates) {
            List<Integer> fixed = fixUpdate(incorrectUpdate);
            // find middle numbers
            sum += fixed.get((fixed.size() - 1) / 2);
        }
        return sum;
    }

    List<Integer> fixUpdate(List<Integer> incorrectUpdate) {
        List<Integer> fixed = new ArrayList<>();
        fixed.add(incorrectUpdate.removeFirst());

        for (int page : incorrectUpdate) {
            // place and test
            for (int position = 0; position <= fixed.size(); position++) {
                List<Integer> updateToTest = new ArrayList<>(fixed);
                updateToTest.add(position, page);
                if (isInOrder(page, updateToTest)) {
                    fixed = updateToTest;
                    break;
                }
            }
        }
        return fixed;
    }

    boolean isInOrder(int page, List<Integer> update) {
        for (Rule rule : rules) {
            if (page == rule.before || page == rule.after) {
                int beforeIndex = update.indexOf(rule.before);
                int afterIndex = update.indexOf(rule.after);
                if (beforeIndex > afterIndex && afterIndex >= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    static class Rule {
        int before;
        int after;

        public Rule(int before, int after) {
            this.before = before;
            this.after = after;
        }
    }
}
