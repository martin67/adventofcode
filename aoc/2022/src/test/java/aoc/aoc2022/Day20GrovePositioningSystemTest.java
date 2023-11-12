package aoc.aoc2022;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 20: Grove Positioning System")
class Day20GrovePositioningSystemTest {

    @ParameterizedTest
    @CsvSource({"3, day20-demo1.txt",
            "7395, day20.txt"})
    void problem1(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day20GrovePositioningSystem(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1623178306, day20-demo1.txt",
            "1640221678213, day20.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day20GrovePositioningSystem(inputLines).problem2()).isEqualTo(expected);
    }
}