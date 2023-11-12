package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 19: Monster Messages")
class Day19MonsterMessagesTest {

    @ParameterizedTest
    @CsvSource({"2, day19-demo1.txt",
            "2, day19-demo2.txt",
            "3, day19-demo3.txt",
            "187, day19.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day19MonsterMessages(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"12, day19-demo3.txt",
            "392, day19.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day19MonsterMessages(inputLines).problem2()).isEqualTo(expected);
    }
}