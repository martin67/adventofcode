package aoc.aoc2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4PassportProcessingTest {

    @ParameterizedTest
    @CsvSource({"2, src/test/resources/2020/day4-demo1.txt",
            "219, src/test/resources/2020/day4.txt"})
    void validPassports(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day4PassportProcessing(inputLines).validPassports());
    }

    @ParameterizedTest
    @CsvSource({"0, src/test/resources/2020/day4-demo2.txt",
            "4, src/test/resources/2020/day4-demo3.txt",
            "127, src/test/resources/2020/day4.txt"})
    void presentAndValidPassports(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day4PassportProcessing(inputLines).presentAndValidPassports());
    }

}