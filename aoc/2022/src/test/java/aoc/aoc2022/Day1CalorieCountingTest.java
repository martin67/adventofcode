package aoc.aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2022: Day 1: Calorie Counting")
class Day1CalorieCountingTest {

    @ParameterizedTest
    @CsvSource({"24000, src/test/resources/day1-demo1.txt",
            "68292, src/test/resources/day1.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day1CalorieCounting(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"45000, src/test/resources/day1-demo1.txt",
            "203203, src/test/resources/day1.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day1CalorieCounting(inputLines).problem2())
                .isEqualTo(expected);
    }

}