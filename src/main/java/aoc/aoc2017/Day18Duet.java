package aoc.aoc2017;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class Day18Duet {

    final ExecutorService executorService;
    List<Instruction> instructions = new ArrayList<>();


    public Day18Duet(List<String> inputLines) {
        executorService = Executors.newCachedThreadPool();

        inputLines.forEach(line -> {
            String[] s = line.split(" ");
            instructions.add(new Instruction(s[0], s[1], s.length == 3 ? s[2] : null));
        });
    }

    long problem1() {
        int pointer = 0;
        long soundPlayed = 0;
        long soundPlayedFirst = 0;
        Map<String, Long> registers = new HashMap<>();

        while (pointer >= 0 && pointer < instructions.size()) {
            Instruction instruction = instructions.get(pointer);
            log.debug("Register: {} - instruction {}, {} {}", registers, instruction.name, instruction.register, instruction.parameter);
            switch (instruction.name) {
                case "snd":
                    soundPlayed = instruction.getValue(registers);
                    log.debug("Sound played: {}", soundPlayed);
                    pointer++;
                    break;
                case "set":
                    registers.put(instruction.register, instruction.getValue(registers));
                    pointer++;
                    break;
                case "add":
                    registers.merge(instruction.register, instruction.getValue(registers), Long::sum);
                    pointer++;
                    break;
                case "mul":
                    registers.put(instruction.register, registers.getOrDefault(instruction.register, 0L) * instruction.getValue(registers));
                    pointer++;
                    break;
                case "mod":
                    registers.put(instruction.register, registers.getOrDefault(instruction.register, 0L) % instruction.getValue(registers));
                    pointer++;
                    break;
                case "rcv":
                    if (instruction.getValue(registers) != 0) {
                        if (soundPlayedFirst == 0) {
                            log.info("First time sound: {}", soundPlayed);
                            soundPlayedFirst = soundPlayed;

                            // exit
                            pointer = -2;
                        }
                    }
                    pointer++;
                    break;
                case "jgz":
                    if (registers.getOrDefault(instruction.register, 0L) > 0) {
                        pointer += instruction.getValue(registers);
                    } else {
                        pointer++;
                    }
                    break;

                default:
                    log.error("Unknown instruction: {}", instruction.name);
                    break;
            }
        }
        return soundPlayedFirst;
    }

    int problem2() throws ExecutionException, InterruptedException {
        Tablet tablet0 = new Tablet(0);
        Tablet tablet1 = new Tablet(1);
        tablet0.inputQueue = tablet1.outputQueue;
        tablet1.inputQueue = tablet0.outputQueue;

        executorService.submit(tablet0);
        Future<Integer> sends = executorService.submit(tablet1);


        return sends.get();
    }

    static class Instruction {
        String name;
        String register;
        String parameter;


        public Instruction(String name, String register, String parameter) {
            this.name = name;
            this.register = register;
            this.parameter = parameter;
        }

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
        private final int id;
        private BlockingQueue<Long> inputQueue;
        private BlockingQueue<Long> outputQueue = new LinkedBlockingQueue<>();
        Map<String, Long> registers = new HashMap<>();

        public Tablet(int id) {
            this.id = id;
            registers.put("p", (long) id);
        }

        @Override
        public Integer call() throws Exception {

            int pointer = 0;
            int numberOfSend = 0;
            while (pointer >= 0 && pointer < instructions.size()) {
                Instruction instruction = instructions.get(pointer);
                //log.debug("[{}] Register: {} - instruction {}, {} {}", id, registers, instruction.name, instruction.register, instruction.parameter);
                switch (instruction.name) {
                    case "snd":
                        //log.debug("[{}] Sending: {}", id, instruction.getValue(registers));
                        outputQueue.put(instruction.getValue(registers));
                        numberOfSend++;
                        pointer++;
                        break;
                    case "set":
                        registers.put(instruction.register, instruction.getValue(registers));
                        pointer++;
                        break;
                    case "add":
                        registers.merge(instruction.register, instruction.getValue(registers), Long::sum);
                        pointer++;
                        break;
                    case "mul":
                        registers.put(instruction.register, registers.getOrDefault(instruction.register, 0L) * instruction.getValue(registers));
                        pointer++;
                        break;
                    case "mod":
                        registers.put(instruction.register, registers.getOrDefault(instruction.register, 0L) % instruction.getValue(registers));
                        pointer++;
                        break;
                    case "rcv":
                        //registers.put(instruction.register, inputQueue.take());
                        registers.put(instruction.register, inputQueue.poll(1, TimeUnit.SECONDS));
                        if (registers.get(instruction.register) != 34) {
                            log.info("[{}] Received: {}", id, registers.get(instruction.register));
                        }
                        if (registers.get(instruction.register) == null) {
                            log.info("timeout!");
                        }
                        pointer++;
                        break;
                    case "jgz":
                        if (registers.getOrDefault(instruction.register, 0L) > 0) {
                            pointer += instruction.getValue(registers);
                        } else {
                            pointer++;
                        }
                        break;

                    default:
                        log.error("Unknown instruction: {}", instruction.name);
                        break;
                }

            }
            return numberOfSend;
        }
    }
}