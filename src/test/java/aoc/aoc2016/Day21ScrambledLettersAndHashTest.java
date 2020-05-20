package aoc.aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day21ScrambledLettersAndHashTest {

    @ParameterizedTest
    @CsvSource({"decab, abcde, src/test/resources/2016/day21-demo1.txt",
            "blabla, abcdefgh, src/test/resources/2016/day21.txt"})
    void scrambledPassword(String expected, String input, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day21ScrambledLettersAndHash(inputLines).scramble(input));
    }
}