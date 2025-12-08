import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

@DisplayName("2024: Day 5: Cafeteria")
class Day5CafeteriaTest {

    @ParameterizedTest
    @CsvSource({"3, day5-demo1.txt",
            "698, day5.txt"})
    void problem1(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        Assertions.assertThat(new Day5Cafeteria(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"14, day5-demo1.txt",
            "352807801032167, day5.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        Assertions.assertThat(new Day5Cafeteria(inputLines).problem2()).isEqualTo(expected);
    }
}