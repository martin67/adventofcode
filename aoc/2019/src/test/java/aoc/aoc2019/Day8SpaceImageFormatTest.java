package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2019: Day 8: Space Image Format")
class Day8SpaceImageFormatTest {

    @Test
    void problem1() throws Exception {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/day8.txt"));
        assertEquals(2480, new Day8SpaceImageFormat(inputLines).digitChecksum());
    }

}