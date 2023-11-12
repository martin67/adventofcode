package aoc.aoc2016;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2016: Day 11: Radioisotope Thermoelectric Generators")
class Day11RadioisotopeThermoelectricGeneratorsTest {

    @ParameterizedTest
    @CsvSource({"11, src/test/resources/day11-demo1.txt",
            "37, src/test/resources/day11.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertThat(new Day11RadioisotopeThermoelectricGenerators(fileName).minimumNumberOfSteps()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"61, src/test/resources/day11-2.txt"})
    @Disabled
    void problem2(int expected, String fileName) throws IOException {
        assertThat(new Day11RadioisotopeThermoelectricGenerators(fileName).minimumNumberOfSteps()).isEqualTo(expected);
    }
}