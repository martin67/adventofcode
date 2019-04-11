import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day3NoMatterHowYouSliceItTest {

    @Test
    void computeSlices() {
        String input = "#1 @ 1,3: 4x4\n#2 @ 3,1: 4x4\n#3 @ 5,5: 2x2";
        Day3NoMatterHowYouSliceIt day3NoMatterHowYouSliceIt = new Day3NoMatterHowYouSliceIt();
        assertEquals(4, day3NoMatterHowYouSliceIt.countSlices(input));
    }

    @Test
    void computeSlicesFromFile() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("build/resources/test/day3.txt"))));
        Day3NoMatterHowYouSliceIt day3NoMatterHowYouSliceIt = new Day3NoMatterHowYouSliceIt();
        assertEquals(111630, day3NoMatterHowYouSliceIt.countSlices(input));
    }
}