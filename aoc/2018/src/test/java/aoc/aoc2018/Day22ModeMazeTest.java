package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import aoc.common.Position;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 22: Mode Maze")
class Day22ModeMazeTest {

    @ParameterizedTest
    @CsvSource({
            "114, 510, 10, 10",
            "8681, 5616, 10, 785"
    })
    void problem1(int riskLevel, int depth, int x, int y) {
        Day22ModeMaze day22ModeMaze = new Day22ModeMaze(depth, new Position(x, y));
        assertThat(day22ModeMaze.computeRiskLevel()).isEqualTo(riskLevel);
    }

    @ParameterizedTest
    @CsvSource({
            "45, 510, 10, 10",
            "1070, 5616, 10, 785"
    })
    void problem2(int minutes, int depth, int x, int y) {
        Day22ModeMaze day22ModeMaze = new Day22ModeMaze(depth, new Position(x, y));
        assertThat(day22ModeMaze.fewestMinutes()).isEqualTo(minutes);
    }
}