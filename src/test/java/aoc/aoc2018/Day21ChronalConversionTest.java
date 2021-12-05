package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("2018: Day 21: Chronal Conversion")
class Day21ChronalConversionTest {

    @Test
    void problem1() throws IOException {
        String fileName = "src/test/resources/2018/day21.txt";
        Day21ChronalConversion day21ChronalConversion = new Day21ChronalConversion(fileName);
        assertEquals(13443200, day21ChronalConversion.fewestInstructions());
    }

    @Test
    void problem2() throws IOException {
        String fileName = "src/test/resources/2018/day21.txt";
        Day21ChronalConversion day21ChronalConversion = new Day21ChronalConversion(fileName);
        assertEquals(7717135, day21ChronalConversion.mostInstructions());
    }

}
