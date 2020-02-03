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
            "aaa, true",
            "jchzalrnumimnmhp, false",
            "haegwjzuvuyypxyu, false",
            "dvszwmarrgswjxmb, false"})
    void niceStringsDemo(String input, boolean expected) {
        assertEquals(expected, new Day5DoesntHeHaveInternElvesForThis().isNice(input));
    }

    @Test
    void niceStrings() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2015/day5.txt"));
        assertEquals(238, new Day5DoesntHeHaveInternElvesForThis().isNice(inputLines));
    }

    @ParameterizedTest
    @CsvSource({"qjhvhtzxzqqjkmpb, true",
            "xxyxx, true",
            "uurcxstgmygtbstg, false",
            "ieodomkazucvgmuy, false"})
    void niceStringsNewRulesDemo(String input, boolean expected) {
        assertEquals(expected, new Day5DoesntHeHaveInternElvesForThis().isNiceNewRule(input));
    }

    @Test
    void niceStringsNewRules() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/2015/day5.txt"));
        assertEquals(68, new Day5DoesntHeHaveInternElvesForThis().isNiceNewRule(inputLines));
    }
}