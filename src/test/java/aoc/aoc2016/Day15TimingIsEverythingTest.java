package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2016: Day 15: Timing is Everything")
class Day15TimingIsEverythingTest {

    @ParameterizedTest
    @CsvSource({"5, src/test/resources/2016/day15-demo1.txt",
            "16824, src/test/resources/2016/day15.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day15TimingIsEverything(inputLines).firstTime());
    }

    @ParameterizedTest
    @CsvSource({"3543984, src/test/resources/2016/day15-2.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day15TimingIsEverything(inputLines).firstTime());
    }

}