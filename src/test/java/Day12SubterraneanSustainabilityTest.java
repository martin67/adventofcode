import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Day12SubterraneanSustainabilityTest {

    @Test
    void ReadData() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("build/resources/test/day12-1.txt"))));
        Day12SubterraneanSustainability day12SubterraneanSustainability = new Day12SubterraneanSustainability(input);
        day12SubterraneanSustainability.CheckInitialState();
    }

}