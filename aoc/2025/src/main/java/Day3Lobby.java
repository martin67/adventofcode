import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day3Lobby {
    List<Bank> banks = new ArrayList<>();

    Day3Lobby(List<String> inputLines) {
        for (String line : inputLines) {
            banks.add(new Bank(line));
        }
    }

    int problem1() {
        return banks.stream().mapToInt(Bank::maxJoltage).sum();
    }

    long problem2() {
        return banks.stream().mapToLong(Bank::maxOverrideJoltage).sum();
    }

    static class Bank {
        String batteries;

        public Bank(String batteries) {
            this.batteries = batteries;
        }

        int maxJoltage() {
            // find largest that is not at the end
            int largest = largestBattery(0, batteries.length() - 1);

            // Second largest, start from largest
            int start = batteries.indexOf(Character.forDigit(largest, 10));
            int secondLargest = largestBattery(start + 1, batteries.length());

            return largest * 10 + secondLargest;
        }

        long maxOverrideJoltage() {
            int start = 0;
            var sb = new StringBuilder();

            for (int i = 12; i > 0; i--) {
                int largest = largestBattery(start, batteries.length() - i + 1);
                sb.append(Character.forDigit(largest, 10));
                start = batteries.indexOf(Character.forDigit(largest, 10), start) + 1;
            }
            return Long.parseLong(sb.toString());
        }

        private int largestBattery(int start, int end) {
            return batteries.substring(start, end)
                    .chars()
                    .map(Character::getNumericValue)
                    .max()
                    .orElseThrow(IllegalStateException::new);
        }
    }
}
