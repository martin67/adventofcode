import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day7TheSumofItsPartsTest {

    @Test
    void puzzleOrder() {
            String input = "Step C must be finished before step A can begin.\n" +
                "Step C must be finished before step F can begin.\n" +
                "Step A must be finished before step B can begin.\n" +
                "Step A must be finished before step D can begin.\n" +
                "Step B must be finished before step E can begin.\n" +
                "Step D must be finished before step E can begin.\n" +
                "Step F must be finished before step E can begin.\n";
            Day7TheSumofItsParts day7TheSumofItsParts = new Day7TheSumofItsParts();
            assertEquals("CABDFE", day7TheSumofItsParts.puzzleOrder(input));
    }

    @Test
    void puzzleOrderFromFile() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("build/resources/test/day7.txt"))));
        Day7TheSumofItsParts day7TheSumofItsParts = new Day7TheSumofItsParts();
        assertEquals("ABGKCMVWYDEHFOPQUILSTNZRJX", day7TheSumofItsParts.puzzleOrder(input));
    }

    @Test
    void computeTime() {
        String input = "Step C must be finished before step A can begin.\n" +
                "Step C must be finished before step F can begin.\n" +
                "Step A must be finished before step B can begin.\n" +
                "Step A must be finished before step D can begin.\n" +
                "Step B must be finished before step E can begin.\n" +
                "Step D must be finished before step E can begin.\n" +
                "Step F must be finished before step E can begin.\n";
        Day7TheSumofItsParts day7TheSumofItsParts = new Day7TheSumofItsParts();
        assertEquals(15, day7TheSumofItsParts.computeTime(input, 2, 0));
    }

    @Test
    void computeTimeFromFile() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("build/resources/test/day7.txt"))));
        Day7TheSumofItsParts day7TheSumofItsParts = new Day7TheSumofItsParts();
        assertEquals(898, day7TheSumofItsParts.computeTime(input, 5, 60));
    }
}