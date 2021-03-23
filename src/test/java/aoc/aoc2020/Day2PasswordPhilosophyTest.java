package aoc.aoc2020;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 2: Password Philosophy")
class Day2PasswordPhilosophyTest {

    @ParameterizedTest
    @CsvSource({"2, src/test/resources/2020/day2-demo1.txt",
            "603, src/test/resources/2020/day2.txt"})
    void validPasswords(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day2PasswordPhilosophy(inputLines).validPasswords());
    }

    @ParameterizedTest
    @CsvSource({"1, src/test/resources/2020/day2-demo1.txt",
            "404, src/test/resources/2020/day2.txt"})
    void validPasswordsNewPolicy(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day2PasswordPhilosophy(inputLines).validPasswordsNewPolicy());
    }

}