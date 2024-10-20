package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 9: Encoding Error")
class Day9EncodingErrorTest {

    @ParameterizedTest
    @CsvSource({"127, 5, day9-demo1.txt",
            "373803594, 25, day9.txt"})
    void problem1(long expected, int preamble, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day9EncodingError(preamble, inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"62, 5, day9-demo1.txt",
            "51152360, 25, day9.txt"})
    void problem2(long expected, int preamble, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day9EncodingError(preamble, inputLines).problem2()).isEqualTo(expected);
    }
}
