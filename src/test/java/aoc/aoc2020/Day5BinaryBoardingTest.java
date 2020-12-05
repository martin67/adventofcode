package aoc.aoc2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5BinaryBoardingTest {

    @ParameterizedTest
    @CsvSource({"820, src/test/resources/2020/day5-demo1.txt",
            "911, src/test/resources/2020/day5.txt"})
    void highestId(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day5BinaryBoarding(inputLines).highestId());
    }

    @ParameterizedTest
    @CsvSource({"629, src/test/resources/2020/day5.txt"})
    void myId(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day5BinaryBoarding(inputLines).myId());
    }

}
