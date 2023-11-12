package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 7: No Space Left On Device")
class Day7NoSpaceLeftOnDeviceTest {

    @ParameterizedTest
    @CsvSource({"95437, day7-demo1.txt",
            "1182909, day7.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day7NoSpaceLeftOnDevice(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"24933642, day7-demo1.txt",
            "2832508, day7.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day7NoSpaceLeftOnDevice(inputLines).problem2()).isEqualTo(expected);
    }
}