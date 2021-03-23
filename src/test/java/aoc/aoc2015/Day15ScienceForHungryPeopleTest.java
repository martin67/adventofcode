package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 15: Science for Hungry People")
class Day15ScienceForHungryPeopleTest {

    @ParameterizedTest
    @CsvSource({"62842880, src/test/resources/2015/day15-demo1.txt",
            "21367368, src/test/resources/2015/day15.txt"})
    void totalScore(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day15ScienceForHungryPeople(inputLines).totalScore(false));
    }

    @ParameterizedTest
    @CsvSource({"57600000, src/test/resources/2015/day15-demo1.txt",
            "1766400, src/test/resources/2015/day15.txt"})
    void totalScoreWithCalories(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day15ScienceForHungryPeople(inputLines).totalScore(true));
    }

}