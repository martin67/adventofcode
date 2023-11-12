package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 21: Monkey Math")
class Day21MonkeyMathTest {

    @ParameterizedTest
    @CsvSource({"152, day21-demo1.txt",
            "80326079210554, day21.txt"})
    void problem1(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day21MonkeyMath(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({ //"301, day21-demo1.txt",
            "3617613952378, day21.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day21MonkeyMath(inputLines).problem2()).isEqualTo(expected);
    }
}