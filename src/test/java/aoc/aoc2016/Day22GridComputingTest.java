package aoc.aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day22GridComputingTest {

    @ParameterizedTest
    @CsvSource({"7, src/test/resources/2016/day22-demo1.txt",
            "934, src/test/resources/2016/day22.txt"})
    void viableNodes(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day22GridComputing(inputLines).viableNodes());
    }

    @ParameterizedTest
    @CsvSource({"7, src/test/resources/2016/day22-demo1.txt",
            "0, src/test/resources/2016/day22.txt"})
    void fewestSteps(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day22GridComputing(inputLines).fewestSteps());
    }
}