package aoc.aoc2017;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 21: Fractal Art")
class Day21FractalArtTest {

    @ParameterizedTest
    @CsvSource({"12, 2, day21-demo1.txt",
            "194, 5, day21.txt",
            "2536879, 18, day21.txt"})
    void problem1(int expected, int iterations, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day21FractalArt(inputLines).problem1(iterations)).isEqualTo(expected);
    }
}
