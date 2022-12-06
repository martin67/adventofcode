package aoc.aoc2019;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
public class IntcodeComputer implements Callable<Integer> {

    private final BlockingQueue<BigInteger> inputQueue;
    private final Map<BigInteger, BigInteger> opcodes;
    private BlockingQueue<BigInteger> outputQueue;
    private BigInteger instructionPointer;
    private BigInteger relativeBaseOffset;

    public IntcodeComputer(List<String> program) {
        this.opcodes = new HashMap<>();
        for (int i = 0; i < program.size(); i++) {
            this.opcodes.put(new BigInteger(String.valueOf(i)), new BigInteger(program.get(i)));
        }
        this.instructionPointer = new BigInteger("0");
        this.relativeBaseOffset = new BigInteger("0");
        this.inputQueue = new LinkedBlockingDeque<>();
        this.outputQueue = new LinkedBlockingDeque<>();
    }

    @Override
    public Integer call() {
        log.debug("Starting thread {}", Thread.currentThread().getName());
        try {
            boolean quit = false;

            while (!quit) {
                switch (getOpcode()) {
                    case "01" -> {
                        log.debug("{} {} {}: Adding {} + {} and storing in position {}",
                                Thread.currentThread().getName(), instructionPointer,
                                getOpcodeString(), getP1(), getP2(), getP3());
                        opcodes.put(getP3(), getP1().add(getP2()));
                        instructionPointer = instructionPointer.add(new BigInteger("4"));
                    }
                    case "02" -> {
                        log.debug("{} {} {}: Multiplying {} * {} and storing in position {}",
                                Thread.currentThread().getName(), instructionPointer,
                                getOpcodeString(), getP1(), getP2(), getP3());
                        opcodes.put(getP3(), getP1().multiply(getP2()));
                        instructionPointer = instructionPointer.add(new BigInteger("4"));
                    }
                    case "03" -> {
                        BigInteger element = inputQueue.take();
                        log.debug("{} {} {}: Input {} and storing in position {}",
                                Thread.currentThread().getName(), instructionPointer,
                                getOpcodeString(), element, getP1forPut());
                        opcodes.put(getP1forPut(), element);
                        instructionPointer = instructionPointer.add(new BigInteger("2"));
                    }
                    case "04" -> {
                        log.debug("{} {} {}: Add {} to output queue",
                                Thread.currentThread().getName(), instructionPointer,
                                getOpcodeString(), getP1());
                        outputQueue.add(getP1());
                        instructionPointer = instructionPointer.add(new BigInteger("2"));
                    }
                    case "05" -> {
                        log.debug("{} {}: Jumping to {} if {} != 0", instructionPointer,
                                getOpcodeString(), getP2(), getP1());
                        if (!getP1().equals(new BigInteger("0"))) {
                            instructionPointer = getP2();
                        } else {
                            instructionPointer = instructionPointer.add(new BigInteger("3"));
                        }
                    }
                    case "06" -> {
                        log.debug("{} {}: Jumping to {} if {} == 0", instructionPointer, getOpcodeString(), getP2(), getP1());
                        if (getP1().equals(new BigInteger("0"))) {
                            instructionPointer = getP2();
                        } else {
                            instructionPointer = instructionPointer.add(new BigInteger("3"));
                        }
                    }
                    case "07" -> {
                        if (getP1().compareTo(getP2()) < 0) {
                            opcodes.put(getP3(), new BigInteger("1"));
                        } else {
                            opcodes.put(getP3(), new BigInteger("0"));
                        }
                        instructionPointer = instructionPointer.add(new BigInteger("4"));
                    }
                    case "08" -> {
                        log.debug("{} {}: If {} == {} => {}=1  ", instructionPointer,
                                getOpcodeString(), getP1(), getP2(), getP3());
                        if (getP1().equals(getP2())) {
                            opcodes.put(getP3(), new BigInteger("1"));
                        } else {
                            opcodes.put(getP3(), new BigInteger("0"));
                        }
                        instructionPointer = instructionPointer.add(new BigInteger("4"));
                    }
                    case "09" -> {
                        log.debug("{} {}: Adjusting relative base with {}", instructionPointer,
                                getOpcodeString(), getP1());
                        relativeBaseOffset = relativeBaseOffset.add(getP1());
                        instructionPointer = instructionPointer.add(new BigInteger("2"));
                    }
                    case "99" -> {
                        log.debug("{} {}: Quitting", instructionPointer, getOpcodeString());
                        quit = true;
                    }
                    default -> log.error("oops");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("Ending thread {}", Thread.currentThread().getName());
        return 0;
    }

    private String getOpcodeString() {
        return StringUtils.leftPad(opcodes.get(instructionPointer).toString(), 5, '0');
    }

    private String getOpcode() {
        return getOpcodeString().substring(3, 5);
    }

    private BigInteger getP1() {
        BigInteger output = null;
        switch (getOpcodeString().charAt(2)) {
            case '0' ->   // position mode
                    output = opcodes.getOrDefault(opcodes.get(instructionPointer.add(new BigInteger("1"))),
                            new BigInteger("0"));
            case '1' ->   // immediate mode
                    output = opcodes.get(instructionPointer.add(new BigInteger("1")));
            case '2' ->   // relative mode
                    output = opcodes.getOrDefault(relativeBaseOffset.add(opcodes.get(instructionPointer.add(new BigInteger("1")))),
                            new BigInteger("0"));
            default -> log.error("oops");
        }
        return output;
    }

    private BigInteger getP1forPut() {
        BigInteger output = null;
        switch (getOpcodeString().charAt(2)) {
            case '0' ->   // position mode
                    output = opcodes.get(instructionPointer.add(new BigInteger("1")));
            case '1' ->   // immediate mode
                    log.error("Should not be in immediate mode!");
            case '2' ->   // relative mode
                    output = relativeBaseOffset.add(opcodes.get(instructionPointer.add(new BigInteger("1"))));
            default -> log.error("oops");
        }
        return output;
    }

    private BigInteger getP2() {
        BigInteger output = null;
        switch (getOpcodeString().charAt(1)) {
            case '0' ->   // position mode
                    output = opcodes.getOrDefault(opcodes.get(instructionPointer.add(new BigInteger("2"))),
                            new BigInteger("0"));
            case '1' ->   // immediate mode
                    output = opcodes.get(instructionPointer.add(new BigInteger("2")));
            case '2' ->   // relative mode
                    output = opcodes.getOrDefault(relativeBaseOffset.add(opcodes.get(instructionPointer.add(new BigInteger("2")))),
                            new BigInteger("0"));
            default -> log.error("oops");
        }
        return output;
    }

    // "Like position mode, parameters in relative mode can be read from or written to."
    // "Parameters that an instruction writes to will never be in immediate mode."

    private BigInteger getP3() {
        BigInteger output = null;
        switch (getOpcodeString().charAt(0)) {
            case '0' ->   // position mode
                    output = opcodes.get(instructionPointer.add(new BigInteger("3")));
            case '1' ->   // immediate mode
                    log.error("Should not be in immediate mode!");
            case '2' ->   // relative mode
                    output = relativeBaseOffset.add(opcodes.get(instructionPointer.add(new BigInteger("3"))));
            default -> log.error("oops");
        }
        return output;
    }

    public BlockingQueue<BigInteger> getInputQueue() {
        return inputQueue;
    }

    public BlockingQueue<BigInteger> getOutputQueue() {
        return outputQueue;
    }

    public void setOutputQueue(BlockingQueue<BigInteger> bq) {
        outputQueue = bq;
    }
}
