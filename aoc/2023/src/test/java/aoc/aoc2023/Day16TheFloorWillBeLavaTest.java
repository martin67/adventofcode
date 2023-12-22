package aoc.aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import aoc.common.AocFiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2023: Day 16: The Floor Will Be Lava")
class Day16TheFloorWillBeLavaTest {

    @ParameterizedTest
    @CsvSource({"46, day16-demo1.txt",
            "0, day16.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day16TheFloorWillBeLava(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"0, day16-demo1.txt",
            "0, day16.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day16TheFloorWillBeLava(inputLines).problem2()).isEqualTo(expected);
    }
}