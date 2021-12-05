package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2017: Day 9: Stream Processing")
class Day9StreamProcessingTest {

    @ParameterizedTest
    @CsvSource({"1, {}",
            "6, {{{}}}",
            "5, '{{},{}}'",
            "16, '{{{},{},{{}}}}'",
            "1, '{<a>,<a>,<a>,<a>}'",
            "9, '{{<ab>},{<ab>},{<ab>},{<ab>}}'",
            "9, '{{<!!>},{<!!>},{<!!>},{<!!>}}'",
            "3, '{{<a!>},{<a!>},{<a!>},{<ab>}}'"})
    void score(int expected, String stream) {
        assertEquals(expected, new Day9StreamProcessing(stream).score());
    }

    @ParameterizedTest
    @CsvSource({"13154, src/test/resources/2017/day9.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day9StreamProcessing(inputLines.get(0)).score());
    }

    @ParameterizedTest
    @CsvSource({"0, <>",
            "17, <random characters>",
            "3, <<<<>",
            "0, {{{}}}",
            "0, '{{},{}}'",
            "0, '{{{},{},{{}}}}'",
            "4, '{<a>,<a>,<a>,<a>}'",
            "8, '{{<ab>},{<ab>},{<ab>},{<ab>}}'",
            "0, '{{<!!>},{<!!>},{<!!>},{<!!>}}'",
            "2, <{!>}>",
            "0, <!!>",
            "0, <!!!>>",
            "10, '<{o\"i!a,<{i<a>'"})
    void garbage(int expected, String stream) {
        assertEquals(expected, new Day9StreamProcessing(stream).garbage());
    }

    @ParameterizedTest
    @CsvSource({"6369, src/test/resources/2017/day9.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day9StreamProcessing(inputLines.get(0)).garbage());
    }
}
