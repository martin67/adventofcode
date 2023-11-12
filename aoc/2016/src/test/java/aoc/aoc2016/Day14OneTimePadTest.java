package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 14: One-Time Pad")
class Day14OneTimePadTest {

    @ParameterizedTest
    @CsvSource({"22728, abc",
            "18626, ngcjuoqr"})
    void problem1(int expected, String salt) {
        assertThat(new Day14OneTimePad(salt, false).hashIndex()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"22551, abc",
            "20092, ngcjuoqr"})
    void problem2(int expected, String salt) {
        assertThat(new Day14OneTimePad(salt, true).hashIndex()).isEqualTo(expected);
    }
}