package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2020: Day 19: Monster Messages")
class Day19MonsterMessagesTest {

    @ParameterizedTest
    @CsvSource({"2, src/test/resources/2020/day19-demo1.txt",
            "2, src/test/resources/2020/day19-demo2.txt",
            "3, src/test/resources/2020/day19-demo3.txt",
            "187, src/test/resources/2020/day19.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day19MonsterMessages(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"12, src/test/resources/2020/day19-demo3.txt",
            "392, src/test/resources/2020/day19.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day19MonsterMessages(inputLines).problem2());
    }

}