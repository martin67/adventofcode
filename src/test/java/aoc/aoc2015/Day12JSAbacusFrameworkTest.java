package aoc.aoc2015;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12JSAbacusFrameworkTest {

    @ParameterizedTest
    @CsvSource({"191164, src/test/resources/2015/day12.txt"})
    void sumOfAllNumbers(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day12JSAbacusFramework(inputLines).sumOfAllNumbers());
    }

}