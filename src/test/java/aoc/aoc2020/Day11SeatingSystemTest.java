package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 11: Seating System")
class Day11SeatingSystemTest {

    @ParameterizedTest
    @CsvSource({"37, src/test/resources/2020/day11-demo1.txt",
            "2204, src/test/resources/2020/day11.txt"})
    void seatsOccupied(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day11SeatingSystem(inputLines).seatsOccupied());
    }

    @ParameterizedTest
    @CsvSource({"26, src/test/resources/2020/day11-demo1.txt",
            "1986, src/test/resources/2020/day11.txt"})
    void seatsOccupied2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day11SeatingSystem(inputLines).seatsOccupied2());
    }

}
