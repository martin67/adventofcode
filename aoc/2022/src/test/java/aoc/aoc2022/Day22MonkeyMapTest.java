package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 22: Monkey Map")
class Day22MonkeyMapTest {

    @ParameterizedTest
    @CsvSource({"6032, day22-demo1.txt",
            "13566, day22.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day22MonkeyMap(inputLines).problem1(false)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"5031, day22-demo1.txt",
            "2569, day22.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day22MonkeyMap(inputLines).problem2()).isEqualTo(expected);
    }
}