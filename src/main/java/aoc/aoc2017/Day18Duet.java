package aoc.aoc2017;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class Day18Duet {

    private final List<Instruction> instructions = new ArrayList<>();

    public Day18Duet(List<String> inputLines) {
        inputLines.forEach(line -> {
            String[] s = line.split(" ");
            instructions.add(new Instruction(s[0], s[1], s.length == 3 ? s[2] : null));
        });
    }

    public long problem1() {
        int pointer = 0;
        long soundPlayed = 0;
        long soundPlayedFirst = 0;
        Map<String, Long> registers = new HashMap<>();

        while (pointer >= 0 && pointer < instructions.size()) {
            Instruction instruction = instructions.get(pointer);
            log.debug("Register: {} - instruction {}, {} {}", registers, instruction.name, instruction.register, instruction.parameter);
            switch (instruction.name) {
                case "snd" -> {
                    soundPlayed = instruction.getValue(registers);
                    log.debug("Sound played: {}", soundPlayed);
                    pointer++;
                }
                case "set" -> {
                    registers.put(instruction.register, instruction.getValue(registers));
                    pointer++;
                }
                case "add" -> {
                    registers.merge(instruction.register, instruction.getValue(registers), Long::sum);
                    pointer++;
                }
                case "mul" -> {
                    registers.put(instruction.register, registers.getOrDefault(instruction.register, 0L) * instruction.getValue(registers));
                    pointer++;
                }
                case "mod" -> {
                    registers.put(instruction.register, registers.getOrDefault(instruction.register, 0L) % instruction.getValue(registers));
                    pointer++;
                }
                case "rcv" -> {
                    if (instruction.getValue(registers) != 0) {
                        if (soundPlayedFirst == 0) {
                            log.info("First time sound: {}", soundPlayed);
                            soundPlayedFirst = soundPlayed;

                            // exit
                            pointer = -2;
                        }
                    }
                    pointer++;
                }
                case "jgz" -> {
                    if (registers.getOrDefault(instruction.register, 0L) > 0) {
                        pointer += instruction.getValue(registers);
                    } else {
                        pointer++;
                    }
                }
                default -> log.error("Unknown instruction: {}", instruction.name);
            }
        }
        return soundPlayedFirst;
    }

    public int problem2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = null;
        Tablet tablet0 = new Tablet(0);
        Tablet tablet1 = new Tablet(1);
        tablet0.inputQueue = tablet1.outputQueue;
        tablet1.inputQueue = tablet0.outputQueue;
        int result;

        try {
            executorService = Executors.newCachedThreadPool();
            executorService.submit(tablet0);
            Future<Integer> sends = executorService.submit(tablet1);
            result = sends.get();
        } finally {
            if (executorService != null)
                executorService.shutdown();
        }
        return result;
    }

    record Instruction(String name, String register, String parameter) {

        long getValue(Map<String, Long> registers) {
            if (parameter == null) {
                if (register.matches("-?\\d+")) {
                    return Integer.parseInt(register);
                } else {
                    return registers.getOrDefault(register, 0L);
                }
            } else {
                if (parameter.matches("-?\\d+")) {
                    return Integer.parseInt(parameter);
                } else {
                    return registers.getOrDefault(parameter, 0L);
                }
            }
        }
    }

    class Tablet implements Callable<Integer> {
        final Map<String, Long> registers = new HashMap<>();
        private final BlockingQueue<Long> outputQueue = new LinkedBlockingQueue<>();
        private BlockingQueue<Long> inputQueue;

        public Tablet(int id) {
            registers.put("p", (long) id);
        }

        @Override
        public Integer call() throws Exception {

            int pointer = 0;
            int numberOfSend = 0;
            while (pointer >= 0 && pointer < instructions.size()) {
                Instruction instruction = instructions.get(pointer);
                switch (instruction.name) {
                    case "snd" -> {
                        outputQueue.put(instruction.getValue(registers));
                        numberOfSend++;
                        pointer++;
                    }
                    case "set" -> {
                        registers.put(instruction.register, instruction.getValue(registers));
                        pointer++;
                    }
                    case "add" -> {
                        registers.merge(instruction.register, instruction.getValue(registers), Long::sum);
                        pointer++;
                    }
                    case "mul" -> {
                        registers.put(instruction.register, Math.multiplyExact(registers.getOrDefault(instruction.register, 0L), instruction.getValue(registers)));
                        pointer++;
                    }
                    case "mod" -> {
                        registers.put(instruction.register, registers.getOrDefault(instruction.register, 0L) % instruction.getValue(registers));
                        pointer++;
                    }
                    case "rcv" -> {
                        // deandlock after 1 sec wait
                        registers.put(instruction.register, inputQueue.poll(1, TimeUnit.SECONDS));
                        if (registers.get(instruction.register) == null) {
                            log.info("timeout!");
                            return numberOfSend;
                        }
                        pointer++;
                    }
                    case "jgz" -> {
                        if (instruction.register.matches("\\d+")) {
                            int val = Integer.parseInt(instruction.register);
                            if (val > 0) {
                                pointer += Integer.parseInt(instruction.parameter);
                            }
                        } else if (registers.getOrDefault(instruction.register, 0L) > 0) {
                            pointer += instruction.getValue(registers);
                        } else {
                            pointer++;
                        }
                    }
                    default -> log.error("Unknown instruction: {}", instruction.name);
                }

            }
            // Should not reach this...
            return -1;
        }
    }
}