package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2017: Day 5: A Maze of Twisty Trampolines, All Alike")
class Day5AMazeOfTwistyTrampolinesAllAlikeTest {

    @ParameterizedTest
    @CsvSource({"5, src/test/resources/day5-demo1.txt",
            "360603, src/test/resources/day5.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day5AMazeOfTwistyTrampolinesAllAlike(Files.readAllLines(Paths.get(fileName))).countSteps());
    }

    @ParameterizedTest
    @CsvSource({"10, src/test/resources/day5-demo1.txt",
            "25347697, src/test/resources/day5.txt"})
    void problem2(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day5AMazeOfTwistyTrampolinesAllAlike(Files.readAllLines(Paths.get(fileName))).countStepsBackwards());
    }

}