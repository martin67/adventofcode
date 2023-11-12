package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 7: Recursive Circus")
class Day7RecursiveCircusTest {

    @ParameterizedTest
    @CsvSource({"tknk, day7-demo1.txt",
            "vmpywg, day7.txt"})
    void problem1(String expected, String fileName) throws IOException {
        assertThat(new Day7RecursiveCircus(AocFiles.readAllLines(fileName)).bottomProgramName()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"60, day7-demo1.txt",
            "1674, day7.txt"})
    void problem2(int expected, String fileName) throws IOException {
        //assertThat(expected, new Day7RecursiveCircus(Files.readAllLines(Paths.get(fileName))).balanceWeight());
        assertThat(new Day7RecursiveCircus(AocFiles.readAllLines(fileName)).computeWeights()).isEqualTo(expected);
    }
}