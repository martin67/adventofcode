package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 14: Docking Data")
class Day14DockingDataTest {

    @ParameterizedTest
    @CsvSource({"165, src/test/resources/2020/day14-demo1.txt",
            "12610010960049, src/test/resources/2020/day14.txt"})
    void problem1(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day14DockingData(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"208, src/test/resources/2020/day14-demo2.txt",
            "3608464522781, src/test/resources/2020/day14.txt"})
    void problem2(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day14DockingData(inputLines).problem2());
    }

}
