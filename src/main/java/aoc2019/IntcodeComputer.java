package aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
@Data
class IntcodeComputer implements Runnable {

    private BlockingQueue<Integer> inputQueue;
    private BlockingQueue<Integer> outputQueue;
    private CountDownLatch countDownLatch;

    private int instructionPointer;
    private List<Integer> opcodes;

    IntcodeComputer(List<Integer> program, int phaseSetting, CountDownLatch countDownLatch) {
        this.opcodes = new ArrayList<>(program);
        this.instructionPointer = 0;
        this.inputQueue = new LinkedBlockingDeque<>();
        this.inputQueue.add(phaseSetting);
        this.outputQueue = new LinkedBlockingDeque<>();
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        log.debug("Starting thread {}", Thread.currentThread().getName());
        try {
            boolean quit = false;

            while (!quit) {
                switch (getOpcode()) {
                    case "01":
                        log.debug("{} {} {}: Adding {} + {} and storing in position {}",
                                Thread.currentThread().getName(), instructionPointer,
                                getOpcodeString(), getP1(), getP2(), opcodes.get(instructionPointer + 3));
                        opcodes.set(opcodes.get(instructionPointer + 3), getP1() + getP2());
                        instructionPointer += 4;
                        break;

                    case "02":
                        log.debug("{} {} {}: Multiplying {} * {} and storing in position {}",
                                Thread.currentThread().getName(), instructionPointer,
                                getOpcodeString(), getP1(), getP2(), opcodes.get(instructionPointer + 3));
                        opcodes.set(opcodes.get(instructionPointer + 3), getP1() * getP2());
                        instructionPointer += 4;
                        break;

                    case "03":
                        int element = inputQueue.take();
                        log.debug("{} {} {}: Input {} and storing in position {}",
                                Thread.currentThread().getName(), instructionPointer,
                                getOpcodeString(), element, opcodes.get(instructionPointer + 1));
                        opcodes.set(opcodes.get(instructionPointer + 1), element);
                        instructionPointer += 2;
                        break;

                    case "04":
                        log.debug("{} {} {}: Output {} from position {}",
                                Thread.currentThread().getName(), instructionPointer,
                                getOpcodeString(), opcodes.get(opcodes.get(instructionPointer + 1)), instructionPointer + 1);
                        outputQueue.add(opcodes.get(opcodes.get(instructionPointer + 1)));
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
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
