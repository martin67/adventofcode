package aoc.aoc2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day1ReportRepairTest {

    @ParameterizedTest
    @CsvSource({"514579, src/test/resources/2020/day1-demo1.txt",
            "712075, src/test/resources/2020/day1.txt"})
    void sumAndMultiplyTwoEntries(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day1ReportRepair(inputLines).sumAndMultiplyTwoEntries());
    }

    @ParameterizedTest
    @CsvSource({"241861950, src/test/resources/2020/day1-demo1.txt",
            "145245270, src/test/resources/2020/day1.txt"})
    void sumAndMultiplyThreeEntries(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day1ReportRepair(inputLines).sumAndMultiplyThreeEntries());
    }

}