package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2019: Day 24: Planet of Discord")
class Day24PlanetOfDiscordTest {

    @ParameterizedTest
    @CsvSource({"2129920, src/test/resources/2019/day24-demo1.txt",
            "28778811, src/test/resources/2019/day24.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day24PlanetOfDiscord(inputLines).biodiversityRating());
    }

    @ParameterizedTest
    @CsvSource({"99, 10, src/test/resources/2019/day24-demo1.txt",
            "2097, 200, src/test/resources/2019/day24.txt"})
    void problem2(int expected, int minutes, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day24PlanetOfDiscord(inputLines).bugsPresent(minutes));
    }

}