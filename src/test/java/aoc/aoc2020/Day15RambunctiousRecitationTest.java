package aoc.aoc2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15RambunctiousRecitationTest {

    @ParameterizedTest
    @CsvSource({"436, '0,3,6'",
            "1, '1,3,2'",
            "10, '2,1,3'",
            "27, '1,2,3'",
            "78, '2,3,1'",
            "438, '3,2,1'",
            "1836, '3,1,2'",
            "870, '11,0,1,10,5,19'"})
    void problem1(long expected, String numbers) {
        assertEquals(expected, new Day15RambunctiousRecitation(numbers).numberSpoken(2020));
    }

    @ParameterizedTest
    @CsvSource({"175594, '0,3,6'",
            "2578, '1,3,2'",
            "3544142, '2,1,3'",
            "261214, '1,2,3'",
            "6895259, '2,3,1'",
            "18, '3,2,1'",
            "362, '3,1,2'",
            "9136, '11,0,1,10,5,19'"})
    void problem2(long expected, String numbers) {
        assertEquals(expected, new Day15RambunctiousRecitation(numbers).numberSpoken(30000000));
    }
}
