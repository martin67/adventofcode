package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 6: Signals and Noise")
class Day6SignalsAndNoiseTest {

    @ParameterizedTest
    @CsvSource({"easter, src/test/resources/day6-demo1.txt",
            "tkspfjcc, src/test/resources/day6.txt"})
    void problem1(String expected, String fileName) throws IOException {
        assertThat(new Day6SignalsAndNoise(fileName).errorCorrectedVersion()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"advent, src/test/resources/day6-demo1.txt",
            "xrlmbypn, src/test/resources/day6.txt"})
    void problem2(String expected, String fileName) throws IOException {
        assertThat(new Day6SignalsAndNoise(fileName).originalMessage()).isEqualTo(expected);
    }
}