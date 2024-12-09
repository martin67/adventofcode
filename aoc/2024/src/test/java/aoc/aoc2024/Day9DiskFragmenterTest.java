package aoc.aoc2024;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2024: Day 9: Disk Fragmenter")
class Day9DiskFragmenterTest {

    @ParameterizedTest
    @CsvSource({"1928, day9-demo1.txt",
            "6446899523367, day9.txt"})
    void problem1(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day9DiskFragmenter(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"2858, day9-demo1.txt",
            "6478232739671, day9.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day9DiskFragmenter(inputLines).problem2()).isEqualTo(expected);
    }
}