package adventofcode2016;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7InternetProtocolVersion7Test {

    @ParameterizedTest
    @CsvSource({"2, out/test/resources/2016/day7-demo1.txt",
            "115, out/test/resources/2016/day7.txt"})
    void supportTLS(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day7InternetProtocolVersion7(fileName).supportTLS());
    }

    @ParameterizedTest
    @CsvSource({"3, out/test/resources/2016/day7-demo2.txt",
            "231, out/test/resources/2016/day7.txt"})
    void supportSSL(int expected, String fileName) throws IOException {
        assertEquals(expected, new Day7InternetProtocolVersion7(fileName).supportSSL());
    }
}