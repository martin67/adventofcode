package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 6: Custom Customs")
class Day6CustomCustomsTest {

    @ParameterizedTest
    @CsvSource({"6, day6-demo1.txt",
            "11, day6-demo2.txt",
            "6742, day6.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day6CustomCustoms(inputLines).questionsWithYes()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"3, day6-demo1.txt",
            "6, day6-demo2.txt",
            "3447, day6.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day6CustomCustoms(inputLines).questionsWithEveryoneYes()).isEqualTo(expected);
    }
}