package aoc.aoc2019;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day15OxygenSystem {

    private final List<String> opcodes;

    public Day15OxygenSystem(List<String> inputLines) {
        opcodes = Stream.of(inputLines.get(0).split(","))
                .collect(Collectors.toList());
    }

    int fewestNumberOfMovementCommands(boolean secondTest) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        IntcodeComputer ic = new IntcodeComputer(opcodes);
        RemoteControl rc = new RemoteControl(secondTest);
        rc.setInputQueue(ic.getOutputQueue());
        rc.setOutputQueue(ic.getInputQueue());

        executorService.submit(ic);
        Future<Integer> futureSum = executorService.submit(rc);

        return futureSum.get();
    }

    @Data
    static class RemoteControl implements Callable<Integer> {
        private final Graph<Position, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        Map<Position, Integer> map = new HashMap<>();
        Position currentPosition;
        Queue<Position> positionsToCheck = new LinkedList<>();
        Position startPosition;
        Position oxygenSystem;
        boolean secondTest;
        DijkstraShortestPath<Position, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        private BlockingQueue<BigInteger> inputQueue;
        private BlockingQueue<BigInteger> outputQueue;

        public RemoteControl(boolean secondTest) {
            this.secondTest = secondTest;
        }

        @SneakyThrows
        @Override
        public Integer call() {

            Position startPosition = new Position(0, 0);
            Position currentPosition = new Position(startPosition);

            map.put(currentPosition, 1);
            graph.addVertex(currentPosition);
            checkPosition(currentPosition);

            while (!positionsToCheck.isEmpty()) {
                // try to go to positionToCheck from current position
                Position positionToCheck = positionsToCheck.poll();
                currentPosition = gotoPosition(currentPosition, positionToCheck);
                checkPosition(currentPosition);
                //printMap();
            }

            printMap();

            ShortestPathAlgorithm.SingleSourcePaths<Position, DefaultEdge> iPaths;
            if (!secondTest) {
                iPaths = dijkstraAlg.getPaths(startPosition);
                log.info("Shortest path length from {} to {} : {}", startPosition, oxygenSystem, iPaths.getPath(oxygenSystem).getLength());
                return iPaths.getPath(oxygenSystem).getLength();
            } else {
                // Find longest shortest-path from oxygen system.
                iPaths = dijkstraAlg.getPaths(oxygenSystem);
                int length;
                ShortestPathAlgorithm.SingleSourcePaths<Position, DefaultEdge> finalIPaths = iPaths;
                length = map.keySet().stream().filter(p -> map.get(p) == 1)
                        .mapToInt(p -> finalIPaths.getPath(p).getLength()).max().orElse(0);
                log.info("Longest path length: {}", length);
                return length;
            }
        }

        void checkPosition(Position start) throws InterruptedException {
            for (Position pos : start.allAdjacent()) {
                // Check all adjacent areas
                if (!map.containsKey(pos)) {
                    int status;
                    // Go one step
                    status = go(start.directionTo(pos, false));
                    log.debug("Going from {} to {}", start, pos);
                    map.put(pos, status);
                    if (status > 0) {
                        positionsToCheck.add(pos);
                        graph.addVertex(pos);
                        graph.addEdge(start, pos);
                        log.debug("Going back from {} to {}", pos, start);
                        go(pos.directionTo(start, false));
                        if (status == 2) {
                            oxygenSystem = pos;
                            log.info("Found oxygen system at{}", oxygenSystem);
                        }
                    }
                }
            }
        }

        Position gotoPosition(Position source, Position target) throws InterruptedException {
            log.debug("Going from {} to {}", source, target);
            ShortestPathAlgorithm.SingleSourcePaths<Position, DefaultEdge> iPaths = dijkstraAlg.getPaths(source);

            Position previous = source;
            for (Position next : iPaths.getPath(target).getVertexList()) {
                if (!next.equals(previous)) {
                    Direction direction = previous.directionTo(next, false);
                    log.debug("Stepping {} from {} to {}", direction, previous, next);
                    go(direction);
                    previous = next;
                }
            }
            return target;
        }

        int go(Direction direction) throws InterruptedException {
            switch (direction) {
                case North -> outputQueue.add(new BigInteger("1"));
                case South -> outputQueue.add(new BigInteger("2"));
                case West -> outputQueue.add(new BigInteger("3"));
                case East -> outputQueue.add(new BigInteger("4"));
            }
            return inputQueue.take().intValue();
        }

        void printMap() {
            // find size of message
            int minX = map.keySet().stream().mapToInt(Position::getX).min().orElse(0);
            int minY = map.keySet().stream().mapToInt(Position::getY).min().orElse(0);
            int maxX = map.keySet().stream().mapToInt(Position::getX).max().orElse(0);
            int maxY = map.keySet().stream().mapToInt(Position::getY).max().orElse(0);

            for (int y = minY; y <= maxY; y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = minX; x <= maxX; x++) {
                    Position pos = new Position(x, y);
                    if (map.containsKey(pos)) {
                        switch (map.get(pos)) {
                            case 0 -> sb.append('#');     // wall
                            case 1 -> sb.append(".");     // normal
                            case 2 -> sb.append("*");     // oxygen
                        }
                    } else {
                        sb.append(' ');
                    }
                }
                System.out.println(sb);
            }
        }
    }
}
