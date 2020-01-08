package aoc.aoc2015;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5DoesntHeHaveInternElvesForThisTest {
    @ParameterizedTest
    @CsvSource({"ugknbfddgicrmopn, true",
            "jchzalrnumimnmhp, false",
            "haegwjzuvuyypxyu, false",
            "dvszwmarrgswjxmb, false"})
    void niceStringsDemo(String input, boolean expected) {
        assertEquals(expected, new Day5DoesntHeHaveInternElvesForThis().isNice(input));
    }

    @Test
    void niceStrings() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2015/day5.txt"));
        assertEquals(1783, new Day5DoesntHeHaveInternElvesForThis().isNice(inputLines));
    }
}