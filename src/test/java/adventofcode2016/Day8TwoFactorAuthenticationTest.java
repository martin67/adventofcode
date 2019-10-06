package adventofcode2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8TwoFactorAuthenticationTest {

    @ParameterizedTest
    @CsvSource({"6, 7, 3, out/test/resources/2016/day8-demo1.txt",
            "106, 50, 6, out/test/resources/2016/day8.txt"})
    void pixelsLit(long expected, int screenWidth, int screenHeight, String fileName) throws IOException {
        assertEquals(expected, new Day8TwoFactorAuthentication(screenWidth, screenHeight, fileName).pixelsLit());
    }

}