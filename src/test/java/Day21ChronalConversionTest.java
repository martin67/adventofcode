import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Day21ChronalConversionTest {
    @Test
    void totalResourceValue() throws IOException {
        String fileName = "out/test/resources/day21.txt";
        Day21ChronalConversion day21ChronalConversion = new Day21ChronalConversion(fileName);
        assertEquals(195290, day21ChronalConversion.lowestInteger());
    }
}