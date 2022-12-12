package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 12: Hill Climbing Algorithm")
class Day12HillClimbingAlgorithmTest {

    @ParameterizedTest
    @CsvSource({"31, src/test/resources/2022/day12-demo1.txt",
            "391, src/test/resources/2022/day12.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day12HillClimbingAlgorithm(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"29, src/test/resources/2022/day12-demo1.txt",
            "386, src/test/resources/2022/day12.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day12HillClimbingAlgorithm(inputLines).problem2())
                .isEqualTo(expected);
    }

}