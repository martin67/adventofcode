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

        String mainRoutine = "A,A,B,C,B,C,B,C,B,A";
        String pathA = "R,6,L,12,R,6";
        String pathB = "L,12,R,6,L,8,L,12";
        String pathC = "R,12,L,10,L,10";

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
                //System.out.print((char) output);
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
                List<String> instructions = getInstructions();

                //computePath(instructions);

                printIntcodeOutput(inputQueue);
                addStringToQueue(mainRoutine, outputQueue);
                printIntcodeOutput(inputQueue);
                addStringToQueue(pathA, outputQueue);
                printIntcodeOutput(inputQueue);
                addStringToQueue(pathB, outputQueue);
                printIntcodeOutput(inputQueue);
                addStringToQueue(pathC, outputQueue);
                printIntcodeOutput(inputQueue);
                addStringToQueue("n", outputQueue);
                return printIntcodeOutput(inputQueue);
            }
        }

        void addStringToQueue(String in, BlockingQueue<BigInteger> queue) {
            for (char c : in.toCharArray()) {
                String number = String.valueOf((int) c);
                queue.add(new BigInteger(number));
                System.out.print(c);
            }
            queue.add(new BigInteger("10"));
            System.out.println();
        }

        int printIntcodeOutput(BlockingQueue<BigInteger> queue) throws InterruptedException {
            BigInteger bi;
            int finalResult = 0;
            while ((bi = queue.poll(100, TimeUnit.MILLISECONDS)) != null) {
                if (bi.intValue() < 128) {
                    System.out.print((char) bi.intValue());
                } else {
                    System.out.println("Value: " + bi.intValue());
                    finalResult = bi.intValue();
                }
            }
            return finalResult;
        }

        List<String> getInstructions() {
            boolean quit = false;
            int stepsInDirection = 0;
            char lastTurn = '?';
            List<String> instructions = new ArrayList<>();

            while (!quit) {
                Position pos = robotPosition.adjacent(robotDirection);
                if (map.containsKey(pos)) {
                    // continue
                    robotPosition = pos;
                    stepsInDirection++;
                } else {
                    if (stepsInDirection > 0) {
                        instructions.add(lastTurn + String.valueOf(stepsInDirection + 1));
                        stepsInDirection = 0;
                    }
                    if (map.containsKey(robotPosition.adjacent(robotDirection.turn(Direction.Left)))) {
                        lastTurn = 'L';
                        robotPosition = robotPosition.adjacent(robotDirection.turn(Direction.Left));
                        robotDirection = robotDirection.turn(Direction.Left);
                    } else if (map.containsKey(robotPosition.adjacent(robotDirection.turn(Direction.Right)))) {
                        lastTurn = 'R';
                        robotPosition = robotPosition.adjacent(robotDirection.turn(Direction.Right));
                        robotDirection = robotDirection.turn(Direction.Right);
                    } else {
                        quit = true;
                    }
                }
            }
            //System.out.println(instructions);
            log.info("Number of instructions: {}", instructions.size());
            log.info("Total number of steps: {}", pathLength(instructions));
            return instructions;
        }

        void computePath(List<String> instructions) {
            // Find patterns
            // max 20 characters in command, excluding newline
            // 34 instructions
            // A path (A,B or C) can be from 1 to 7 steps
            HashMap<List<String>, Integer> instructionFrequency = new HashMap<>();
            for (int patternLength = 1; patternLength < 8; patternLength++) {
                int start = 0;
                while (patternLength + start < instructions.size()) {
                    List<String> pattern = instructions.subList(start, start + patternLength);
                    if (instructionFrequency.containsKey(pattern)) {
                        instructionFrequency.put(pattern, instructionFrequency.get(pattern) + 1);
                    } else {
                        instructionFrequency.put(pattern, 1);
                    }
                    start++;
                }
            }

            List<List<String>> possibleStartPaths = new ArrayList<>();
            for (int i = 1; i < 8; i++) {
                List<String> path = instructions.subList(0, i);
                if (pathSize(path) <= 20) {
                    possibleStartPaths.add(path);
                }
            }

            List<List<String>> possibleEndPaths = new ArrayList<>();
            for (int i = 1; i < 8; i++) {
                List<String> path = instructions.subList(instructions.size() - i, instructions.size());
                if (pathSize(path) <= 20) {
                    possibleEndPaths.add(path);
                }
            }

            // x * A + y * B + z * C = 34
            // x + y + z <= 10
            //
            for (List<String> startPath : possibleStartPaths) {
                for (List<String> endPath : possibleEndPaths) {
                    int start;
                    int end;
//                        log.info("Before: {}", instructions);
                    List<String> tempPath = new ArrayList<>(instructions);
                    // if the paths are identical, then there will be two extra paths not one
                    if (startPath.equals(endPath)) {
                        log.info("Lists are identical! {}", startPath);

                    } else {
                        // Start with the longest path
                        if (startPath.size() > endPath.size()) {
                            start = removeAllSubLists(tempPath, startPath, "A");
                            end = removeAllSubLists(tempPath, endPath, "C");
                        } else {
                            end = removeAllSubLists(tempPath, endPath, "C");
                            start = removeAllSubLists(tempPath, startPath, "A");
                        }
                        if (start > 0 && end > 0) {
                            log.info("Removing {} x start {} and {} x end {}", start, startPath, end, endPath);
                            log.info("After: ({}) {}", tempPath.size(), tempPath);
                            List<String> path2 = new ArrayList<>();
                            for (String s : tempPath) {
                                if (s.startsWith("A") || s.startsWith("C")) {

                                } else {
                                    path2.add(s);
                                }
                            }
                            log.info("Final: ({}) {}\n", path2.size(), path2);
                        }
                    }
                }
            }
        }

        int removeAllSubLists(List<String> list, List<String> subList, String id) {
            int index;
            int numberOfRemovals = 0;
            do {
                index = Collections.indexOfSubList(list, subList);
                if (index != -1) {
                    //list.subList(index, index + subList.size()).clear();
                    for (int i = index; i < index + subList.size(); i++) {
                        list.set(i, id);
                    }
                    numberOfRemovals++;
                }
            } while (index != -1);
            return numberOfRemovals;
        }

        int pathLength(List<String> path) {
            int length = 0;
            for (String step : path) {
                length += Integer.parseInt(step.substring(1));
            }

            return length;
        }

        int pathSize(List<String> path) {
            return String.join(",", path).length();
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
