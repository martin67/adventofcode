import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Day24ImmuneSystemSimulator20XXTest {

    @ParameterizedTest
    @CsvSource({"5216, out/test/resources/day24-demo0.txt",
            "0, out/test/resources/day24.txt"})
    void winningArmyUnits(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day24ImmuneSystemSimulator20XX(fileName).winningArmyUnits());
    }
}