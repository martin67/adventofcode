import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day23ExperimentalEmergencyTeleportationTest {

    @ParameterizedTest
    @CsvSource({"7, out/test/resources/day23-demo0.txt",
            "935, out/test/resources/day23.txt"})
    void nanoBotsInRange(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day23ExperimentalEmergencyTeleportation(fileName).nanoBotsInRange());
    }
}