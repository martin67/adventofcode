package aoc2019;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
class IntcodeComputer {

    private int instructionPointer;
    private List<Integer> opcodes;

    List<Integer> run(List<Integer> program, List<Integer> input) {
        opcodes = new ArrayList<>(program);
        Queue<Integer> inputQueue = new LinkedList<>(input);
        List<Integer> output = new ArrayList<>();
        boolean quit = false;
        instructionPointer = 0;

        while (!quit) {
            switch (getOpcode()) {
                case "01":
                    log.debug("{} {}: Adding {} + {} and storing in position {}", instructionPointer,
                            getOpcodeString(), getP1(), getP2(), opcodes.get(instructionPointer + 3));
                    opcodes.set(opcodes.get(instructionPointer + 3), getP1() + getP2());
                    instructionPointer += 4;
                    break;

                case "02":
                    log.debug("{} {}: Multiplying {} * {} and storing in position {}", instructionPointer,
                            getOpcodeString(), getP1(), getP2(), opcodes.get(instructionPointer + 3));
                    opcodes.set(opcodes.get(instructionPointer + 3), getP1() * getP2());
                    instructionPointer += 4;
                    break;

                case "03":
                    log.debug("{} {}: Input {} and storing in position {}", instructionPointer,
                            getOpcodeString(), input, opcodes.get(instructionPointer + 1));
                    opcodes.set(opcodes.get(instructionPointer + 1), inputQueue.poll());
                    instructionPointer += 2;
                    break;

                case "04":
                    log.debug("{} {}: Output {} from position {}", instructionPointer,
                            getOpcodeString(), opcodes.get(opcodes.get(instructionPointer + 1)), instructionPointer + 1);
                    output.add(opcodes.get(opcodes.get(instructionPointer + 1)));
                    instructionPointer += 2;
                    break;

                case "05":
                    log.debug("{} {}: Jumping to {} if {} != 0", instructionPointer,
                            getOpcodeString(), getP2(), getP1());
                    if (getP1() != 0) {
                        instructionPointer = getP2();
                    } else {
                        instructionPointer += 3;
                    }
                    break;

                case "06":
                    log.debug("{} {}: Jumping to {} if {} == 0", instructionPointer, getOpcodeString(), getP2(), getP1());
                    if (getP1() == 0) {
                        instructionPointer = getP2();
                    } else {
                        instructionPointer += 3;
                    }
                    break;

                case "07":
                    if (getP1() < getP2()) {
                        opcodes.set(opcodes.get(instructionPointer + 3), 1);
                    } else {
                        opcodes.set(opcodes.get(instructionPointer + 3), 0);
                    }
                    instructionPointer += 4;
                    break;

                case "08":
                    if (getP1() == getP2()) {
                        opcodes.set(opcodes.get(instructionPointer + 3), 1);
                    } else {
                        opcodes.set(opcodes.get(instructionPointer + 3), 0);
                    }
                    instructionPointer += 4;
                    break;

                case "99":
                    log.debug("{} {}: Quitting", instructionPointer, getOpcodeString());
                    quit = true;
                    break;

                default:
                    log.error("oops");
            }
        }
        return output;
    }

    private String getOpcodeString() {
        return StringUtils.leftPad(opcodes.get(instructionPointer).toString(), 4, '0');
    }

    private String getOpcode() {
        return getOpcodeString().substring(2, 4);
    }

    private int getP1() {
        return getOpcodeString().charAt(1) == '1' ? opcodes.get(instructionPointer + 1) : opcodes.get(opcodes.get(instructionPointer + 1));
    }

    private int getP2() {
        return getOpcodeString().charAt(0) == '1' ? opcodes.get(instructionPointer + 2) : opcodes.get(opcodes.get(instructionPointer + 2));
    }
}
