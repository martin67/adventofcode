package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2018: Day 12: Subterranean Sustainability")
class Day12SubterraneanSustainabilityTest {

    @Test
    void ReadDemoData() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/2018/day12-1.txt"))));
        Day12SubterraneanSustainability day12SubterraneanSustainability = new Day12SubterraneanSustainability(input, 20);
        assertEquals(325, day12SubterraneanSustainability.ComputePlantSum());
    }

    @Test
    void ReadData() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/2018/day12-2.txt"))));
        Day12SubterraneanSustainability day12SubterraneanSustainability = new Day12SubterraneanSustainability(input, 205);
        day12SubterraneanSustainability.ComputePlantSum();
        assertEquals(3061, day12SubterraneanSustainability.ComputePlantSum());
    }

    @Test
    void problem2() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/2018/day12-2.txt"))));
        Day12SubterraneanSustainability day12SubterraneanSustainability = new Day12SubterraneanSustainability(input, 205);
        //assertEquals(3061, day12SubterraneanSustainability.ComputeBigPlantSum(new BigInteger("50000000000")));
        assertEquals("4049999998575", day12SubterraneanSustainability.ComputeBigPlantSum(new BigInteger("50000000000")));
    }

}