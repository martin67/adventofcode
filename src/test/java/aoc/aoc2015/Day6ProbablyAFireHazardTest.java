package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 6: Probably a Fire Hazard")
class Day6ProbablyAFireHazardTest {
    @Test
    void lightsLit() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2015/day6.txt"));
        assertEquals(543903, new Day6ProbablyAFireHazard().lightsLit(inputLines, false));
    }

    @Test
    void totalBrightness() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2015/day6.txt"));
        assertEquals(14687245, new Day6ProbablyAFireHazard().lightsLit(inputLines, true));
    }
}