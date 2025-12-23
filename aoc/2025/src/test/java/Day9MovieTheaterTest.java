import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

@DisplayName("2024: Day 9: Movie Theater")
class Day9MovieTheaterTest {

    @ParameterizedTest
    @CsvSource({"50, day9-demo1.txt",
            "4765757080, day9.txt"})
    void problem1(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        Assertions.assertThat(new Day9MovieTheater(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"24, day9-demo1.txt",
            "0, day9.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        Assertions.assertThat(new Day9MovieTheater(inputLines).problem2()).isEqualTo(expected);
    }
}