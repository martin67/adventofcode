package aoc.aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17TwoStepsForwardTest {

    @ParameterizedTest
    @CsvSource({"DDRRRD, ihgpwlah",
            "DDUDRLRRUDRD, kglvqrro",
            "DRURDRUDDLLDLUURRDULRLDUUDDDRR, ulqzkmiv",
            "RRRLDRDUDD, qtetzkpl"})
    void shortestPath(String expected, String passcode) {
        assertEquals(expected, new Day17TwoStepsForward(passcode).shortestPath());
    }

    @ParameterizedTest
    @CsvSource({"370, ihgpwlah",
            "492, kglvqrro",
            "830, ulqzkmiv",
            "706, qtetzkpl"})
    void longestPath(int expected, String passcode) {
        assertEquals(expected, new Day17TwoStepsForward(passcode).longestPath());
    }
}