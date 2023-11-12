package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 25: Combo Breaker")
class Day25ComboBreakerTest {

    @ParameterizedTest
    @CsvSource({"14897079, day25-demo1.txt",
            "7032853, day25.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day25ComboBreaker(inputLines).problem1()).isEqualTo(expected);
    }
}