package aoc.aoc2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2016: Day 5: How About a Nice Game of Chess?")
class Day5HowAboutANiceGameOfChessTest {

    @ParameterizedTest
    @CsvSource({"18f47a30, abc",
            "4543c154, ojvtpuvg"})
    void problem1(String expected, String input) throws NoSuchAlgorithmException {
        assertEquals(expected, new Day5HowAboutANiceGameOfChess(input).getPassword());
    }

    @ParameterizedTest
    @CsvSource({"05ace8e3, abc",
            "1050cbbd, ojvtpuvg"})
    void problem2(String expected, String input) throws NoSuchAlgorithmException {
        assertEquals(expected, new Day5HowAboutANiceGameOfChess(input).getSecondPassword());
    }

}