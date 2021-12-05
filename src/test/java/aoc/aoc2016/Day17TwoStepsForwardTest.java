package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2016: Day 17: Two Steps Forward")
class Day17TwoStepsForwardTest {

    @ParameterizedTest
    @CsvSource({"DDRRRD, ihgpwlah",
            "DDUDRLRRUDRD, kglvqrro",
            "DRURDRUDDLLDLUURRDULRLDUUDDDRR, ulqzkmiv",
            "RRRLDRDUDD, qtetzkpl"})
    void problem1(String expected, String passcode) {
        assertEquals(expected, new Day17TwoStepsForward(passcode).shortestPath());
    }

    @ParameterizedTest
    @CsvSource({"370, ihgpwlah",
            "492, kglvqrro",
            "830, ulqzkmiv",
            "706, qtetzkpl"})
    void problem2(int expected, String passcode) {
        assertEquals(expected, new Day17TwoStepsForward(passcode).longestPath());
    }
}