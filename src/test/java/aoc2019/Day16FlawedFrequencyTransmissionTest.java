package aoc2019;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day16FlawedFrequencyTransmissionTest {

    @ParameterizedTest
    @CsvSource({"01029498, 4, 12345678",
            "24176176, 100, 80871224585914546619083218645595",
            "73745418, 100, 19617804207202209144916044189917",
            "52432133, 100, 69317163492948606335995924319873"})
    void totalEnergy(String expected, int phases, String input) {
        assertEquals(expected, new Day16FlawedFrequencyTransmission(input).firstEightDigits(phases));
    }
}