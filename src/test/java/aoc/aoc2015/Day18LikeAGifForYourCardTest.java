package aoc.aoc2015;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18LikeAGifForYourCardTest {

    @ParameterizedTest
    @CsvSource({"4, 4, src/test/resources/2015/day18-demo1.txt",
            "814, 100, src/test/resources/2015/day18.txt"})
    void numberOfLights(int expected, int steps, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day18LikeAGifForYourCard(inputLines).numberOfLights(steps, false));
    }

    @ParameterizedTest
    @CsvSource({"17, 5, src/test/resources/2015/day18-demo1.txt",
            "924, 100, src/test/resources/2015/day18.txt"})
    void numberOfLightsWithCorners(int expected, int steps, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day18LikeAGifForYourCard(inputLines).numberOfLights(steps, true));
    }

}