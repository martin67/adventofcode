package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 22: Crab Combat")
class Day22CrabCombatTest {

    @ParameterizedTest
    @CsvSource({"306, day22-demo1.txt",
            "30197, day22.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day22CrabCombat(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"291, day22-demo1.txt",
            "105, day22-demo2.txt",
            "34031, day22.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day22CrabCombat(inputLines).problem2()).isEqualTo(expected);
    }
}