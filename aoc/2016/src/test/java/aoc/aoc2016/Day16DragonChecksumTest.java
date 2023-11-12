package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 16: Dragon Checksum")
class Day16DragonChecksumTest {

    @ParameterizedTest
    @CsvSource({"100, 1",
            "001, 0",
            "11111000000, 11111",
            "1111000010100101011110000, 111100001010"})
    void problem1(String expected, String input) {
        assertThat(new Day16DragonChecksum().dragonCurve(input)).isEqualTo(expected);
    }

    @Test
    void checksum() {
        assertThat(new Day16DragonChecksum().checksum("110010110100")).isEqualTo("100");
    }

    @ParameterizedTest
    @CsvSource({"01100, 20, 10000",
            "10010110010011110, 272, 10010000000110000",
            "01101011101100011, 35651584, 10010000000110000"})
    void problem2(String expected, int length, String input) {
        assertThat(new Day16DragonChecksum().completeChecksum(length, input)).isEqualTo(expected);
    }
}