package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2021: Day 3: Binary Diagnostic")
class Day3BinaryDiagnosticTest {

    @ParameterizedTest
    @CsvSource({"198, src/test/resources/day3-demo1.txt",
            "3895776, src/test/resources/day3.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day3BinaryDiagnostic(inputLines).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"230, src/test/resources/day3-demo1.txt",
            "7928162, src/test/resources/day3.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day3BinaryDiagnostic(inputLines).problem2())
                .isEqualTo(expected);
    }

}