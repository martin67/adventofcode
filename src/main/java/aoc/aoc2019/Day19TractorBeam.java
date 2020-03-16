package aoc.aoc2019;

import aoc.Direction;
import aoc.Position;
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
                    for (int x = previousLeftEdge.getX(); x < previousRightEdge.getX(); x++) {
                        map.add(new Position(x, previousLeftEdge.getY()));
                    }

                    int gridsize = 10;
                    if (map.contains(new Position(previousLeftEdge.getX(), previousLeftEdge.getY())) &&
                            map.contains(new Position(previousLeftEdge.getX() + gridsize, previousLeftEdge.getY() - gridsize))) {
                        log.info("Found it at {}, {}", previousLeftEdge.getX(), previousLeftEdge.getY() - gridsize);
                        printMap();
                        return previousLeftEdge.getX() * 10000 + previousLeftEdge.getY() - gridsize;
                    }
                }
            }
        }

        int getState(Position pos) throws InterruptedException {
            IntcodeComputer ic = new IntcodeComputer(opcodes);
            setInputQueue(ic.getOutputQueue());
            setOutputQueue(ic.getInputQueue());
            outputQueue.add(new BigInteger(String.valueOf(pos.getX())));
            outputQueue.add(new BigInteger(String.valueOf(pos.getY())));
            executorService.submit(ic);
            return inputQueue.take().intValue();
        }

        void printMap() {
            int xMax = map.stream().mapToInt(Position::getX).max().orElse(0);
            int yMax = map.stream().mapToInt(Position::getY).max().orElse(0);
            for (int y = 0; y <= yMax; y++) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%3d ", y));
                for (int x = 0; x <= xMax; x++) {
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

    final ExecutorService executorService;
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