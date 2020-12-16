package aoc.aoc2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13ShuttleSearchTest {

    @ParameterizedTest
    @CsvSource({"295, src/test/resources/2020/day13-demo1.txt",
            "2935, src/test/resources/2020/day13.txt"})
    void busWait(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day13ShuttleSearch(inputLines).busWait());
    }

    @ParameterizedTest
    @CsvSource({"1068781, '7,13,x,x,59,x,31,19'",
            "3417, '17,x,13,19'",
            "754018, '67,7,59,61'",
            "779210, '67,x,7,59,61'",
            "1261476, '67,7,x,59,61'",
            "1202161486, '1789,37,47,1889'"})
    void earliestTimestamp(long expected, String input) {
        assertEquals(expected, new Day13ShuttleSearch(input).earliestTimestamp());
    }

    @ParameterizedTest
    @CsvSource({"1068781, src/test/resources/2020/day13-demo1.txt",
            "836024966345345, src/test/resources/2020/day13.txt"})
    void earliestTimestampFromFile(long expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day13ShuttleSearch(inputLines.get(1)).earliestTimestamp());
    }

}
