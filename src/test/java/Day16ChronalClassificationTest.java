import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day16ChronalClassificationTest {

    @Test
    void ThreeOrMoreOpCodesTest() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("out/test/resources/day16.txt"))));
        Day16ChronalClassification day16ChronalClassification = new Day16ChronalClassification(input);
        assertEquals(493, day16ChronalClassification.threeOrMoreOpCodes());
    }

    @Test
    void RemainingRegisterTest() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("out/test/resources/day16.txt"))));
        Day16ChronalClassification day16ChronalClassification = new Day16ChronalClassification(input);
        assertEquals(445, day16ChronalClassification.remainingRegister());
    }

}