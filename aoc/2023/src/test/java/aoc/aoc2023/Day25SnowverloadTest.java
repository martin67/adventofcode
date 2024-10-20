package aoc.aoc2023;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("2023: Day 25: Snowverload")
class Day25SnowverloadTest {

    @ParameterizedTest
    @CsvSource({"54, day25-demo1.txt",
            "0, day25.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day25Snowverload(inputLines).problem1()).isEqualTo(expected);
    }
}