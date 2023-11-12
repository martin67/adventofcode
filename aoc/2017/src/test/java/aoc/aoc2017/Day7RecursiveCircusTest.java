package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2017: Day 7: Recursive Circus")
class Day7RecursiveCircusTest {

    @ParameterizedTest
    @CsvSource({"tknk, src/test/resources/day7-demo1.txt",
            "vmpywg, src/test/resources/day7.txt"})
    void problem1(String expected, String fileName) throws IOException {
        assertEquals(expected, new Day7RecursiveCircus(Files.readAllLines(Paths.get(fileName))).bottomProgramName());
    }

    @ParameterizedTest
    @CsvSource({"60, src/test/resources/day7-demo1.txt",
            "1674, src/test/resources/day7.txt"})
    void problem2(int expected, String fileName) throws IOException {
        //assertEquals(expected, new Day7RecursiveCircus(Files.readAllLines(Paths.get(fileName))).balanceWeight());
        assertEquals(expected, new Day7RecursiveCircus(Files.readAllLines(Paths.get(fileName))).computeWeights());
    }

}