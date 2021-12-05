package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2018: Day 20: A Regular Map")
class Day20ARegularMapTest {

    @ParameterizedTest
    @CsvSource({
            "3, src/test/resources/2018/day20-demo1.txt, day20-demo1.gml",
            "10, src/test/resources/2018/day20-demo2.txt, day20-demo2.gml",
            "18, src/test/resources/2018/day20-demo3.txt, day20-demo3.gml",
            "23, src/test/resources/2018/day20-demo4.txt, day20-demo4.gml",
            "31, src/test/resources/2018/day20-demo5.txt, day20-demo5.gml",
            "4214, src/test/resources/2018/day20.txt, day20.gml"
    })
    void problem1(int outcome, String fileName, String output) throws IOException {
        Day20ARegularMap day20ARegularMap = new Day20ARegularMap(fileName, output, true);
        assertEquals(outcome, day20ARegularMap.largestNumberOfDoors());
    }

    @ParameterizedTest
    @CsvSource({
            "2, src/test/resources/2018/day20-demo1.txt, day20-demo1.gml, 2",
            "11, src/test/resources/2018/day20-demo2.txt, day20-demo2.gml, 5",
            "20, src/test/resources/2018/day20-demo3.txt, day20-demo3.gml, 5",
            "31, src/test/resources/2018/day20-demo4.txt, day20-demo4.gml, 5",
            "44, src/test/resources/2018/day20-demo5.txt, day20-demo5.gml, 5",
            "8492, src/test/resources/2018/day20.txt, day20.gml, 1000"
    })
    void problem2(int outcome, String fileName, String output, int minNumberOfDoors) throws IOException {
        Day20ARegularMap day20ARegularMap = new Day20ARegularMap(fileName, output, false);
        assertEquals(outcome, day20ARegularMap.countRooms(minNumberOfDoors));
    }

}