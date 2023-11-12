package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 14: Space Stoichiometry")
class Day14SpaceStoichiometryTest {

    @ParameterizedTest
    @CsvSource({"31, day14-demo1.txt",
            "165, day14-demo2.txt",
            "13312, day14-demo3.txt",
            "180697, day14-demo4.txt",
            "2210736, day14-demo5.txt",
            "741927, day14.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day14SpaceStoichiometry(inputLines).minimumAmountOfOre()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"82892753, day14-demo3.txt",
            "5586022, day14-demo4.txt",
            "460664, day14-demo5.txt",
            "2371699, day14.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day14SpaceStoichiometry(inputLines).maximumAmountOfFuel()).isEqualTo(expected);
    }
}