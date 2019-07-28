import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day17ReservoirResearchTest {
    @ParameterizedTest
    @CsvSource({
            "57, day17-demo.txt",
            "39162, day17.txt"
    })
    void NumberofWaterTiles(int outcome, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("out/test/resources/" + fileName))));
        Day17ReservoirResearch day17ReservoirResearch = new Day17ReservoirResearch();
        assertEquals(outcome, day17ReservoirResearch.numberOfWaterTiles(input));
    }

    @ParameterizedTest
    @CsvSource({
            "29, day17-demo.txt",
            "32047, day17.txt"
    })
    void NumberOfRemainingWater(int outcome, String fileName) throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("out/test/resources/" + fileName))));
        Day17ReservoirResearch day17ReservoirResearch = new Day17ReservoirResearch();
        assertEquals(outcome, day17ReservoirResearch.numberOfRemainingWater(input));
    }
}