package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2020: Day 7: Handy Haversacks")
class Day7HandyHaversacksTest {

    @ParameterizedTest
    @CsvSource({"4, src/test/resources/day7-demo1.txt",
            "185, src/test/resources/day7.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day7HandyHaversacks(inputLines).numberOfBags());
    }

    @ParameterizedTest
    @CsvSource({"32, src/test/resources/day7-demo1.txt",
            "126, src/test/resources/day7-demo2.txt",
            "89084, src/test/resources/day7.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day7HandyHaversacks(inputLines).numberOfBagsInside());
    }

}
