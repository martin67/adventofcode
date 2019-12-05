package aoc2019;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day5SunnyWithAChanceOfAsteroids {

    int instructionPointer;
    List<Integer> opcodes;

    int diagnosticCode(String opcodeStrings, int input) {
        opcodes = Stream.of(opcodeStrings.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        boolean quit = false;
        instructionPointer = 0;
        int lastCode = 0;

        while (!quit) {
            switch (getOpcode()) {
                case "01":
                    log.info("{} {}: Adding {} + {} and storing in position {}", instructionPointer, getOpcodeString(), getP1(), getP2(), opcodes.get(instructionPointer + 3));
                    opcodes.set(opcodes.get(instructionPointer + 3), getP1() + getP2());
                    instructionPointer += 4;
                    break;

                case "02":
                    log.info("{} {}: Multiplying {} * {} and storing in position {}", instructionPointer, getOpcodeString(), getP1(), getP2(), opcodes.get(instructionPointer + 3));
                    opcodes.set(opcodes.get(instructionPointer + 3), getP1() * getP2());
                    instructionPointer += 4;
                    break;

                case "03":
                    log.info("{} {}: Reading {} and storing in position {}", instructionPointer, getOpcodeString(), input, opcodes.get(instructionPointer + 1));
                    opcodes.set(opcodes.get(instructionPointer + 1), input);
                    instructionPointer += 2;
                    break;

                case "04":
                    log.info("{} {}: Printing {} from position {}", instructionPointer, getOpcodeString(), opcodes.get(opcodes.get(instructionPointer + 1)), instructionPointer + 1);
                    System.out.printf("Output: %d\n", opcodes.get(opcodes.get(instructionPointer + 1)));
                    lastCode = opcodes.get(opcodes.get(instructionPointer + 1));
                    instructionPointer += 2;
                    break;

                case "05":
                    log.info("{} {}: Jumping to {} if {} != 0", instructionPointer, getOpcodeString(), getP2(), getP1());
                    if (getP1() != 0) {
                        instructionPointer = getP2();
                    } else {
                        instructionPointer += 3;
                    }
                    break;

                case "06":
                    log.info("{} {}: Jumping to {} if {} == 0", instructionPointer, getOpcodeString(), getP2(), getP1());
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
                    quit = true;
                    break;

                default:
                    log.error("oops");
            }
        }
        return lastCode;
    }


    String getOpcodeString() {
        return StringUtils.leftPad(opcodes.get(instructionPointer).toString(), 4, '0');
    }

    String getOpcode() {
        return getOpcodeString().substring(2, 4);
    }

    int getP1() {
        String opcodeString = getOpcodeString();
        return opcodeString.charAt(1) == '1' ? opcodes.get(instructionPointer + 1) : opcodes.get(opcodes.get(instructionPointer + 1));
    }

    int getP2() {
        String opcodeString = getOpcodeString();
        return opcodeString.charAt(0) == '2' ? opcodes.get(instructionPointer + 2) : opcodes.get(opcodes.get(instructionPointer + 2));
    }

}
