package aoc2019;

import java.util.List;
import java.util.stream.Collectors;

public class Day16FlawedFrequencyTransmission {

    List<Integer> fft;

    public Day16FlawedFrequencyTransmission(String input) {
        fft = input.chars().mapToObj(Character::getNumericValue).collect(Collectors.toList());

    }

    int factor(int length) {
    // 0, 0, 1, 1, 0, 0, -1, -1
    // 0, 0, 0, 1, 1, 1, 0, 0, 0, -1, -1, -1
    }

    String firstEightDigits(int phases) {

        return "12345678";
    }
}
