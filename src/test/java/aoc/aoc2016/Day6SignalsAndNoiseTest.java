package aoc.aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6SignalsAndNoiseTest {

    @ParameterizedTest
    @CsvSource({"easter, src/test/resources/2016/day6-demo1.txt",
            "tkspfjcc, src/test/resources/2016/day6.txt"})
    void bathroomCode(String expected, String fileName) throws IOException {
        assertEquals(expected, new Day6SignalsAndNoise(fileName).errorCorrectedVersion());
    }

    @ParameterizedTest
    @CsvSource({"advent, src/test/resources/2016/day6-demo1.txt",
            "xrlmbypn, src/test/resources/2016/day6.txt"})
    void originalMessage(String expected, String fileName) throws IOException {
        assertEquals(expected, new Day6SignalsAndNoise(fileName).originalMessage());
    }
}