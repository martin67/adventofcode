import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import aoc.common.HexPosition;

class HexPositionTest {

    @Test
    void distances() {
        HexPosition start = new HexPosition(0,0);
        Assertions.assertEquals(4, start.distance(new HexPosition(4,0)));
        Assertions.assertEquals(4, start.distance(new HexPosition(-4,0)));
        Assertions.assertEquals(6, start.distance(new HexPosition(4,2)));
        Assertions.assertEquals(3, start.distance(new HexPosition(2,1)));
        Assertions.assertEquals(3, start.distance(new HexPosition(-2,3)));
    }
}