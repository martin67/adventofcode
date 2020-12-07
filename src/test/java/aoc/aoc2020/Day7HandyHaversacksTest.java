package aoc.aoc2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7HandyHaversacksTest {

    @ParameterizedTest
    @CsvSource({"4, src/test/resources/2020/day7-demo1.txt",
            "185, src/test/resources/2020/day7.txt"})
    void numberOfBags(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day7HandyHaversacks(inputLines).numberOfBags());
    }

    @ParameterizedTest
    @CsvSource({"32, src/test/resources/2020/day7-demo1.txt",
            "126, src/test/resources/2020/day7-demo2.txt",
            "89084, src/test/resources/2020/day7.txt"})
    void numberOfBagsInside(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day7HandyHaversacks(inputLines).numberOfBagsInside());
    }
}
