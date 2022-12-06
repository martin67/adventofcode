package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2021: Day 1: Sonar Sweep")
class Day1SonarSweepTest {

    @ParameterizedTest
    @CsvSource({"7, src/test/resources/2021/day1-demo1.txt",
            "1298, src/test/resources/2021/day1.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day1SonarSweep(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"5, src/test/resources/2021/day1-demo2.txt",
            "1248, src/test/resources/2021/day1.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day1SonarSweep(inputLines).problem2())
                .isEqualTo(expected);
    }

}