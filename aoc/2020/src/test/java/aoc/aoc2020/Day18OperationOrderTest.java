package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2020: Day 18: Operation Order")
class Day18OperationOrderTest {

    @ParameterizedTest
    @CsvSource({"26457, src/test/resources/day18-demo1.txt",
            "36382392389406, src/test/resources/day18.txt"})
    void problem1(long expected, String fileName) throws Exception {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day18OperationOrder(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"694173, src/test/resources/day18-demo1.txt",
            "381107029777968, src/test/resources/day18.txt"})
    void problem2(long expected, String fileName) throws Exception {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day18OperationOrder(inputLines).problem2());
    }

}