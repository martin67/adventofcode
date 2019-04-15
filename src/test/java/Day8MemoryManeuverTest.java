import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day8MemoryManeuverTest {

    @Test
    void computeSumOfMetadata() {
            String input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";
            Day8MemoryManeuver day8MemoryManeuver = new Day8MemoryManeuver();
            assertEquals(138, day8MemoryManeuver.computeSumOfMetadata(input));
    }

    @Test
    void computeSumOfMetadataFromFile() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("build/resources/test/day8.txt"))));
        Day8MemoryManeuver day8MemoryManeuver = new Day8MemoryManeuver();
        assertEquals(37439, day8MemoryManeuver.computeSumOfMetadata(input));
    }

    @Test
    void computeRootNode() {
        String input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";
        Day8MemoryManeuver day8MemoryManeuver = new Day8MemoryManeuver();
        assertEquals(138, day8MemoryManeuver.computeRootNode(input));
    }
}