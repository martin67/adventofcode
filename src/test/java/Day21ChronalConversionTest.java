import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Day21ChronalConversionTest {

    @Test
    void lowestInteger() throws IOException {
        Day21ChronalConversion day21ChronalConversion = new Day21ChronalConversion();
        assertEquals(10, day21ChronalConversion.lowestInteger());
    }
}