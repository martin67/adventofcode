package aoc.aoc2015;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 1: Not Quite Lisp")
class Day1NotQuiteLispTest {
    @ParameterizedTest
    @CsvSource({"(()), 0",
            "()(), 0",
            "(((, 3",
            "(()(()(, 3",
            "))(((((, 3",
            "()), -1",
            "()), -1",
            "))), -3",
            ")())()), -3"})
    void getFloorDemo(String input, int expected) {
        assertThat(new Day1NotQuiteLisp().getFloor(input)).isEqualTo(expected);
    }

    @Test
    void getFloor() throws IOException {
        var inputLines = AocFiles.readAllLines("day1.txt");
        assertThat(new Day1NotQuiteLisp().getFloor(inputLines.get(0))).isEqualTo(232);
    }

    @ParameterizedTest
    @CsvSource({"), 1",
            "()()), 5"})
    void getPositionDemo(String input, int expected) {
        assertThat(new Day1NotQuiteLisp().getPosition(input)).isEqualTo(expected);
    }

    @Test
    void getPosition() throws IOException {
        var inputLines = AocFiles.readAllLines("day1.txt");
        assertThat(new Day1NotQuiteLisp().getPosition(inputLines.get(0))).isEqualTo(1783);
    }
}