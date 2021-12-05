package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2020: Day 24: Lobby Layout")
class Day24LobbyLayoutTest {

    @ParameterizedTest
    @CsvSource({"10, src/test/resources/2020/day24-demo1.txt",
            "400, src/test/resources/2020/day24.txt"})
    void problem1(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day24LobbyLayout(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"2208, src/test/resources/2020/day24-demo1.txt",
            "3768, src/test/resources/2020/day24.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day24LobbyLayout(inputLines).problem2());
    }

}