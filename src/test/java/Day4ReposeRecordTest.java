import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day4ReposeRecordTest {

    @Test
    void computeChecksum() {
        String input = "[1518-11-01 00:00] Guard #10 begins shift\n" +
                "[1518-11-01 00:05] falls asleep\n" +
                "[1518-11-01 00:25] wakes up\n" +
                "[1518-11-01 00:30] falls asleep\n" +
                "[1518-11-01 00:55] wakes up\n" +
                "[1518-11-01 23:58] Guard #99 begins shift\n" +
                "[1518-11-02 00:40] falls asleep\n" +
                "[1518-11-02 00:50] wakes up\n" +
                "[1518-11-03 00:05] Guard #10 begins shift\n" +
                "[1518-11-03 00:24] falls asleep\n" +
                "[1518-11-03 00:29] wakes up\n" +
                "[1518-11-04 00:02] Guard #99 begins shift\n" +
                "[1518-11-04 00:36] falls asleep\n" +
                "[1518-11-04 00:46] wakes up\n" +
                "[1518-11-05 00:03] Guard #99 begins shift\n" +
                "[1518-11-05 00:45] falls asleep\n" +
                "[1518-11-05 00:55] wakes up";
        Day4ReposeRecord day4ReposeRecord = new Day4ReposeRecord();
        assertEquals(240, day4ReposeRecord.computeChecksum(input));
    }

    @Test
    void computeChecksumFromFile() throws IOException {
        String input = new String((Files.readAllBytes(Paths.get("build/resources/test/day4.txt"))));
        Day4ReposeRecord day4ReposeRecord = new Day4ReposeRecord();
        assertEquals(99911, day4ReposeRecord.computeChecksum(input));
    }
}
