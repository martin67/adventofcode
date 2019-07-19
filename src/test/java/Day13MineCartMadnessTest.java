import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day13MineCartMadnessTest {

    @Test
    void ReadDemoData() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("out/test/resources/day13-0.txt"))));
        Day13MineCartMadness day13MineCartMadness = new Day13MineCartMadness(input);
        assertEquals(new Position(7,3), day13MineCartMadness.getFirstCollision());
    }

    @Test
    void ReadFirstData() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("out/test/resources/day13-1.txt"))));
        Day13MineCartMadness day13MineCartMadness = new Day13MineCartMadness(input);
        assertEquals(new Position(86,118), day13MineCartMadness.getFirstCollision());
    }

    @Test
    void LastCartDemo() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("out/test/resources/day13-2.txt"))));
        Day13MineCartMadness day13MineCartMadness = new Day13MineCartMadness(input);
        assertEquals(new Position(6,4), day13MineCartMadness.lastCart());
    }

    @Test
    void LastCart() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("out/test/resources/day13-1.txt"))));
        Day13MineCartMadness day13MineCartMadness = new Day13MineCartMadness(input);
        assertEquals(new Position(2,81), day13MineCartMadness.lastCart());
    }

}