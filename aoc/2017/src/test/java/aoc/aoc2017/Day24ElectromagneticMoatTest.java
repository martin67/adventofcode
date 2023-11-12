package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 24: Electromagnetic Moat")
class Day24ElectromagneticMoatTest {

    @ParameterizedTest
    @CsvSource({"31, src/test/resources/day24-demo1.txt",
            "1940, src/test/resources/day24.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertThat(new Day24ElectromagneticMoat(Files.readAllLines(Paths.get(fileName))).problem1())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"19, src/test/resources/day24-demo1.txt",
            "1928, src/test/resources/day24.txt"})
    void problem2(int expected, String fileName) throws IOException {
        assertThat(new Day24ElectromagneticMoat(Files.readAllLines(Paths.get(fileName))).problem2())
                .isEqualTo(expected);
    }

}