package aoc.aoc2018;

import org.junit.jupiter.api.Test;

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
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/2018/day3.txt"))));
        Day3NoMatterHowYouSliceIt day3NoMatterHowYouSliceIt = new Day3NoMatterHowYouSliceIt();
        assertEquals(111630, day3NoMatterHowYouSliceIt.countSlices(input));
    }

    @Test
    void getSingleClaim() {
        String input = "#1 @ 1,3: 4x4\n#2 @ 3,1: 4x4\n#3 @ 5,5: 2x2";
        Day3NoMatterHowYouSliceIt day3NoMatterHowYouSliceIt = new Day3NoMatterHowYouSliceIt();
        assertEquals(3, day3NoMatterHowYouSliceIt.getSingleClaim(input));
    }

    @Test
    void getSingleClaimFromFile() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/2018/day3.txt"))));
        Day3NoMatterHowYouSliceIt day3NoMatterHowYouSliceIt = new Day3NoMatterHowYouSliceIt();
        assertEquals(724, day3NoMatterHowYouSliceIt.getSingleClaim(input));
    }

}