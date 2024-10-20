package aoc.aoc2022;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day11MonkeyInTheMiddle {
    final List<Monkey> monkeys = new ArrayList<>();
    long lcd = 1;

     Day11MonkeyInTheMiddle(List<String> inputLines) {
        // Create all monkeys in advance;
        int numberOfMonkeys = inputLines.size() / 7 + 1;
        log.info("{} monkeys", numberOfMonkeys);
        for (int i = 0; i < numberOfMonkeys; i++) {
            monkeys.add(new Monkey(i));
        }
        int index = 0;

        for (String line : inputLines) {
            if (line.startsWith("Monkey")) {
                index = Integer.parseInt(line.split(" ")[1].substring(0, 1));
            } else if (line.startsWith("  Starting items:")) {
                String[] items = line.substring(18).split(",");
                for (String item : items) {
                    monkeys.get(index).items.push(Long.parseLong(item.trim()));
                }
            } else if (line.startsWith("  Operation:")) {
                monkeys.get(index).operation = line.substring(13);
            } else if (line.startsWith("  Test:")) {
                long divisor = Long.parseLong(line.trim().split(" ")[3]);
                monkeys.get(index).divisor = divisor;
                lcd *= divisor;
            } else if (line.startsWith("    If true:")) {
                monkeys.get(index).trueTarget = monkeys.get(Integer.parseInt(line.trim().split(" ")[5]));
            } else if (line.startsWith("    If false:")) {
                monkeys.get(index).falseTarget = monkeys.get(Integer.parseInt(line.trim().split(" ")[5]));
            }
        }
    }

    long problem1() {
        for (int i = 0; i < 20; i++) {
            for (Monkey monkey : monkeys) {
                log.debug("Monkey {}", monkey.id);
                while (!monkey.items.isEmpty()) {
                    monkey.inspect();
                }
            }
        }
        List<Monkey> sortedMonkeys = monkeys.stream().sorted(Comparator.comparing(Monkey::getInspections).reversed()).toList();
        return sortedMonkeys.get(0).inspections * sortedMonkeys.get(1).inspections;
    }

    long problem2() {
        for (int i = 0; i < 10000; i++) {
            for (Monkey monkey : monkeys) {
                while (!monkey.items.isEmpty()) {
                    monkey.inspect2();
                }
            }
        }
        List<Monkey> sortedMonkeys = monkeys.stream().sorted(Comparator.comparing(Monkey::getInspections).reversed()).toList();
        return sortedMonkeys.get(0).inspections * sortedMonkeys.get(1).inspections;
    }

    @SuppressWarnings("DataFlowIssue")
    class Monkey {
        final int id;
        final Deque<Long> items = new ArrayDeque<>();
        long divisor;
        String operation;
        Monkey trueTarget;
        Monkey falseTarget;
        @Getter
        long inspections = 0;

        public Monkey(int id) {
            this.id = id;
        }

        long operate(long old) {
            String[] s = operation.split(" ");
            long a;
            long b;

            if (s[2].equals("old")) {
                a = old;
            } else {
                a = Long.parseLong(s[2]);
            }

            if (s[4].equals("old")) {
                b = old;
            } else {
                b = Long.parseLong(s[4]);
            }

            return switch (s[3]) {
                case "+" -> a + b;
                case "*" -> a * b;
                default -> throw new IllegalStateException("Unexpected value: " + s[4]);
            };
        }

        void inspect() {
            inspections++;
            long item = items.pollLast();
            log.debug("  Monkey inspects an item with a worry level of {}", item);

            long worryLevel = operate(item);
            log.debug("    Worry level is updated to {}", worryLevel);

            worryLevel = worryLevel / 3;
            log.debug("    Monkey gets bored with item. Worry level is divided by 3 to {}.", worryLevel);

            if (worryLevel % divisor == 0) {
                log.debug("    Current worry level is divisible by {}.", divisor);
                log.debug("    Item with worry level {} is thrown to monkey {}", worryLevel, trueTarget.id);
                trueTarget.items.push(worryLevel);
            } else {
                log.debug("    Current worry level is not divisible by {}.", divisor);
                log.debug("    Item with worry level {} is thrown to monkey {}", worryLevel, falseTarget.id);
                falseTarget.items.push(worryLevel);
            }
        }

        void inspect2() {
            inspections++;
            long item = items.pollLast();
            long worryLevel = operate(item);

            long rest = worryLevel / lcd;
            if (rest > 0) {
                worryLevel -= rest * lcd;
            }

            if (worryLevel % divisor == 0) {
                trueTarget.items.push(worryLevel);
            } else {
                falseTarget.items.push(worryLevel);
            }
        }

    }
}
