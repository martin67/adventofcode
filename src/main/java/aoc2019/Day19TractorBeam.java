package aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day19TractorBeam {

    @Data
    static class DroneController implements Callable<Integer> {
        private BlockingQueue<BigInteger> inputQueue;
        private BlockingQueue<BigInteger> outputQueue;
        Set<Position> map = new HashSet<>();
        ExecutorService executorService;
        List<String> opcodes;
        boolean squareMode;

        public DroneController(ExecutorService executorService, List<String> opcodes, boolean squareMode) {
            this.executorService = executorService;
            this.opcodes = opcodes;
            this.squareMode = squareMode;
        }

        @Override
        public Integer call() throws Exception {
            if (!isSquareMode()) {
                for (int y = 0; y < 50; y++) {
                    for (int x = 0; x < 50; x++) {
                        Position pos = new Position(x, y);
                        int state = getState(pos);
                        if (state == 1) {
                            map.add(pos);
                        }
                    }
                }
                printMap();
                return map.size();

            } else {

                Position previousLeftEdge = new Position(20, 18);
                Position previousRightEdge = new Position(22, 18);
                while (true) {
                    // get next left edge
                    Position nextPos = previousLeftEdge.adjacent(Direction.Down);
                    while (getState(nextPos) == 0) {
                        nextPos = nextPos.adjacent(Direction.Right);
                    }
                    previousLeftEdge = nextPos;
                    map.add(previousLeftEdge);

                    // get next right edge
                    nextPos = previousRightEdge.adjacent(Direction.Down);
                    while (getState(nextPos) == 1) {
                        nextPos = nextPos.adjacent(Direction.Right);
                    }
                    previousRightEdge = nextPos.adjacent(Direction.Left);
                    map.add(previousRightEdge);

                    // fill space between
                    for (int x = previousLeftEdge.x; x < previousRightEdge.x; x++) {
                        map.add(new Position(x, previousLeftEdge.y));
                    }

                    int gridsize = 10;
                    if (map.contains(new Position(previousLeftEdge.x, previousLeftEdge.y - gridsize)) &&
                            map.contains(new Position(previousLeftEdge.x + gridsize, previousLeftEdge.y - gridsize))) {
                        log.info("Found it at {}, {}", previousLeftEdge.x, previousLeftEdge.y - gridsize);
                        return previousLeftEdge.x * 10000 + previousLeftEdge.y - gridsize;
                    }
                }
            }
        }

        int getState(Position pos) throws InterruptedException {
            IntcodeComputer ic = new IntcodeComputer(opcodes);
            setInputQueue(ic.getOutputQueue());
            setOutputQueue(ic.getInputQueue());
            outputQueue.add(new BigInteger(String.valueOf(pos.x)));
            outputQueue.add(new BigInteger(String.valueOf(pos.y)));
            executorService.submit(ic);
            return inputQueue.take().intValue();
        }

        void printMap() {
            for (int y = 0; y < 50; y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < 50; x++) {
                    Position pos = new Position(x, y);
                    if (map.contains(pos)) {
                        sb.append("#");     // normal
                    } else {
                        sb.append('.');     // wall
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

    int pointsAffected() throws ExecutionException, InterruptedException {
        DroneController dc = new DroneController(executorService, opcodes, false);
        Future<Integer> futureSum = executorService.submit(dc);
        return futureSum.get();
    }

    int closetSquare() throws ExecutionException, InterruptedException {
        DroneController dc = new DroneController(executorService, opcodes, true);
        Future<Integer> futureSum = executorService.submit(dc);
        return futureSum.get();
    }
}

// 15791361 too high