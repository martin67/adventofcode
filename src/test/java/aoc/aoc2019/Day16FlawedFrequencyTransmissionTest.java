package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2019: Day 16: Flawed Frequency Transmission")
class Day16FlawedFrequencyTransmissionTest {

    @ParameterizedTest
    @CsvSource({"01029498, 4, 12345678",
            "24176176, 100, 80871224585914546619083218645595",
            "73745418, 100, 19617804207202209144916044189917",
            "52432133, 100, 69317163492948606335995924319873"})
    void totalEnergyDemo(String expected, int phases, String input) {
        assertEquals(expected, new Day16FlawedFrequencyTransmission(input).firstEightDigits(phases));
    }

    @Test
    void problem1() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day16.txt"));
        assertEquals("73127523", new Day16FlawedFrequencyTransmission(inputLines.get(0)).firstEightDigits(100));
    }

    @ParameterizedTest
    @CsvSource({"84462026, 100, 03036732577212944063491565474664",
            "78725270, 100, 02935109699940807407585447034323",
            "53553731, 100, 03081770884921959731165446850517"})
    void finalOutputDemo(String expected, int phases, String input) {
        assertEquals(expected, new Day16FlawedFrequencyTransmission(input).finalOutput(phases, 10000));
    }

    @Test
    void problem2() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day16.txt"));
        assertEquals("80284420", new Day16FlawedFrequencyTransmission(inputLines.get(0)).finalOutput(100, 10000));
    }
}