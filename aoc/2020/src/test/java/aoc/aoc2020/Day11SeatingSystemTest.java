package aoc.aoc2020;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2020: Day 11: Seating System")
class Day11SeatingSystemTest {

    @ParameterizedTest
    @CsvSource({"37, day11-demo1.txt",
            "2204, day11.txt"})
    void problem1(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day11SeatingSystem(inputLines).seatsOccupied()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"26, day11-demo1.txt",
            "1986, day11.txt"})
    void problem2(int expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day11SeatingSystem(inputLines).seatsOccupied2()).isEqualTo(expected);
    }
}
