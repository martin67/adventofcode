package aoc2019;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2ProgramAlarmTest {
    @ParameterizedTest
    @CsvSource({"3500, '1,9,10,3,2,3,11,0,99,30,40,50'",
            "2, '1,0,0,0,99'",
            "2, '2,3,0,3,99'",
            "2, '2,4,4,5,99,0'",
            "30, '1,1,1,4,99,5,6,0,99'"})
    void valueAtProgramHalt(int finalValue, String opcodes) {
        assertEquals(finalValue, new Day2ProgramAlarm().valueAtProgramHalt(opcodes));
    }
}