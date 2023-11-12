package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 5: Alchemical Reduction")
class Day5AlchemicalReductionTest {

    @ParameterizedTest
    @CsvSource({"10, src/test/resources/day5-demo1.txt",
            "9526, src/test/resources/day5.txt"})
    void problem1(int expected, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day5AlchemicalReduction day5AlchemicalReduction = new Day5AlchemicalReduction();
        assertThat(day5AlchemicalReduction.getPolymerLength(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"4, src/test/resources/day5-demo1.txt",
            "6694, src/test/resources/day5.txt"})
    void problem2(int expected, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day5AlchemicalReduction day5AlchemicalReduction = new Day5AlchemicalReduction();
        assertThat(day5AlchemicalReduction.findShortestPolymer(input)).isEqualTo(expected);
    }
}