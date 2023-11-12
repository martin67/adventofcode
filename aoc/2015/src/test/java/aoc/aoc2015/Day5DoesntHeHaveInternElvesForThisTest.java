package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2015: Day 5: Doesn't He Have Intern-Elves For This?")
class Day5DoesntHeHaveInternElvesForThisTest {
    @ParameterizedTest
    @CsvSource({"ugknbfddgicrmopn, true",
            "aaa, true",
            "jchzalrnumimnmhp, false",
            "haegwjzuvuyypxyu, false",
            "dvszwmarrgswjxmb, false"})
    void niceStringsDemo(String input, boolean expected) {
        assertThat(new Day5DoesntHeHaveInternElvesForThis().isNice(input))
                .isEqualTo(expected);
    }

    @Test
    void niceStrings() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/day5.txt"));
        assertThat(new Day5DoesntHeHaveInternElvesForThis().isNice(inputLines))
                .isEqualTo(238);
    }

    @ParameterizedTest
    @CsvSource({"qjhvhtzxzqqjkmpb, true",
            "xxyxx, true",
            "uurcxstgmygtbstg, false",
            "ieodomkazucvgmuy, false"})
    void niceStringsNewRulesDemo(String input, boolean expected) {
        assertThat(new Day5DoesntHeHaveInternElvesForThis().isNiceNewRule(input))
                .isEqualTo(expected);
    }

    @Test
    void niceStringsNewRules() throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get("src/test/resources/day5.txt"));
        assertThat(new Day5DoesntHeHaveInternElvesForThis().isNiceNewRule(inputLines))
                .isEqualTo(69);
    }
}