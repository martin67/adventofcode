package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 18: Boiling Boulders")
class Day18BoilingBouldersTest {

    @ParameterizedTest
    @CsvSource({"10, src/test/resources/day18-demo1.txt",
            "64, src/test/resources/day18-demo2.txt",
            "4636, src/test/resources/day18.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day18BoilingBoulders(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"58, src/test/resources/day18-demo2.txt",
            "2572, src/test/resources/day18.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day18BoilingBoulders(inputLines).problem2())
                .isEqualTo(expected);
    }

}