package aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HexPositionTest {

    @Test
    void distances() {
        HexPosition start = new HexPosition(0,0);
        assertEquals(4, start.distance(new HexPosition(4,0)));
        assertEquals(4, start.distance(new HexPosition(-4,0)));
        assertEquals(6, start.distance(new HexPosition(4,2)));
        assertEquals(3, start.distance(new HexPosition(2,1)));
        assertEquals(3, start.distance(new HexPosition(-2,3)));
    }
}