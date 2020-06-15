package aoc.aoc2015;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17NoSuchThingAsTooMuchTest {

    @ParameterizedTest
    @CsvSource({"4, 25, src/test/resources/2015/day17-demo1.txt",
            "1638, 150, src/test/resources/2015/day17.txt"})
    void numberOfCombinations(int expected, int liters, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day17NoSuchThingAsTooMuch(inputLines).numberOfCombinations(liters));
    }

    @ParameterizedTest
    @CsvSource({"3, 25, src/test/resources/2015/day17-demo1.txt",
            "17, 150, src/test/resources/2015/day17.txt"})
    void numberOfMinimumCombinations(int expected, int liters, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day17NoSuchThingAsTooMuch(inputLines).numberOfMinimumCombinations(liters));
    }

}