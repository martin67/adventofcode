package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2016: Day 6: Signals and Noise")
class Day6SignalsAndNoiseTest {

    @ParameterizedTest
    @CsvSource({"easter, src/test/resources/day6-demo1.txt",
            "tkspfjcc, src/test/resources/day6.txt"})
    void problem1(String expected, String fileName) throws IOException {
        assertEquals(expected, new Day6SignalsAndNoise(fileName).errorCorrectedVersion());
    }

    @ParameterizedTest
    @CsvSource({"advent, src/test/resources/day6-demo1.txt",
            "xrlmbypn, src/test/resources/day6.txt"})
    void problem2(String expected, String fileName) throws IOException {
        assertEquals(expected, new Day6SignalsAndNoise(fileName).originalMessage());
    }
}