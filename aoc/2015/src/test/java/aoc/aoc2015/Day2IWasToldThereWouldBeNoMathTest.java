package aoc.aoc2015;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 2: I Was Told There Would Be No Math Test")
class Day2IWasToldThereWouldBeNoMathTest {
    @ParameterizedTest
    @CsvSource({"2x3x4, 58",
            "1x1x10, 43"})
    void getSquareFeetDemo(String input, int expected) throws Exception {
        assertThat(new Day2IWasToldThereWouldBeNoMath().getSquareFeet(Collections.singletonList(input)))
                .isEqualTo(expected);
    }

    @Test
    void getSquareFeet() throws Exception {
        var inputLines = AocFiles.readAllLines("day2.txt");
        assertThat(new Day2IWasToldThereWouldBeNoMath().getSquareFeet(inputLines)).isEqualTo(1588178);
    }

    @ParameterizedTest
    @CsvSource({"2x3x4, 34",
            "1x1x10, 14"})
    void getRibbonDemo(String input, int expected) {
        assertThat(new Day2IWasToldThereWouldBeNoMath().getRibbon(Collections.singletonList(input)))
                .isEqualTo(expected);
    }

    @Test
    void getRibbon() throws IOException {
        var inputLines = AocFiles.readAllLines("day2.txt");
        assertThat(new Day2IWasToldThereWouldBeNoMath().getRibbon(inputLines)).isEqualTo(3783758);
    }
}