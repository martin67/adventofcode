package aoc2019;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day20DonutMazeTest {
    @ParameterizedTest
    @CsvSource({"23, src/test/resources/2019/day20-demo1.txt",
            "58, src/test/resources/2019/day20-demo2.txt",
            "442, src/test/resources/2019/day20.txt"})
    void shortestPath(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day20DonutMaze(inputLines).shortestPath());
    }

    @ParameterizedTest
    @CsvSource({"396, src/test/resources/2019/day20-demo3.txt",
            "5208, src/test/resources/2019/day20.txt"})
    void shortestRecursivePath(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day20DonutMaze(inputLines).shortestRecursivePath());
    }
}