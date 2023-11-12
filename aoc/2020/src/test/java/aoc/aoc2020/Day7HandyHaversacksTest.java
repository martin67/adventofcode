package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 7: Handy Haversacks")
class Day7HandyHaversacksTest {

    @ParameterizedTest
    @CsvSource({"4, day7-demo1.txt",
            "185, day7.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day7HandyHaversacks(inputLines).numberOfBags()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"32, day7-demo1.txt",
            "126, day7-demo2.txt",
            "89084, day7.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day7HandyHaversacks(inputLines).numberOfBagsInside()).isEqualTo(expected);
    }
}
