package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 11: Hex Ed")
class Day11HexEdTest {

    @ParameterizedTest
    @CsvSource({"3, 'ne,ne,ne'",
            "0,'ne,ne,sw,sw'",
            "2, 'ne,ne,s,s'",
            "3, 'se,sw,se,sw,sw'"})
    void fewestSteps(int expected, String directions) {
        assertThat(new Day11HexEd(directions).fewestSteps()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"877, day11.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day11HexEd(inputLines.get(0)).fewestSteps()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1622, day11.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day11HexEd(inputLines.get(0)).furthestAway()).isEqualTo(expected);
    }
}