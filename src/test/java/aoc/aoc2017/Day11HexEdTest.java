package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2017: Day 11: Hex Ed")
class Day11HexEdTest {

    @ParameterizedTest
    @CsvSource({"3, 'ne,ne,ne'",
            "0,'ne,ne,sw,sw'",
            "2, 'ne,ne,s,s'",
            "3, 'se,sw,se,sw,sw'"})
    void fewestSteps(int expected, String directions) {
        assertEquals(expected, new Day11HexEd(directions).fewestSteps());
    }

    @ParameterizedTest
    @CsvSource({"877, src/test/resources/2017/day11.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day11HexEd(inputLines.get(0)).fewestSteps());
    }

    @ParameterizedTest
    @CsvSource({"1622, src/test/resources/2017/day11.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day11HexEd(inputLines.get(0)).furthestAway());
    }

}