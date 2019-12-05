package aoc2019;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5SunnyWithAChanceOfAsteroidsTest {

    @Test
    void diagnosticCodeFromFile() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day5.txt"));
        for (String opcodes : inputLines) {
            assertEquals(13933662, new Day5SunnyWithAChanceOfAsteroids().diagnosticCode(opcodes, 1));
        }
    }

}