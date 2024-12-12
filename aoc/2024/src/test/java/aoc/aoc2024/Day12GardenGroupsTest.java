package aoc.aoc2024;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2024: Day 12: Garden Groups")
class Day12GardenGroupsTest {

    @ParameterizedTest
    @CsvSource({"140, day12-demo1.txt",
            "772, day12-demo2.txt",
            "1930, day12-demo3.txt",
            "1424472, day12.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12GardenGroups(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"80, day12-demo1.txt",
            "236, day12-demo4.txt",
            "368, day12-demo5.txt",
            "1206, day12-demo3.txt",
            "870202, day12.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12GardenGroups(inputLines).problem2()).isEqualTo(expected);
    }
}