import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day14ChocolateChartsTest {
    @ParameterizedTest
    @CsvSource({
            "9, 5158916779",
            "5, 0124515891",
            "18, 9251071085",
            "2018, 5941429882",
            "147061, 2145581131"
    })
    void ComputeScore(int numberOfRecipes, String score) throws IOException {
        Day14ChocolateCharts day14ChocolateCharts = new Day14ChocolateCharts();
        assertEquals(score, day14ChocolateCharts.computeScore(numberOfRecipes));
    }

    @ParameterizedTest
    @CsvSource({
            "51589, 9",
            "01245, 5",
            "92510, 18",
            "59414, 2018",
            "147061, 20283721"
    })
    void ComputeRecipes(String score, int numberOfRecipes) throws IOException {
        Day14ChocolateCharts day14ChocolateCharts = new Day14ChocolateCharts();
        assertEquals(numberOfRecipes, day14ChocolateCharts.computeRecipes(score));
    }
}