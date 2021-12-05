package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2016: Day 25: Clock Signal")
class Day25ClockSignalTest {

    @ParameterizedTest
    @CsvSource({"158, src/test/resources/2016/day25.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day25ClockSignal(inputLines).signalValue());
    }

}