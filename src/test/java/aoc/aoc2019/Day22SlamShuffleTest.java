package aoc.aoc2019;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2019: Day 22: Slam Shuffle")
class Day22SlamShuffleTest {

    @ParameterizedTest
    @CsvSource({"0, 10, src/test/resources/2019/day22-demo1.txt",
            "0, 10, src/test/resources/2019/day22-demo2.txt",
            "0, 10, src/test/resources/2019/day22-demo3.txt",
            "0, 10, src/test/resources/2019/day22-demo4.txt",
            "4086, 10007, src/test/resources/2019/day22.txt"})
    void problem1(int expected, int numberOfCards, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day22SlamShuffle(inputLines).cardPosition(numberOfCards));
    }

    @Test
    void problem2() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2019/day22.txt"));
        assertEquals("1041334417227", new Day22SlamShuffle(inputLines).numberOnCard("119315717514047", "101741582076661"));
    }

}
