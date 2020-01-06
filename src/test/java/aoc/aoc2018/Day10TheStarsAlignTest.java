package aoc.aoc2018;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day10TheStarsAlignTest {

    @Test
    void getMessage() {
        String input = "position=< 9,  1> velocity=< 0,  2>\n" +
                "position=< 7,  0> velocity=<-1,  0>\n" +
                "position=< 3, -2> velocity=<-1,  1>\n" +
                "position=< 6, 10> velocity=<-2, -1>\n" +
                "position=< 2, -4> velocity=< 2,  2>\n" +
                "position=<-6, 10> velocity=< 2, -2>\n" +
                "position=< 1,  8> velocity=< 1, -1>\n" +
                "position=< 1,  7> velocity=< 1,  0>\n" +
                "position=<-3, 11> velocity=< 1, -2>\n" +
                "position=< 7,  6> velocity=<-1, -1>\n" +
                "position=<-2,  3> velocity=< 1,  0>\n" +
                "position=<-4,  3> velocity=< 2,  0>\n" +
                "position=<10, -3> velocity=<-1,  1>\n" +
                "position=< 5, 11> velocity=< 1, -2>\n" +
                "position=< 4,  7> velocity=< 0, -1>\n" +
                "position=< 8, -2> velocity=< 0,  1>\n" +
                "position=<15,  0> velocity=<-2,  0>\n" +
                "position=< 1,  6> velocity=< 1,  0>\n" +
                "position=< 8,  9> velocity=< 0, -1>\n" +
                "position=< 3,  3> velocity=<-1,  1>\n" +
                "position=< 0,  5> velocity=< 0, -1>\n" +
                "position=<-2,  2> velocity=< 2,  0>\n" +
                "position=< 5, -2> velocity=< 1,  2>\n" +
                "position=< 1,  4> velocity=< 2,  1>\n" +
                "position=<-2,  7> velocity=< 2, -2>\n" +
                "position=< 3,  6> velocity=<-1, -1>\n" +
                "position=< 5,  0> velocity=< 1,  0>\n" +
                "position=<-6,  0> velocity=< 2,  0>\n" +
                "position=< 5,  9> velocity=< 1, -2>\n" +
                "position=<14,  7> velocity=<-2,  0>\n" +
                "position=<-3,  6> velocity=< 2, -1>";
        Day10TheStarsAlign day10TheStarsAlign = new Day10TheStarsAlign();
        Day10TheStarsAlign.Result result = day10TheStarsAlign.getMessage(input);
        //assertEquals("HI", result.getMessage());
        assertEquals(3, result.getTime());
    }


    @Test
    void getMessageFromFile() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("src/test/resources/2018/day10.txt"))));
        Day10TheStarsAlign day10TheStarsAlign = new Day10TheStarsAlign();
        Day10TheStarsAlign.Result result = day10TheStarsAlign.getMessage(input);
        //assertEquals("RECLRNZE", result.getMessage());
        assertEquals(10007, result.getTime());
    }
}