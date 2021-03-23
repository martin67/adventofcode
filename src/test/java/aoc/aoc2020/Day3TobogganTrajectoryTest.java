package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 3: Toboggan Trajectory")
class Day3TobogganTrajectoryTest {

    @ParameterizedTest
    @CsvSource({"7, src/test/resources/2020/day3-demo1.txt",
            "176, src/test/resources/2020/day3.txt"})
    void treesEncountered(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day3TobogganTrajectory(inputLines).treesEncountered(3, 1));
    }

    @ParameterizedTest
    @CsvSource({"336, src/test/resources/2020/day3-demo1.txt",
            "5872458240, src/test/resources/2020/day3.txt"})
    void treesSecondEncounter(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day3TobogganTrajectory(inputLines).treesSecondEncounter());
    }

}