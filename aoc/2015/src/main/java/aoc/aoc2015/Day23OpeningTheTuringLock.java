package aoc.aoc2015;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day23OpeningTheTuringLock {
    final List<Instruction> instructions = new ArrayList<>();
    int a;
    int b;
    public Day23OpeningTheTuringLock(List<String> inputLines) {
        var pattern = Pattern.compile("(\\w+) (\\+)(, (\\+))");
        for (String line : inputLines) {
            var matcher = pattern.matcher(line);
        }
    }

    public int problem1() {
        return 0;
    }

    public int problem2() {
        return 0;
    }

    class Instruction {
        String name;
        String register;
        int offset;

    }
}
