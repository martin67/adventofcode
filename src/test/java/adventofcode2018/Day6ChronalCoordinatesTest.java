package adventofcode2018;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day6ChronalCoordinatesTest {

    @Test
    void largestSize() {
        String input = "1, 1\n" +
                "1, 6\n" +
                "8, 3\n" +
                "3, 4\n" +
                "5, 5\n" +
                "8, 9";
        Day6ChronalCoordinates day6ChronalCoordinates = new Day6ChronalCoordinates();
        assertEquals(17, day6ChronalCoordinates.largestArea(input));
    }

    @Test
    void largestSizeFromFile() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("out/test/resources/2018/day6.txt"))));
        Day6ChronalCoordinates day6ChronalCoordinates = new Day6ChronalCoordinates();
        assertEquals(3293, day6ChronalCoordinates.largestArea(input));
    }

    @Test
    void sizeOfRegion() {
        String input = "1, 1\n" +
                "1, 6\n" +
                "8, 3\n" +
                "3, 4\n" +
                "5, 5\n" +
                "8, 9";
        Day6ChronalCoordinates day6ChronalCoordinates = new Day6ChronalCoordinates();
        assertEquals(16, day6ChronalCoordinates.sizeOfRegion(input, 32));
    }

    @Test
    void sizeOfRegionFromFile() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("out/test/resources/2018/day6.txt"))));
        Day6ChronalCoordinates day6ChronalCoordinates = new Day6ChronalCoordinates();
        assertEquals(45176, day6ChronalCoordinates.sizeOfRegion(input, 10000));
    }
}