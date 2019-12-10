package aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
@Data
class IntcodeComputer implements Runnable {

    private BlockingQueue<BigInteger> inputQueue;
    private BlockingQueue<BigInteger> outputQueue;
    private CountDownLatch countDownLatch;

    private BigInteger instructionPointer;
    private BigInteger relativeBase;
    private Map<BigInteger, BigInteger> opcodes;

    IntcodeComputer(List<String> program, int phaseSetting, CountDownLatch countDownLatch) {
        this.opcodes = new HashMap<>();
        for (int i = 0; i < program.size(); i++) {
            this.opcodes.put(new BigInteger(String.valueOf(i)), new BigInteger(program.get(i)));
        }
        this.instructionPointer = new BigInteger("0");
        this.relativeBase = new BigInteger("0");
        this.inputQueue = new LinkedBlockingDeque<>();
        if (phaseSetting != 0) {
            this.inputQueue.add(new BigInteger(String.valueOf(phaseSetting)));
        }
        this.outputQueue = new LinkedBlockingDeque<>();
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        log.info("Starting thread {}", Thread.currentThread().getName());
        try {
            boolean quit = false;

            while (!quit) {
                switch (getOpcode()) {
                    case "01":
                        log.info("{} {} {}: Adding {} + {} and storing in position {}",
                                Thread.currentThread().getName(), instructionPointer,
                                getOpcodeString(), getP1(), getP2(), opcodes.get(instructionPointer.add(new BigInteger("3"))));
                        opcodes.put(opcodes.get(instructionPointer.add(new BigInteger("3"))), getP1().add(getP2()));
                        instructionPointer = instructionPointer.add(new BigInteger("4"));
                        break;

                    case "02":
                        log.info("{} {} {}: Multiplying {} * {} and storing in position {}",
                                Thread.currentThread().getName(), instructionPointer,
                                getOpcodeString(), getP1(), getP2(), opcodes.get(instructionPointer.add(new BigInteger("3"))));
                        opcodes.put(opcodes.get(instructionPointer.add(new BigInteger("3"))), getP1().multiply(getP2()));
                        instructionPointer = instructionPointer.add(new BigInteger("4"));
                        break;

                    case "03":
                        BigInteger element = inputQueue.take();
                        log.info("{} {} {}: Input {} and storing in position {}",
                                Thread.currentThread().getName(), instructionPointer,
                                getOpcodeString(), element, opcodes.get(instructionPointer.add(new BigInteger("1"))));
                        opcodes.put(opcodes.get(instructionPointer.add(new BigInteger("1"))), element);
                        instructionPointer = instructionPointer.add(new BigInteger("2"));
                        break;

                    case "04":
                        log.info("{} {} {}: Output {} from position {}",
                                Thread.currentThread().getName(), instructionPointer,
                                getOpcodeString(), opcodes.get(opcodes.get(instructionPointer.add(new BigInteger("1")))),
                                instructionPointer.add(new BigInteger("1")));
                        //outputQueue.add(opcodes.get(opcodes.get(instructionPointer.add(new BigInteger("1")))));
                        outputQueue.add(getP1());
                        instructionPointer = instructionPointer.add(new BigInteger("2"));
                        break;

                    case "05":
                        log.info("{} {}: Jumping to {} if {} != 0", instructionPointer,
                                getOpcodeString(), getP2(), getP1());
                        if (!getP1().equals(new BigInteger("0"))) {
                            instructionPointer = getP2();
                        } else {
                            instructionPointer = instructionPointer.add(new BigInteger("3"));
                        }
                        break;

                    case "06":
                        log.info("{} {}: Jumping to {} if {} == 0", instructionPointer, getOpcodeString(), getP2(), getP1());
                        if (getP1().equals(new BigInteger("0"))) {
                            instructionPointer = getP2();
                        } else {
                            instructionPointer = instructionPointer.add(new BigInteger("3"));
                        }
                        break;

                    case "07":
                        //if (getP1() < getP2()) {
                        if (getP1().compareTo(getP2()) < 0) {
                            opcodes.put(opcodes.get(instructionPointer.add(new BigInteger("3"))),
                                    new BigInteger("1"));
                        } else {
                            opcodes.put(opcodes.get(instructionPointer.add(new BigInteger("3"))),
                                    new BigInteger("0"));
                        }
                        instructionPointer = instructionPointer.add(new BigInteger("4"));
                        break;

                    case "08":
                        if (getP1().equals(getP2())) {
                            opcodes.put(opcodes.get(instructionPointer.add(new BigInteger("3"))),
                                    new BigInteger("1"));
                        } else {
                            opcodes.put(opcodes.get(instructionPointer.add(new BigInteger("3"))),
                                    new BigInteger("0"));
                        }
                        instructionPointer = instructionPointer.add(new BigInteger("4"));
                        break;

                    case "09":
                        relativeBase = getP1();
                        instructionPointer = instructionPointer.add(new BigInteger("2"));
                        break;

                    case "99":
                        log.info("{} {}: Quitting", instructionPointer, getOpcodeString());
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

    private BigInteger getP1() {
        BigInteger result = null;
        switch (getOpcodeString().charAt(1)) {
            case '0':   // position mode
                result = opcodes.getOrDefault(opcodes.get(instructionPointer.add(new BigInteger("1"))),
                        new BigInteger("0"));
                break;

            case '1':   // immediate mode
                result = opcodes.get(instructionPointer.add(new BigInteger("1")));
                break;

            case '2':   // relative mode
                result = opcodes.getOrDefault(relativeBase.add(opcodes.get(instructionPointer.add(new BigInteger("1")))),
                        new BigInteger("0"));
                break;
        }
        return result;
    }

    private BigInteger getP2() {
        BigInteger result = null;
        switch (getOpcodeString().charAt(0)) {
            case '0':   // position mode
                result = opcodes.getOrDefault(opcodes.get(instructionPointer.add(new BigInteger("2"))),
                        new BigInteger("0"));
                break;

            case '1':   // immediate mode
                result = opcodes.get(instructionPointer.add(new BigInteger("2")));
                break;

            case '2':   // relative mode
                result = opcodes.getOrDefault(relativeBase.add(opcodes.get(instructionPointer.add(new BigInteger("2")))),
                        new BigInteger("0"));
                break;
        }
        return result;
    }

}
