package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2019: Day 5: Sunny with a Chance of Asteroids")
class Day5SunnyWithAChanceOfAsteroidsTest {

    @Test
    void problem1() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day5.txt"));
        for (String opcodes : inputLines) {
            assertEquals(13933662, new Day5SunnyWithAChanceOfAsteroids().diagnosticCode(opcodes, 1));
        }
    }

    @Test
    void problem2() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day5.txt"));
        for (String opcodes : inputLines) {
            assertEquals(2369720, new Day5SunnyWithAChanceOfAsteroids().diagnosticCode(opcodes, 5));
        }
    }

}