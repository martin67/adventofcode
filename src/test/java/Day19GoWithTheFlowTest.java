import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Day19GoWithTheFlowTest {

    @ParameterizedTest
    @CsvSource({
            "6, out/test/resources/day19-demo.txt",
            "1836, out/test/resources/day19.txt"
    })
    void getLeftInRegister(int outcome, String fileName) throws IOException {
        Day19GoWithTheFlow day19GoWithTheFlow = new Day19GoWithTheFlow(fileName);
        assertEquals(outcome, day19GoWithTheFlow.getLeftInRegister());
    }
}