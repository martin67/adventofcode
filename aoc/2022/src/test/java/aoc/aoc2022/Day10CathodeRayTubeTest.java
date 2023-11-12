package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 10: Cathode-Ray Tube")
class Day10CathodeRayTubeTest {

    @ParameterizedTest
    @CsvSource({"13140, src/test/resources/day10-demo1.txt",
            "15360, src/test/resources/day10.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day10CathodeRayTube(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"605, src/test/resources/day10-demo1.txt",
            "-281, src/test/resources/day10.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day10CathodeRayTube(inputLines).problem2())
                .isEqualTo(expected);
    }

}