package aoc.aoc2017;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14DiskDefragmentationTest {

    @ParameterizedTest
    @CsvSource({"8108, flqrgnkx",
            "8226, wenycdww"})
    void squaresUsed(int expected, String key) {
        assertEquals(expected, new Day14DiskDefragmentation(key).squaresUsed());
    }

    @ParameterizedTest
    @CsvSource({"1242, flqrgnkx",
            "1128, wenycdww"})
    void numberOfRegions(int expected, String key) {
        assertEquals(expected, new Day14DiskDefragmentation(key).numberOfRegions());
    }

}