import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Day21ChronalConversionTest {
    @Test
    void fewestInstructions() throws IOException {
        String fileName = "out/test/resources/day21.txt";
        Day21ChronalConversion day21ChronalConversion = new Day21ChronalConversion(fileName);
        assertEquals(13443200, day21ChronalConversion.fewestInstructions());
    }

    @Test
    void mostInstructions() throws IOException {
        String fileName = "out/test/resources/day21.txt";
        Day21ChronalConversion day21ChronalConversion = new Day21ChronalConversion(fileName);
        assertEquals(7717135, day21ChronalConversion.mostInstructions());
    }
}
