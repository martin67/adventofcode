package aoc.aoc2017;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day8IHeardYouLikeRegisters {

    final Map<String, Integer> registers;
    final List<String> instructions;

    public Day8IHeardYouLikeRegisters(List<String> input) {
        registers = new HashMap<>();
        instructions = input;
    }

    public int largestRegisterValue(boolean partTwo) {

        // c dec -10 if a >= 1
        Pattern pattern = Pattern.compile("^(\\w+) (dec|inc) (-?\\d+) if (\\w+) (\\W+) (-?\\d+)$");
        Matcher matcher;
        int largestRegisterValueEver = Integer.MIN_VALUE;

        for (String line : instructions) {
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                String destReg = matcher.group(1);
                String operator = matcher.group(2);
                int amount = Integer.parseInt(matcher.group(3));
                String condReg = matcher.group(4);
                String condition = matcher.group(5);
                int condValue = Integer.parseInt(matcher.group(6));

                // Init registers if they are not set
                registers.putIfAbsent(destReg, 0);
                registers.putIfAbsent(condReg, 0);

                // Check condition
                boolean evaluation;
                switch (condition) {
                    case ">":
                        evaluation = registers.get(condReg) > condValue;
                        break;
                    case "<":
                        evaluation = registers.get(condReg) < condValue;
                        break;
                    case ">=":
                        evaluation = registers.get(condReg) >= condValue;
                        break;
                    case "<=":
                        evaluation = registers.get(condReg) <= condValue;
                        break;
                    case "==":
                        evaluation = registers.get(condReg) == condValue;
                        break;
                    case "!=":
                        evaluation = registers.get(condReg) != condValue;
                        break;
                    default:
                        log.error("Unknown operator");
                        evaluation = false;
                        break;
                }

                if (evaluation) {
                    int val;
                    if (operator.equals("inc")) {
                        val = registers.get(destReg) + amount;
                    } else {
                        val = registers.get(destReg) - amount;
                    }
                    registers.put(destReg, val);
                }

            } else {
                log.warn("Could not parse {}", line);
            }

            int largestRegisterValue = registers.values().stream().mapToInt(Integer::intValue).max().orElseThrow(IllegalArgumentException::new);
            if (largestRegisterValue > largestRegisterValueEver) {
                largestRegisterValueEver = largestRegisterValue;
            }
        }

        if (partTwo) {
            return largestRegisterValueEver;
        } else {
            return registers.values().stream().mapToInt(Integer::intValue).max().orElseThrow(IllegalArgumentException::new);
        }
    }
}
