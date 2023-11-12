package aoc.aoc2015;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 18: Like a GIF For Your Yard")
class Day18LikeAGifForYourCardTest {

    @ParameterizedTest
    @CsvSource({"4, 4, day18-demo1.txt",
            "814, 100, day18.txt"})
    void problem1(int expected, int steps, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day18LikeAGifForYourCard(inputLines).numberOfLights(steps, false)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"17, 5, day18-demo1.txt",
            "924, 100, day18.txt"})
    void problem2(int expected, int steps, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day18LikeAGifForYourCard(inputLines).numberOfLights(steps, true)).isEqualTo(expected);
    }
}