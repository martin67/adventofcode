package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 25: Four-Dimensional Adventure")
class Day25FourDimensionalAdventureTest {

    @ParameterizedTest
    @CsvSource({"2, src/test/resources/day25-demo1.txt",
            "4, src/test/resources/day25-demo2.txt",
            "3, src/test/resources/day25-demo3.txt",
            "8, src/test/resources/day25-demo4.txt",
            "338, src/test/resources/day25.txt"})
    void problem1(int expected, String fileName) throws IOException {
        assertThat(new Day25FourDimensionalAdventure(fileName).numberOfConstellations()).isEqualTo(expected);
    }
}