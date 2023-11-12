package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 18: Many-Worlds Interpretation")
class Day18ManyWorldsInterpretationTest {

    @ParameterizedTest
    @CsvSource({"8, day18-demo1.txt",
            "86, day18-demo2.txt",
            "132, day18-demo3.txt",
            "136, day18-demo4.txt",
            "81, day18-demo5.txt",
            "4250, day18.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day18ManyWorldsInterpretation(inputLines).shortestPath()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"8, day18-demo6.txt",
            "24, day18-demo7.txt",
            "32, day18-demo8.txt",
            "72, day18-demo9.txt",
            "1640, day18-2.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day18ManyWorldsInterpretation(inputLines).shortestMultiplePath()).isEqualTo(expected);
    }

}