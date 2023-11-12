package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 4: Security Through Obscurity")
class Day4SecurityThroughObscurityTest {

    @ParameterizedTest
    @CsvSource({"1514, src/test/resources/day4-demo1.txt",
            "185371, src/test/resources/day4.txt"})
    void problem1(long expected, String fileName) throws IOException {
        assertThat(new Day4SecurityThroughObscurity(fileName).sectorIdSum()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"0, src/test/resources/day4-demo2.txt",
            "984, src/test/resources/day4.txt"})
    void problem2(long expected, String fileName) throws IOException {
        assertThat(new Day4SecurityThroughObscurity(fileName).northPoleObjectsSectorId()).isEqualTo(expected);
    }
}