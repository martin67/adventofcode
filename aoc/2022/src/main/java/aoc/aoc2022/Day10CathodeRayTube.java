package aoc.aoc2022;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Day10CathodeRayTube {

    final List<String> instructions;

     Day10CathodeRayTube(List<String> inputLines) {
        this.instructions = inputLines;
    }

    int problem1() {
        List<Integer> checkpoints = List.of(20, 60, 100, 140, 180, 220);
        int registerX = 1;
        int checksum = 0;
        int cycle = 1;
        for (String instruction : instructions) {
            log.debug("Instruction: {}, X: {}, cycle: {}", instruction, registerX, cycle);
            if (checkpoints.contains(cycle)) {
                checksum += registerX * cycle;
            }
            cycle++;
            if (!instruction.equals("noop")) {
                if (checkpoints.contains(cycle)) {
                    checksum += registerX * cycle;
                }
                cycle++;
                registerX += Integer.parseInt(instruction.split(" ")[1]);
            }
        }
        return checksum;
    }

    int problem2() {
        int registerX = 1;
        List<Integer> checkpoints = List.of(41, 81, 121, 161, 201, 241);
        StringBuilder sb = new StringBuilder();
        int checksum = 0;
        int cycle = 1;
        for (String instruction : instructions) {
            log.debug("Instruction: {}, X: {}, cycle: {}", instruction, registerX, cycle);
            if (checkpoints.contains(cycle)) {
                checksum += registerX * cycle;
                sb.append("\n");
            }
            sb.append(drawPixel(cycle, registerX));
            cycle++;
            if (!instruction.equals("noop")) {
                String[] s = instruction.split(" ");
                int val = Integer.parseInt(s[1]);

                if (checkpoints.contains(cycle)) {
                    checksum += registerX * cycle;
                    sb.append("\n");
                }
                sb.append(drawPixel(cycle, registerX));
                cycle++;
                registerX += val;
            }
        }
        System.out.println(sb);
        return checksum;
    }

    String drawPixel(int cycle, int registerX) {
        int crtPosition = (cycle + 39) % 40;
        if (crtPosition >= registerX - 1 && crtPosition <= registerX + 1) {
            return "#";
        } else {
            return ".";
        }
    }

}
