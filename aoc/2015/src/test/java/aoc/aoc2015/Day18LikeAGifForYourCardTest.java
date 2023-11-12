package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 18: Like a GIF For Your Yard")
class Day18LikeAGifForYourCardTest {

    @ParameterizedTest
    @CsvSource({"4, 4, src/test/resources/day18-demo1.txt",
            "814, 100, src/test/resources/day18.txt"})
    void problem1(int expected, int steps, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day18LikeAGifForYourCard(inputLines).numberOfLights(steps, false))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"17, 5, src/test/resources/day18-demo1.txt",
            "924, 100, src/test/resources/day18.txt"})
    void problem2(int expected, int steps, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertThat(new Day18LikeAGifForYourCard(inputLines).numberOfLights(steps, true))
                .isEqualTo(expected);
    }
}