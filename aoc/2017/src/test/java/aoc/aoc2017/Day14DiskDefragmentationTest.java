package aoc.aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2017: Day 14: Disk Defragmentation")
class Day14DiskDefragmentationTest {

    @ParameterizedTest
    @CsvSource({"8108, flqrgnkx",
            "8226, wenycdww"})
    void problem1(int expected, String key) {
        assertThat(new Day14DiskDefragmentation(key).squaresUsed()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1242, flqrgnkx",
            "1128, wenycdww"})
    void problem2(int expected, String key) {
        assertThat(new Day14DiskDefragmentation(key).numberOfRegions()).isEqualTo(expected);
    }
}