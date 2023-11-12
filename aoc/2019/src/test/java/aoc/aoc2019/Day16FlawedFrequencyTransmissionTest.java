package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 16: Flawed Frequency Transmission")
class Day16FlawedFrequencyTransmissionTest {

    @ParameterizedTest
    @CsvSource({"01029498, 4, 12345678",
            "24176176, 100, 80871224585914546619083218645595",
            "73745418, 100, 19617804207202209144916044189917",
            "52432133, 100, 69317163492948606335995924319873"})
    void totalEnergyDemo(String expected, int phases, String input) {
        assertThat(new Day16FlawedFrequencyTransmission(input).firstEightDigits(phases)).isEqualTo(expected);
    }

    @Test
    void problem1() throws IOException {
        var inputLines = AocFiles.readAllLines("day16.txt");
        assertThat(new Day16FlawedFrequencyTransmission(inputLines.get(0)).firstEightDigits(100))
                .isEqualTo("73127523");
    }

    @ParameterizedTest
    @CsvSource({"84462026, 100, 03036732577212944063491565474664",
            "78725270, 100, 02935109699940807407585447034323",
            "53553731, 100, 03081770884921959731165446850517"})
    void finalOutputDemo(String expected, int phases, String input) {
        assertThat(new Day16FlawedFrequencyTransmission(input).finalOutput(phases, 10000)).isEqualTo(expected);
    }

    @Test
    void problem2() throws IOException {
        var inputLines = AocFiles.readAllLines("day16.txt");
        assertThat(new Day16FlawedFrequencyTransmission(inputLines.get(0)).finalOutput(100, 10000))
                .isEqualTo("80284420");
    }
}