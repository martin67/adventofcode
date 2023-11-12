package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 17: No Such Thing as Too Much")
class Day17NoSuchThingAsTooMuchTest {

    @ParameterizedTest
    @CsvSource({"4, 25, src/test/resources/day17-demo1.txt",
            "1638, 150, src/test/resources/day17.txt"})
    void problem1(int expected, int liters, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day17NoSuchThingAsTooMuch(inputLines).numberOfCombinations(liters))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"3, 25, src/test/resources/day17-demo1.txt",
            "17, 150, src/test/resources/day17.txt"})
    void problem2(int expected, int liters, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day17NoSuchThingAsTooMuch(inputLines).numberOfMinimumCombinations(liters))
                .isEqualTo(expected);
    }
}