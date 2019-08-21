import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day20ARegularMapTest {

    @ParameterizedTest
    @CsvSource({
            "3, out/test/resources/day20-demo1.txt, day20-demo1.gml",
            "10, out/test/resources/day20-demo2.txt, day20-demo2.gml",
            "18, out/test/resources/day20-demo3.txt, day20-demo3.gml",
            "23, out/test/resources/day20-demo4.txt, day20-demo4.gml",
            "31, out/test/resources/day20-demo5.txt, day20-demo5.gml",
            "4214, out/test/resources/day20.txt, day20.gml"
    })
    void largestNumberOfDoors(int outcome, String fileName, String output) throws IOException {
        Day20ARegularMap day20ARegularMap = new Day20ARegularMap(fileName, output);
        assertEquals(outcome, day20ARegularMap.largestNumberOfDoors());
    }

    @ParameterizedTest
    @CsvSource({
            "3, out/test/resources/day20-demo1.txt, 1",
            "10, out/test/resources/day20-demo2.txt, 5",
            "18, out/test/resources/day20-demo3.txt, 5",
            "23, out/test/resources/day20-demo4.txt, 5",
            "31, out/test/resources/day20-demo5.txt, 5",
            "4214, out/test/resources/day20.txt, 1000"
    })
    void countRooms(int outcome, String fileName, int minNumberOfDoors) throws IOException {
        Day20ARegularMap day20ARegularMap = new Day20ARegularMap(fileName, null);
        assertEquals(10, day20ARegularMap.countRooms(minNumberOfDoors));
    }

}