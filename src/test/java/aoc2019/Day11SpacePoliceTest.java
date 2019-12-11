package aoc2019;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11SpacePoliceTest {

    @Test
    void numberOfPanelsPainted() throws IOException, InterruptedException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day11.txt"));
        assertEquals(1681, new Day11SpacePolice(inputLines).numberOfPanelsPainted(false));
    }

    @Test
    void panelMessage() throws IOException, InterruptedException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day11.txt"));
        assertEquals(250, new Day11SpacePolice(inputLines).numberOfPanelsPainted(true));
    }

}