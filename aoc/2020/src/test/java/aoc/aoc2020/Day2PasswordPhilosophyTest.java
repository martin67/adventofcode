package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 2: Password Philosophy")
class Day2PasswordPhilosophyTest {

    @ParameterizedTest
    @CsvSource({"2, day2-demo1.txt",
            "603, day2.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day2PasswordPhilosophy(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1, day2-demo1.txt",
            "404, day2.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day2PasswordPhilosophy(inputLines).problem2()).isEqualTo(expected);
    }
}