package aoc.aoc2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17ConwayCubesTest {

    @ParameterizedTest
    @CsvSource({"112, src/test/resources/2020/day17-demo1.txt",
            "255, src/test/resources/2020/day17.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day17ConwayCubes(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"848, src/test/resources/2020/day17-demo1.txt",
            "2340, src/test/resources/2020/day17.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day17ConwayCubes(inputLines).problem2());
    }

}
