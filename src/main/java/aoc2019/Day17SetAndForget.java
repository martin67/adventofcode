package aoc2019;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day17SetAndForget {

    @Data
    static class RemoteControl implements Callable<Integer> {
        private BlockingQueue<BigInteger> inputQueue;
        private BlockingQueue<BigInteger> outputQueue;

        Map<Position, Integer> map = new HashMap<>();
        Position currentPosition;
        Position startPosition;
        Position oxygenSystem;
        boolean dustCollector;

        public RemoteControl(boolean dustCollector) {
            this.dustCollector = dustCollector;
        }

        @SneakyThrows
        @Override
        public Integer call() {
            int x = 0;
            int y = 0;
            int total = 0;
            while (total < 2497) {
                int output = inputQueue.take().intValue();
                switch (output) {
                    case 35:        // #
                        map.put(new Position(x, y), output);
                        x++;
                        break;
                    case 46:        // .
                        x++;
                        break;
                    case 10:        // newline
                        x = 0;
                        y++;
                        break;
                    case 94:        // vacuum robot
                        x++;
                        break;
                    default:
                        log.error("Oops, {} at {},{}", output, x, y);
                        x++;
                        break;
                }
                //printMap();
                System.out.print((char) output);
                total++;
            }

            int sum = 0;
            for (Position pos : map.keySet()) {
                if (map.containsKey(pos.adjacent(Direction.North)) && map.containsKey(pos.adjacent(Direction.South)) &&
                        map.containsKey(pos.adjacent(Direction.East)) && map.containsKey(pos.adjacent(Direction.West))) {
                    sum += pos.x * pos.y;
                }
            }
            log.info("Received {}", sum);
            return sum;
        }
    }

    ExecutorService executorService;
    private final List<String> opcodes;

    public Day17SetAndForget(List<String> inputLines) {
        executorService = Executors.newCachedThreadPool();
        opcodes = Stream.of(inputLines.get(0).split(","))
                .collect(Collectors.toList());
    }

    int sumOfAlignmentParameters() throws InterruptedException, ExecutionException {
        IntcodeComputer ic = new IntcodeComputer(opcodes);
        RemoteControl rc = new RemoteControl(false);
        rc.setInputQueue(ic.getOutputQueue());
        rc.setOutputQueue(ic.getInputQueue());

        executorService.submit(ic);
        Future<Integer> futureSum = executorService.submit(rc);

        return futureSum.get();
    }

    int dustCollected() throws ExecutionException, InterruptedException {
        opcodes.set(0, "2");
        IntcodeComputer ic = new IntcodeComputer(opcodes);
        RemoteControl rc = new RemoteControl(true);
        rc.setInputQueue(ic.getOutputQueue());
        rc.setOutputQueue(ic.getInputQueue());

        executorService.submit(ic);
        Future<Integer> futureSum = executorService.submit(rc);

        return futureSum.get();
    }

}
