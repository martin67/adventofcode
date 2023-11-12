package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 6: Memory Reallocation")
class Day6MemoryReallocationTest {

    @ParameterizedTest
    @CsvSource({"5, day6-demo1.txt",
            "5042, day6.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertThat(new Day6MemoryReallocation(AocFiles.readAllLines(fileName)).countCycles(false))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"4, day6-demo1.txt",
            "1086, day6.txt"})
    void problem2(int expected, String fileName) throws IOException {
        assertThat(new Day6MemoryReallocation(AocFiles.readAllLines(fileName)).countCycles(true)).isEqualTo(expected);
    }
}