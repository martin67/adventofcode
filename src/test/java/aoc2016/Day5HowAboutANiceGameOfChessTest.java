package aoc2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5HowAboutANiceGameOfChessTest {

    @ParameterizedTest
    @CsvSource({"18f47a30, abc",
            "4543c154, ojvtpuvg"})
    void getPassword(String expected, String input) throws NoSuchAlgorithmException {
        assertEquals(expected, new Day5HowAboutANiceGameOfChess(input).getPassword());
    }

    @ParameterizedTest
    @CsvSource({"05ace8e3, abc",
            "1050cbbd, ojvtpuvg"})
    void getSecondPassword(String expected, String input) throws NoSuchAlgorithmException {
        assertEquals(expected, new Day5HowAboutANiceGameOfChess(input).getSecondPassword());
    }

}