package aoc.aoc2023;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("2023: Day 5: If You Give A Seed A Fertilizer")
class Day5IfYouGiveASeedAFertilizerTest {

    @ParameterizedTest
    @CsvSource({"35, day5-demo1.txt",
            "57075758, day5.txt"})
    void problem1(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day5IfYouGiveASeedAFertilizer(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"46, day5-demo1.txt",
            "31161857, day5.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day5IfYouGiveASeedAFertilizer(inputLines).problem2()).isEqualTo(expected);
    }
}