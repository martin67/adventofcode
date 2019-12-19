package aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day19TractorBeam {

    @Data
    static class DroneController implements Callable<Long> {
        private BlockingQueue<BigInteger> inputQueue;
        private BlockingQueue<BigInteger> outputQueue;
        Map<Position, Integer> map = new HashMap<>();
        ExecutorService executorService;
        List<String> opcodes;

        public DroneController(ExecutorService executorService, List<String> opcodes) {
            this.executorService = executorService;
            this.opcodes = opcodes;
        }

        @Override
        public Long call() throws Exception {
            for (int x = 0; x < 50; x++) {
                for (int y = 0; y < 50; y++) {
                    IntcodeComputer ic = new IntcodeComputer(opcodes);
                    setInputQueue(ic.getOutputQueue());
                    setOutputQueue(ic.getInputQueue());
                    outputQueue.add(new BigInteger(String.valueOf(x)));
                    outputQueue.add(new BigInteger(String.valueOf(y)));
                    executorService.submit(ic);
                    BigInteger state = inputQueue.take();
                    map.put(new Position(x, y), state.intValue());
                }
            }
            printMap();
            return map.values().stream().filter(i -> i == 1).count();
        }

        void printMap() {
            for (int y = 0; y < 50; y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < 50; x++) {
                    Position pos = new Position(x, y);
                        switch (map.get(pos)) {
                            case 0:
                                sb.append('.');     // wall
                                break;
                            case 1:
                                sb.append("#");     // normal
                                break;
                        }
                }
                System.out.println(sb.toString());
            }
        }
    }

    ExecutorService executorService;
    private final List<String> opcodes;

    public Day19TractorBeam(List<String> inputLines) {
        executorService = Executors.newCachedThreadPool();
        opcodes = Stream.of(inputLines.get(0).split(","))
                .collect(Collectors.toList());
    }

    long pointsAffected() throws ExecutionException, InterruptedException {
        DroneController dc = new DroneController(executorService, opcodes);
        Future<Long> futureSum = executorService.submit(dc);
        return futureSum.get();
    }
}
