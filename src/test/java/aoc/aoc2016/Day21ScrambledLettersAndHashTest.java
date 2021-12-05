package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2016: Day 21: Scrambled Letters and Hash")
class Day21ScrambledLettersAndHashTest {

    @ParameterizedTest
    @CsvSource({"decab, abcde, src/test/resources/2016/day21-demo1.txt",
            "dbfgaehc, abcdefgh, src/test/resources/2016/day21.txt"})
    void problem1(String expected, String input, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day21ScrambledLettersAndHash(inputLines).scramble(input));
    }

    @ParameterizedTest
    @CsvSource({"abcdefgh, dbfgaehc, src/test/resources/2016/day21.txt",
            "aghfcdeb, fbgdceah, src/test/resources/2016/day21.txt"})
    void problem2(String expected, String input, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day21ScrambledLettersAndHash(inputLines).crack(input));
    }
}