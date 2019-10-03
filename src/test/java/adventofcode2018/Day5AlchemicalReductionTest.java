package adventofcode2018;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day5AlchemicalReductionTest {

    @Test
    void getPolymerLength() {
        String input = "dabAcCaCBAcCcaDA";
        Day5AlchemicalReduction day5AlchemicalReduction = new Day5AlchemicalReduction();
        assertEquals(10, day5AlchemicalReduction.getPolymerLength(input));
    }

    @Test
    void getPolymerLengthFromFile() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("out/test/resources/2018/day5.txt"))));
        Day5AlchemicalReduction day5AlchemicalReduction = new Day5AlchemicalReduction();
        assertEquals(9526, day5AlchemicalReduction.getPolymerLength(input));
    }

    @Test
    void findShortestPolymer() {
        String input = "dabAcCaCBAcCcaDA";
        Day5AlchemicalReduction day5AlchemicalReduction = new Day5AlchemicalReduction();
        assertEquals(4, day5AlchemicalReduction.findShortestPolymer(input));
    }

    @Test
    void findShortestPolymerFromFile() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("out/test/resources/2018/day5.txt"))));
        Day5AlchemicalReduction day5AlchemicalReduction = new Day5AlchemicalReduction();
        assertEquals(6694, day5AlchemicalReduction.findShortestPolymer(input));
    }

}