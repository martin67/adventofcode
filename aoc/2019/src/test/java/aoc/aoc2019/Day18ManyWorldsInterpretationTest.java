package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2019: Day 18: Many-Worlds Interpretation")
class Day18ManyWorldsInterpretationTest {

    @ParameterizedTest
    @CsvSource({"8, src/test/resources/day18-demo1.txt",
            "86, src/test/resources/day18-demo2.txt",
            "132, src/test/resources/day18-demo3.txt",
            "136, src/test/resources/day18-demo4.txt",
            "81, src/test/resources/day18-demo5.txt",
            "4250, src/test/resources/day18.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day18ManyWorldsInterpretation(inputLines).shortestPath());
    }

    @ParameterizedTest
    @CsvSource({"8, src/test/resources/day18-demo6.txt",
            "24, src/test/resources/day18-demo7.txt",
            "32, src/test/resources/day18-demo8.txt",
            "72, src/test/resources/day18-demo9.txt",
            "1640, src/test/resources/day18-2.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day18ManyWorldsInterpretation(inputLines).shortestMultiplePath());
    }

}