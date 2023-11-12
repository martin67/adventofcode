package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 12: Rain Risk")
class Day12RainRiskTest {

    @ParameterizedTest
    @CsvSource({"25, day12-demo1.txt",
            "636, day12.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12RainRisk(inputLines).distance()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"286, day12-demo1.txt",
            "26841, day12.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12RainRisk(inputLines).distanceWithWaypoint()).isEqualTo(expected);
    }
}
