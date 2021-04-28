package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 25: Combo Breaker")
class Day25ComboBreakerTest {

    @ParameterizedTest
    @CsvSource({"14897079, src/test/resources/2020/day25-demo1.txt",
            "7032853, src/test/resources/2020/day25.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day25ComboBreaker(inputLines).problem1());
    }

}