package aoc.aoc2019;

import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2019: Day 2: Program Alarm")
class Day2ProgramAlarmTest {

    @ParameterizedTest
    @CsvSource({"3500, '1,9,10,3,2,3,11,0,99,30,40,50'",
            "2, '1,0,0,0,99'",
            "2, '2,3,0,3,99'",
            "2, '2,4,4,5,99,0'",
            "30, '1,1,1,4,99,5,6,0,99'"})
    void valueAtProgramHalt(int finalValue, String opcodes) {
        assertThat(new Day2ProgramAlarm().valueAtProgramHalt(opcodes, null, null)).isEqualTo(finalValue);
    }

    @Test
    void problem1() throws IOException {
        var inputLines = AocFiles.readAllLines("day2.txt");
        for (String opcodes : inputLines) {
            assertThat(new Day2ProgramAlarm().valueAtProgramHalt(opcodes, 12, 2)).isEqualTo(5098658);
        }
    }

    @Test
    void problem2() throws IOException {
        var inputLines = AocFiles.readAllLines("day2.txt");
        for (String opcodes : inputLines) {
            assertThat(new Day2ProgramAlarm().nounAndVerb(opcodes)).isEqualTo(5064);
        }
    }
}