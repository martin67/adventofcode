package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 19: A Series of Tubes")
class Day19ASeriesOfTubesTest {

    @ParameterizedTest
    @CsvSource({"ABCDEF, day19-demo1.txt",
            "VEBTPXCHLI, day19.txt"})
    void problem1(String expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day19ASeriesOfTubes(inputLines).problem1().letters).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"38, day19-demo1.txt",
            "18702, day19.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day19ASeriesOfTubes(inputLines).problem1().steps).isEqualTo(expected);
    }
}
