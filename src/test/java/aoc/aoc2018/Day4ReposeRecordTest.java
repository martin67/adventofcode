package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2018: Day 4: Repose Record")
class Day4ReposeRecordTest {

    @ParameterizedTest
    @CsvSource({"240, src/test/resources/2018/day4-demo1.txt",
            "99911, src/test/resources/2018/day4.txt"})
    void problem1(int expected, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day4ReposeRecord day4ReposeRecord = new Day4ReposeRecord();
        assertEquals(expected, day4ReposeRecord.computeChecksum(input));
    }

    @ParameterizedTest
    @CsvSource({"4455, src/test/resources/2018/day4-demo1.txt",
            "65854, src/test/resources/2018/day4.txt"})
    void problem2(int expected, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get(fileName))));
        Day4ReposeRecord day4ReposeRecord = new Day4ReposeRecord();
        assertEquals(expected, day4ReposeRecord.computeChecksum2(input));
    }

}
