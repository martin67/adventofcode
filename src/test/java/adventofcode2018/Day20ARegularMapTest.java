package adventofcode2018;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day20ARegularMapTest {

    @ParameterizedTest
    @CsvSource({
            "3, out/test/resources/2018/day20-demo1.txt, day20-demo1.gml",
            "10, out/test/resources/2018/day20-demo2.txt, day20-demo2.gml",
            "18, out/test/resources/2018/day20-demo3.txt, day20-demo3.gml",
            "23, out/test/resources/2018/day20-demo4.txt, day20-demo4.gml",
            "31, out/test/resources/2018/day20-demo5.txt, day20-demo5.gml",
            "4214, out/test/resources/2018/day20.txt, day20.gml"
    })
    void largestNumberOfDoors(int outcome, String fileName, String output) throws IOException {
        Day20ARegularMap day20ARegularMap = new Day20ARegularMap(fileName, output, true);
        assertEquals(outcome, day20ARegularMap.largestNumberOfDoors());
    }

    @ParameterizedTest
    @CsvSource({
            "2, out/test/resources/2018/day20-demo1.txt, day20-demo1.gml, 2",
            "11, out/test/resources/2018/day20-demo2.txt, day20-demo2.gml, 5",
            "20, out/test/resources/2018/day20-demo3.txt, day20-demo3.gml, 5",
            "31, out/test/resources/2018/day20-demo4.txt, day20-demo4.gml, 5",
            "44, out/test/resources/2018/day20-demo5.txt, day20-demo5.gml, 5",
            "8492, out/test/resources/2018/day20.txt, day20.gml, 1000"
    })
    void countRooms(int outcome, String fileName, String output, int minNumberOfDoors) throws IOException {
        Day20ARegularMap day20ARegularMap = new Day20ARegularMap(fileName, output, false);
        assertEquals(outcome, day20ARegularMap.countRooms(minNumberOfDoors));
    }

}