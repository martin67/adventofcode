package aoc2019;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8SpaceImageFormatTest {
    @Test
    void digitChecksum() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day8.txt"));
        assertEquals(0, new Day8SpaceImageFormat(inputLines).digitChecksum());
    }
}