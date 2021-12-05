package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2015: Day 20: Infinite Elves and Infinite Houses")
class Day20InfiniteElvesAndInfiniteHousesTest {
    @ParameterizedTest
    @CsvSource({"1, 10",
            "2, 30",
            "3, 40",
            "4, 70",
            "5, 60",
            "6, 120",
            "7, 80",
            "8, 150",
            "9, 130",
            "12, 280",
            "29000000, 0"
    })
    void lowestHouseNumber(Long input, Long expected) {
        assertEquals(expected, new Day20InfiniteElvesAndInfiniteHouses().lowestHouseNumber(input));
    }

    @ParameterizedTest
    @CsvSource({"1, 10",
            "2, 30",
            "3, 40",
            "4, 70",
            "5, 60",
            "6, 120",
            "7, 80",
            "8, 150",
            "9, 130",
            "12, 280",
            "29000000, 0"
    })
    void lowestHouseNumber2(Long input, Long expected) {
        assertEquals(expected, new Day20InfiniteElvesAndInfiniteHouses().lowestHouseNumber2(input));
    }

}