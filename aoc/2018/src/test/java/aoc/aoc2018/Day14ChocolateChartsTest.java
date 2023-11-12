package aoc.aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2018: Day 14: Chocolate Charts")
class Day14ChocolateChartsTest {

    @ParameterizedTest
    @CsvSource({
            "9, 5158916779",
            "5, 0124515891",
            "18, 9251071085",
            "2018, 5941429882",
            "147061, 2145581131"
    })
    void problem1(int numberOfRecipes, String score) {
        Day14ChocolateCharts day14ChocolateCharts = new Day14ChocolateCharts();
        assertThat(day14ChocolateCharts.computeScore(numberOfRecipes)).isEqualTo(score);
    }

    @ParameterizedTest
    @CsvSource({
            "51589, 9",
            "01245, 5",
            "92510, 18",
            "59414, 2018",
            "147061, 20283721"
    })
    void problem2(String score, int numberOfRecipes) {
        Day14ChocolateCharts day14ChocolateCharts = new Day14ChocolateCharts();
        assertThat(day14ChocolateCharts.computeRecipes(score)).isEqualTo(numberOfRecipes);
    }
}