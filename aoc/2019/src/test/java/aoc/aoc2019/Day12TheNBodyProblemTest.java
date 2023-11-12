package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 12: The N-Body Problem")
class Day12TheNBodyProblemTest {

    @ParameterizedTest
    @CsvSource({"179, 10, day12-demo1.txt",
            "1940, 100, day12-demo2.txt",
            "6490, 1000, day12.txt"})
    void problem1(int expected, int steps, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12TheNBodyProblem(inputLines).totalEnergy(steps)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"2772, day12-demo1.txt",
            "277068010964808, day12.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12TheNBodyProblem(inputLines).stepsToOriginalState()).isEqualTo(expected);
    }
}