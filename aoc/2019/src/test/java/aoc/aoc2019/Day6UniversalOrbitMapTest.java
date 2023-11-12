package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 6: Universal Orbit Map")
class Day6UniversalOrbitMapTest {

    @ParameterizedTest
    @CsvSource({"42, day6-demo1.txt",
            "119831, day6.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day6UniversalOrbitMap(inputLines).numberOfOrbits()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"4, day6-demo2.txt",
            "322, day6.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day6UniversalOrbitMap(inputLines).minimumNumberOfOrbits()).isEqualTo(expected);
    }
}