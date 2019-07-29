import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day18SettlersOfTheNorthPoleTest {

    @ParameterizedTest
    @CsvSource({
            "1147, day18-demo.txt",
            "588436, day18.txt"
    })
    void computeResourceValue(int outcome, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("out/test/resources/" + fileName))));
        Day18SettlersOfTheNorthPole day18SettlersOfTheNorthPole = new Day18SettlersOfTheNorthPole(input);
        assertEquals(outcome, day18SettlersOfTheNorthPole.computeResourceValue());
    }
}