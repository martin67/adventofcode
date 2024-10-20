package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 13: Shuttle Search")
class Day13ShuttleSearchTest {

    @ParameterizedTest
    @CsvSource({"295, day13-demo1.txt",
            "2935, day13.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day13ShuttleSearch(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1068781, '7,13,x,x,59,x,31,19'",
            "3417, '17,x,13,19'",
            "754018, '67,7,59,61'",
            "779210, '67,x,7,59,61'",
            "1261476, '67,7,x,59,61'",
            "1202161486, '1789,37,47,1889'"})
    void earliestTimestamp(long expected, String input) {
        assertThat(new Day13ShuttleSearch(input).problem2()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1068781, day13-demo1.txt",
            "836024966345345, day13.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day13ShuttleSearch(inputLines.get(1)).problem2()).isEqualTo(expected);
    }
}
