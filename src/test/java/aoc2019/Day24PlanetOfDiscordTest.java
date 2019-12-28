package aoc2019;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class Day24PlanetOfDiscordTest {

    @ParameterizedTest
    @CsvSource({"2129920, src/test/resources/2019/day24-demo1.txt",
            "28778811, src/test/resources/2019/day24.txt"})
    void biodiversityRating(int expected, String fileName) throws IOException {
        List<String> inputLines = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, new Day24PlanetOfDiscord(inputLines).biodiversityRating());
    }
}