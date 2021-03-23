package aoc.aoc2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day21AllergenAssessmentTest {

    @ParameterizedTest
    @CsvSource({"5, src/test/resources/2020/day21-demo1.txt",
            "2798, src/test/resources/2020/day21.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day21AllergenAssessment(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"'mxmxvkd,sqjhc,fvjkl', src/test/resources/2020/day21-demo1.txt",
            "2798, src/test/resources/2020/day21.txt"})
    void problem2(String expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day21AllergenAssessment(inputLines).problem2());
    }
}