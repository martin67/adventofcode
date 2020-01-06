package aoc.aoc2019;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8SpaceImageFormatTest {
    @Test
    void digitChecksum() throws Exception {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day8.txt"));
        assertEquals(2480, new Day8SpaceImageFormat(inputLines).digitChecksum());
    }
}