package aoc.aoc2021;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2021: Day 17: Trick Shot")
class Day17TrickShotTest {

    @ParameterizedTest
    @CsvSource({"3, 7, 2",
            "6, 6, 3",
            "0, 9, 0",
            "-4, 17, -4"})
    void problem0(int expected, int xVelocity, int yVelocity) throws IOException {
        var inputLines = Files.readAllLines(Paths.get("day17-demo1.txt"));
        assertThat(new Day17TrickShot(inputLines).problem0(xVelocity, yVelocity)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"45, day17-demo1.txt",
            "12090, day17.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day17TrickShot(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"112, day17-demo1.txt",
            "5059, day17.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day17TrickShot(inputLines).problem2()).isEqualTo(expected);
    }
}