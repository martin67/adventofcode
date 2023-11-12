package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 21: Allergen Assessment")
class Day21AllergenAssessmentTest {

    @ParameterizedTest
    @CsvSource({"5, day21-demo1.txt",
            "2798, day21.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day21AllergenAssessment(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"'mxmxvkd,sqjhc,fvjkl', day21-demo1.txt",
            "'gbt,rpj,vdxb,dtb,bqmhk,vqzbq,zqjm,nhjrzzj', day21.txt"})
    void problem2(String expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day21AllergenAssessment(inputLines).problem2()).isEqualTo(expected);
    }
}