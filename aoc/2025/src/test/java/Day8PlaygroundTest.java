import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

@DisplayName("2024: Day 8: Playground")
class Day8PlaygroundTest {

    @ParameterizedTest
    @CsvSource({"40, day8-demo1.txt",
            "0, day8.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        Assertions.assertThat(new Day8Playground(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"0, day8-demo1.txt",
            "0, day8.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        Assertions.assertThat(new Day8Playground(inputLines).problem2()).isEqualTo(expected);
    }
}