package aoc.aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2021: Day 18: Snailfish")
class Day18SnailfishTest {

    @ParameterizedTest
    @CsvSource({"'[[[[0,9],2],3],4]', '[[[[[9,8],1],2],3],4]'",
            "'[7,[6,[5,[7,0]]]]', '[7,[6,[5,[4,[3,2]]]]]'",
            "'[[6,[5,[7,0]]],3]', '[[6,[5,[4,[3,2]]]],1]'",
            "'[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]', '[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]'",
            "'[[3,[2,[8,0]]],[9,[5,[7,0]]]]', '[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]'"})
    void explode(String expected, String input) {
        assertEquals(expected, new Day18Snailfish().explode(input));
    }

    @ParameterizedTest
    @CsvSource({"0, src/test/resources/2021/day18-demo1.txt",
            "0, src/test/resources/2021/day18.txt"})
    void problem1(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day18Snailfish(inputLines).problem1());
    }

    @ParameterizedTest
    @CsvSource({"0, src/test/resources/2021/day18-demo1.txt",
            "0, src/test/resources/2021/day18.txt"})
    void problem2(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day18Snailfish(inputLines).problem2());
    }

}