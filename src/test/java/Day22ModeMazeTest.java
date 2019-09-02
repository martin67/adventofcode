import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Day22ModeMazeTest {

    @ParameterizedTest
    @CsvSource({
            "114, 510, 10, 10",
            "8681, 5616, 10, 785"
    })
    void riskLevel(int riskLevel, int depth, int x, int y) throws IOException {
        Day22ModeMaze day22ModeMaze = new Day22ModeMaze(depth, new Position(x, y));
        assertEquals(riskLevel, day22ModeMaze.computeRiskLevel());
    }
}