package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 2: Dive!")
class Day2DiveTest {

    @ParameterizedTest
    @CsvSource({"150, src/test/resources/2021/day2-demo1.txt",
            "1728414, src/test/resources/2021/day2.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day2Dive(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"900, src/test/resources/2021/day2-demo1.txt",
            "1765720035, src/test/resources/2021/day2.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day2Dive(inputLines).problem2());
    }

}