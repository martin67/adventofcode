package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 7: The Treachery of Whales")
class Day7TheTreacheryOfWhalesTest {

    @ParameterizedTest
    @CsvSource({"37, src/test/resources/2021/day7-demo1.txt",
            "352254, src/test/resources/2021/day7.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day7TheTreacheryOfWhales(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"168, src/test/resources/2021/day7-demo1.txt",
            "99053143, src/test/resources/2021/day7.txt"})
    void problem2(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day7TheTreacheryOfWhales(inputLines).problem2());
    }

}