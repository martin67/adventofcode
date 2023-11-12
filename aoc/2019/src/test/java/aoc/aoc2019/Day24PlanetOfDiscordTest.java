package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 24: Planet of Discord")
class Day24PlanetOfDiscordTest {

    @ParameterizedTest
    @CsvSource({"2129920, day24-demo1.txt",
            "28778811, day24.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day24PlanetOfDiscord(inputLines).biodiversityRating()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"99, 10, day24-demo1.txt",
            "2097, 200, day24.txt"})
    void problem2(int expected, int minutes, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day24PlanetOfDiscord(inputLines).bugsPresent(minutes)).isEqualTo(expected);
    }
}