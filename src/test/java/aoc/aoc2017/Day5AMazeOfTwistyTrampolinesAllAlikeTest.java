package aoc.aoc2017;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5AMazeOfTwistyTrampolinesAllAlikeTest {

    @ParameterizedTest
    @CsvSource({"5, src/test/resources/2017/day5-demo1.txt",
            "360603, src/test/resources/2017/day5.txt"})
    void countSteps(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day5AMazeOfTwistyTrampolinesAllAlike(Files.readAllLines(Paths.get(fileName))).countSteps());
    }

    @ParameterizedTest
    @CsvSource({"10, src/test/resources/2017/day5-demo1.txt",
            "25347697, src/test/resources/2017/day5.txt"})
    void countStepsBackwards(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day5AMazeOfTwistyTrampolinesAllAlike(Files.readAllLines(Paths.get(fileName))).countStepsBackwards());
    }
}