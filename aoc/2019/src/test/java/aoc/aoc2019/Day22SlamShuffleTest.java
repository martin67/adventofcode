package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 22: Slam Shuffle")
class Day22SlamShuffleTest {

    @ParameterizedTest
    @CsvSource({"0, 10, day22-demo1.txt",
            "0, 10, day22-demo2.txt",
            "0, 10, day22-demo3.txt",
            "0, 10, day22-demo4.txt",
            "4086, 10007, day22.txt"})
    void problem1(int expected, int numberOfCards, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day22SlamShuffle(inputLines).cardPosition(numberOfCards)).isEqualTo(expected);
    }

    @Test
    void problem2() throws IOException {
        var inputLines = AocFiles.readAllLines("day22.txt");
        assertThat(new Day22SlamShuffle(inputLines).numberOnCard("119315717514047", "101741582076661"))
                .isEqualTo("1041334417227");
    }
}
