import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2025: Day 1: Secret Entrance")
class Day1SecretEntranceTest {

    @ParameterizedTest
    @CsvSource({"3, day1-demo1.txt",
            "1018, day1.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day1SecretEntrance(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"6, day1-demo1.txt",
            "10, day1-demo2.txt",
            "5815, day1.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day1SecretEntrance(inputLines).problem2()).isEqualTo(expected);
    }
}