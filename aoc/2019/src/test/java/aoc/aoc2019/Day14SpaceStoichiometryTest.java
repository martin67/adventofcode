package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2019: Day 14: Space Stoichiometry")
class Day14SpaceStoichiometryTest {

    @ParameterizedTest
    @CsvSource({"31, src/test/resources/day14-demo1.txt",
            "165, src/test/resources/day14-demo2.txt",
            "13312, src/test/resources/day14-demo3.txt",
            "180697, src/test/resources/day14-demo4.txt",
            "2210736, src/test/resources/day14-demo5.txt",
            "741927, src/test/resources/day14.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day14SpaceStoichiometry(inputLines).minimumAmountOfOre());
    }

    @ParameterizedTest
    @CsvSource({"82892753, src/test/resources/day14-demo3.txt",
            "5586022, src/test/resources/day14-demo4.txt",
            "460664, src/test/resources/day14-demo5.txt",
            "2371699, src/test/resources/day14.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day14SpaceStoichiometry(inputLines).maximumAmountOfFuel());
    }

}