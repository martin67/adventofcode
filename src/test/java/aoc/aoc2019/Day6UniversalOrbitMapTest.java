package aoc.aoc2019;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6UniversalOrbitMapTest {
    @ParameterizedTest
    @CsvSource({"42, src/test/resources/2019/day6-demo1.txt",
            "119831, src/test/resources/2019/day6.txt"})
    void numberOfOrbits(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day6UniversalOrbitMap(inputLines).numberOfOrbits());
    }

    @ParameterizedTest
    @CsvSource({"4, src/test/resources/2019/day6-demo2.txt",
            "322, src/test/resources/2019/day6.txt"})
    void minimumNumberOfOrbits(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day6UniversalOrbitMap(inputLines).minimumNumberOfOrbits());
    }
}