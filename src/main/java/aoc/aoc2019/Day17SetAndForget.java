package aoc.aoc2019;

import aoc.Direction;
import aoc.Position;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

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
        Position robotPosition;
        Direction robotDirection;
        boolean dustCollector;
        private final Graph<Position, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

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
                    case 94:        // vacuum robot
                        Position pos = new Position(x, y);
                        map.put(pos, output);
                        graph.addVertex(pos);
                        if (map.containsKey(pos.adjacent(Direction.Left))) {
                            graph.addEdge(pos, pos.adjacent(Direction.Left));
                        }
                        if (map.containsKey(pos.adjacent(Direction.Up))) {
                            graph.addEdge(pos, pos.adjacent(Direction.Up));
                        }
                        if (output == 94) {
                            robotPosition = new Position(pos);
                            robotDirection = Direction.North;
                        }
                        x++;
                        break;
                    case 46:        // .
                        x++;
                        break;
                    case 10:        // newline
                        x = 0;
                        y++;
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

            if (!dustCollector) {
                int sum = 0;
                for (Position pos : map.keySet()) {
                    if (map.containsKey(pos.adjacent(Direction.North)) && map.containsKey(pos.adjacent(Direction.South)) &&
                            map.containsKey(pos.adjacent(Direction.East)) && map.containsKey(pos.adjacent(Direction.West))) {
                        sum += pos.getX() * pos.getY();
                    }
                }
                log.info("Received {}", sum);
                return sum;
            } else {
                boolean quit = false;
                int stepsInDirection = 0;
                List<String> instructions = new ArrayList<>();

                while (!quit) {
                    Position pos = robotPosition.adjacent(robotDirection);
                    if (map.containsKey(pos)) {
                        // continue
                        robotPosition = pos;
                        stepsInDirection++;
                    } else {
                        if (stepsInDirection > 0) {
                            instructions.add(String.valueOf(stepsInDirection));
                            stepsInDirection = 0;
                        }
                        if (map.containsKey(robotPosition.adjacent(robotDirection.turn(Direction.Left)))) {
                            instructions.add("L");
                            robotPosition = robotPosition.adjacent(robotDirection.turn(Direction.Left));
                            robotDirection = robotDirection.turn(Direction.Left);
                        } else if (map.containsKey(robotPosition.adjacent(robotDirection.turn(Direction.Right)))) {
                            instructions.add("R");
                            robotPosition = robotPosition.adjacent(robotDirection.turn(Direction.Right));
                            robotDirection = robotDirection.turn(Direction.Right);
                        } else {
                            quit = true;
                        }
                    }
                }
                System.out.println(instructions);
                log.info("Number of instructions: {}", instructions.size());

                // Find patterns
                // max 20 characters in command, excluding newline
                // 34 instructions
                HashMap<List<String>, Integer> instructionFrequency = new HashMap<>();
                for (int patternLength = 2; patternLength < 34; patternLength += 2) {
                    int slider = 0;
                    while (slider + patternLength < instructions.size()) {
                        int start = patternLength;
                        while (patternLength + start < instructions.size()) {
                            List<String> pattern = instructions.subList(start, start + patternLength);
                            if (pattern.equals(instructions.subList(slider, slider + patternLength))) {
                                if (instructionFrequency.containsKey(pattern)) {
                                    instructionFrequency.put(pattern, instructionFrequency.get(pattern) + 1);
                                } else {
                                    instructionFrequency.put(pattern, 1);
                                }
                            }
                            start += patternLength;
                        }
                        slider += patternLength;
                    }
                }
                //instructionFrequency.entrySet().stream().sorted(Comparator.comparing())
//                Set<List<Integer>> filteredInstructions = null;
//                        int hej = instructionFrequency.entrySet().stream()
//                        .filter(i -> i.getKey().size() < 20)
//                        .filter(i -> i.getValue() > 1)
//                        .map(Map.Entry::getKey)
//                        .collect(Collectors.toMap(List<Integer>, Integer));
                return 0;
            }
        }
    }

    final ExecutorService executorService;
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
