import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day1ChronalCalibrationTest {

    @ParameterizedTest
    @CsvSource({
            "0 1, 1",
            "1 -2, -1",
            "-1 3, 2",
            "2 1, 3",
            "1 1 1, 3",
            "1 1 -2, 0",
            "-1 -2 -3, -6"
    })
    void computeFrequency(String input, Integer expected) {
        Day1ChronalCalibration day1ChronalCalibration = new Day1ChronalCalibration();
        assertEquals(expected, day1ChronalCalibration.computeFrequency(input));
    }

    @Test
    void computeFrequencyFromFile() throws IOException {
        Day1ChronalCalibration day1ChronalCalibration = new Day1ChronalCalibration();
        String input = new String((Files.readAllBytes(Paths.get("build/resources/test/day1-1.txt"))));

        assertEquals(582, day1ChronalCalibration.computeFrequency(input));
    }

}