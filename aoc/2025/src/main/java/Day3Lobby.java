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
            int largest = batteries.substring(0, batteries.length() - 1)
                    .chars()
                    .map(Character::getNumericValue)
                    .max()
                    .orElseThrow(IllegalStateException::new);

            // Second largest, start from largest
            int start = batteries.indexOf(Character.forDigit(largest, 10));
            int secondLargest = batteries.substring(start + 1)
                    .chars()
                    .map(Character::getNumericValue)
                    .max()
                    .orElseThrow(IllegalStateException::new);


            return largest * 10 + secondLargest;
        }

        long maxOverrideJoltage() {
            return 0;
        }
    }
}
