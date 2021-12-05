package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2020: Day 16: Ticket Translation")
class Day16TicketTranslationTest {

    @ParameterizedTest
    @CsvSource({"71, src/test/resources/2020/day16-demo1.txt",
            "21996, src/test/resources/2020/day16.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day16TicketTranslation(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"650080463519, src/test/resources/2020/day16.txt"})
    void problem2(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day16TicketTranslation(inputLines).problem2());
    }

}
