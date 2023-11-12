package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 18: Duet")
class Day18DuetTest {

    @ParameterizedTest
    @CsvSource({"4, day18-demo1.txt",
            "9423, day18.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day18Duet(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"3, day18-demo2.txt",
            "7620, day18.txt"})
    void problem2(int expected, String fileName) throws IOException, ExecutionException, InterruptedException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day18Duet(inputLines).problem2()).isEqualTo(expected);
    }
}