package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2020: Day 6: Custom Customs")
class Day6CustomCustomsTest {

    @ParameterizedTest
    @CsvSource({"6, src/test/resources/2020/day6-demo1.txt",
            "11, src/test/resources/2020/day6-demo2.txt",
            "6742, src/test/resources/2020/day6.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day6CustomCustoms(inputLines).questionsWithYes());
    }

    @ParameterizedTest
    @CsvSource({"3, src/test/resources/2020/day6-demo1.txt",
            "6, src/test/resources/2020/day6-demo2.txt",
            "3447, src/test/resources/2020/day6.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day6CustomCustoms(inputLines).questionsWithEveryoneYes());
    }

}