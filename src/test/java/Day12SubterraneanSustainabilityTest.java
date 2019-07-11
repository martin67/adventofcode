import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12SubterraneanSustainabilityTest {

    @Test
    void ReadDemoData() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("build/resources/test/day12-1.txt"))));
        Day12SubterraneanSustainability day12SubterraneanSustainability = new Day12SubterraneanSustainability(input);
        assertEquals(325, day12SubterraneanSustainability.ComputePlantSum());
    }

    @Test
    void ReadData() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("build/resources/test/day12-2.txt"))));
        Day12SubterraneanSustainability day12SubterraneanSustainability = new Day12SubterraneanSustainability(input);
        assertEquals(3061, day12SubterraneanSustainability.ComputePlantSum());
    }

}