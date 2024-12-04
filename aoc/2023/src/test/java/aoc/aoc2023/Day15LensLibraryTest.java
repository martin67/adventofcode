package aoc.aoc2023;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2023: Day 15: Lens Library")
class Day15LensLibraryTest {

    @ParameterizedTest
    @CsvSource({"52, day15-demo1.txt",
            "1320, day15-demo2.txt",
            "514394, day15.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day15LensLibrary(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"145, day15-demo2.txt",
            "236358, day15.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day15LensLibrary(inputLines).problem2()).isEqualTo(expected);
    }
}