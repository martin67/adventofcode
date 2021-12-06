package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2017: Day 19: A Series of Tubes")
class Day19ASeriesOfTubesTest {

    @ParameterizedTest
    @CsvSource({"ABCDEF, src/test/resources/2017/day19-demo1.txt",
            "VEBTPXCHLI, src/test/resources/2017/day19.txt"})
    void problem1(String expected, String fileName) throws IOException {
        assertEquals(expected, new Day19ASeriesOfTubes(Files.readAllLines(Paths.get(fileName))).problem1().letters);
    }

    @ParameterizedTest
    @CsvSource({"38, src/test/resources/2017/day19-demo1.txt",
            "18702, src/test/resources/2017/day19.txt"})
    void problem2(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day19ASeriesOfTubes(Files.readAllLines(Paths.get(fileName))).problem1().steps);
    }

}
