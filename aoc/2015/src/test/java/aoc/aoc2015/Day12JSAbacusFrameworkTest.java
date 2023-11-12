package aoc.aoc2015;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 12: JSAbacusFramework.io")
class Day12JSAbacusFrameworkTest {

    @ParameterizedTest
    @CsvSource({"191164, day12.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12JSAbacusFramework(inputLines).sumOfAllNumbers()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"6; [1,2,3]",
            "4; [1,{\"c\":\"red\",\"b\":2},3]",
            "0; {\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}",
            "6; [1,\"red\",5]",
            "0; []",
            "0; {}"}, delimiter = ';')
    void ignoringRed(int expected, String in) {
        assertThat(new Day12JSAbacusFramework(Collections.singletonList(in)).sumOfAllNumbersIgnoringRed())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"87842, day12.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day12JSAbacusFramework(inputLines).sumOfAllNumbersIgnoringRed()).isEqualTo(expected);
    }
}