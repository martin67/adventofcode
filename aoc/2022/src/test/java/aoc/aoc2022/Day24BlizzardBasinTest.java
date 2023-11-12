package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 24: Blizzard Basin")
class Day24BlizzardBasinTest {

    @ParameterizedTest
    @CsvSource({"18, day24-demo1.txt",
            "7763, day24.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day24BlizzardBasin(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"70, day24-demo1.txt",
            "2569, day24.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day24BlizzardBasin(inputLines).problem2()).isEqualTo(expected);
    }
}