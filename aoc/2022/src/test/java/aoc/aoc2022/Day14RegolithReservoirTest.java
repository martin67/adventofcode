package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 14: Regolith Reservoir")
class Day14RegolithReservoirTest {

    @ParameterizedTest
    @CsvSource({"24, day14-demo1.txt",
            "1068, day14.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day14RegolithReservoir(inputLines).problem1(false)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"93, day14-demo1.txt",
            "27936, day14.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day14RegolithReservoir(inputLines).problem2()).isEqualTo(expected);
    }
}