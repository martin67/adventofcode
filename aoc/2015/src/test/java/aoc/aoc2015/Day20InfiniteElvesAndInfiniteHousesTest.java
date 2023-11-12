package aoc.aoc2015;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

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
    void problem1(Long input, Long expected) {
        assertThat(new Day20InfiniteElvesAndInfiniteHouses().lowestHouseNumber(input))
                .isEqualTo(expected);
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
    void problem2(Long input, Long expected) {
        assertThat(new Day20InfiniteElvesAndInfiniteHouses().lowestHouseNumber2(input))
                .isEqualTo(expected);
    }
}