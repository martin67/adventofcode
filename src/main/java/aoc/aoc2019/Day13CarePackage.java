package aoc.aoc2019;

import aoc.Direction;
import aoc.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day13CarePackage {

    @Data
    static class Tile {
        Position position;
        int id;

        public Tile(Position position, int id) {
            this.position = position;
            this.id = id;
        }

        public Tile(Tile t) {
            this.position = t.position;
            this.id = t.id;
        }

        boolean isBrick() {
            return id == 2;
        }

        boolean isWall() {
            return id == 1;
        }
    }

    @Data
    @AllArgsConstructor
    static class SimulationResult {
        int step;
        Position position;
    }

    @Data
    class GameController implements Callable<Integer> {
        BlockingQueue<BigInteger> inputQueue;
        BlockingQueue<BigInteger> outputQueue;

        @Override
        public Integer call() throws Exception {
            Position paddlePosition = new Position(22, 22);
            Position nextPaddlePosition = paddlePosition;
            int steps = 0;
            Queue<SimulationResult> simulation = null;

            getOutputQueue().add(new BigInteger("0"));

            while (true) {
                int x = getInputQueue().take().intValue();
                int y = getInputQueue().take().intValue();
                int id = getInputQueue().take().intValue();

                if (x == -1 && y == 0) {
                    score = id;
                    log.debug("Score: {}", score);
                    // check if game over (all bricks removed)
                    long blocks = tiles.values().stream().filter(Tile::isBrick).count();
                    if (blocks == 0 && tiles.size() == 1056) {
                        log.info("Final score: {}", score);
                        return score;
                    }
                } else {

                    Position pos = new Position(x, y);
                    if (tiles.size() == 1056) {
                        log.debug("Game output: type {}, {}", id, pos);
                    }

                    if (tiles.containsKey(pos)) {
                        tiles.get(pos).id = id;
                    } else {
                        tiles.put(pos, new Tile(pos, id));
                    }

                    if (tiles.size() == 1056 && id == 3) {
                        log.debug("Step {}, moving paddle to {}", steps, pos);
                        paddlePosition = pos;
                    }

                    if (tiles.size() == 1056 && id == 4) {
                        log.debug("Step {}, moving ball to {}", steps, pos);
                        liveBallPositions.add(new Position(pos));
                        steps++;
                    }

                    if (tiles.size() == 1056 && simulation == null) {
                        simulation = simulate(tiles);
                        nextPaddlePosition = simulation.poll().position;
                    }
                }

                if (tiles.size() == 1056 && id == 4) {
//                    printGame();
//                    System.out.println();

                    if (steps == simulation.peek().step + 2) {
                        simulation.poll();
                        nextPaddlePosition = simulation.peek().position;
                    }

                    log.debug("Step: {}: moving paddle position to {} (now at {})", steps, nextPaddlePosition, paddlePosition);
                    log.debug("queue length: {}", getOutputQueue().size());
                    if (nextPaddlePosition.getX() > paddlePosition.getX()) {
                        log.debug("Moving paddle to the right");
                        getOutputQueue().add(new BigInteger("1"));
                    } else if (nextPaddlePosition.getX() < paddlePosition.getX()) {
                        log.debug("Moving paddle to the left");
                        getOutputQueue().add(new BigInteger("-1"));
                    } else {
                        log.debug("Not moving paddle");
                        getOutputQueue().add(new BigInteger("0"));
                    }
                }
            }
        }

        Queue<SimulationResult> simulate(Map<Position, Tile> originalTiles) {
            Queue<SimulationResult> simulation = new LinkedList<>();
            Map<Position, Tile> tiles = new HashMap<>();
            for (Position pos : originalTiles.keySet()) {
                tiles.put(pos, new Tile(originalTiles.get(pos)));
            }
            int stepCounter = 0;
            //Position ballPosition = new Position(19, 18);
            Position ballPosition = new Position(21, 20);
            Direction ballDirection = Direction.SouthEast;
            simulation.add(new SimulationResult(0, new Position(22, 22)));
            simulatedBallPositions.add(new Position(ballPosition));

            log.info("Starting simulation");
            while (tiles.values().stream().anyMatch(Tile::isBrick)) {
                log.debug("Step {}, {} bricks left", stepCounter, tiles.values().stream().filter(Tile::isBrick).count());
                Tile next = tiles.get(ballPosition.adjacent(ballDirection));
                if (next.position.getY() == 22) {
                    // Bounce of paddle
                    ballDirection = ballDirection.bounceWall(Direction.South);
                    simulation.add(new SimulationResult(stepCounter, new Position(ballPosition.getX(), 22)));
                } else {
                    switch (next.id) {
                        case 0:     // empty
                        case 4:     // ball
                            // move the ball
                            ballPosition = ballPosition.adjacent(ballDirection);
                            simulatedBallPositions.add(new Position(ballPosition));
                            // then check that the ball is not adjacent to a brick
                            for (Position brickPosition : ballPosition.adjacentDiagonal(ballDirection)) {
                                if (tiles.get(brickPosition).isBrick()) {
                                    Direction dirToP = ballPosition.directionTo(brickPosition, false);
                                    ballDirection = ballDirection.bounceWall(dirToP);
                                    tiles.get(brickPosition).id = 0;
                                }
                            }
                            stepCounter++;
                            break;
                        case 1:     // wall
                            // Bounce (switch direction) 90 degrees
                            for (Position wallPosition : ballPosition.adjacentDiagonal(ballDirection)) {
                                if (tiles.get(wallPosition).isWall()) {
                                    Direction dirToP = ballPosition.directionTo(wallPosition, false);
                                    ballDirection = ballDirection.bounceWall(dirToP);
                                }
                            }
                            break;
                        case 2:     // block
                            // just switch direction going back the opposite way
                            // and remove tile
                            ballDirection = ballDirection.opposite();
                            next.id = 0;
                            break;
                        default:
                            log.error("Oops");
                            break;
                    }
                }
            }
            // end with start position
            simulation.add(new SimulationResult(stepCounter, new Position(22, 22)));
            log.info("Simulation complete, {} steps", simulation.size());
            return simulation;
        }
    }


    final ExecutorService executorService;
    private final List<String> opcodes;
    final Map<Position, Tile> tiles;
    int score;
    final List<Position> simulatedBallPositions = new ArrayList<>();
    final List<Position> liveBallPositions = new ArrayList<>();


    public Day13CarePackage(List<String> inputLines) {
        executorService = Executors.newCachedThreadPool();
        opcodes = Stream.of(inputLines.get(0).split(","))
                .collect(Collectors.toList());
        tiles = new HashMap<>();
    }

    long numberOfBlockTiles() throws ExecutionException, InterruptedException {
        IntcodeComputer ic = new IntcodeComputer(opcodes);
        Future<Integer> futureSum = executorService.submit(ic);
        futureSum.get();

        List<BigInteger> output = new ArrayList<>();
        ic.getOutputQueue().drainTo(output);

        for (int i = 0; i < output.size(); i += 3) {
            Position pos = new Position(output.get(i).intValue(), output.get(i + 1).intValue());
            int id = output.get(i + 2).intValue();
            if (tiles.containsKey(pos)) {
                tiles.get(pos).id = id;
            } else {
                tiles.put(pos, new Tile(pos, id));
            }
        }

        return tiles.values().stream().filter(t -> t.getId() == 2).count();
    }

    int lastScore() throws InterruptedException, ExecutionException {
        opcodes.set(0, "2");
        IntcodeComputer ic = new IntcodeComputer(opcodes);
        GameController gc = new GameController();
        gc.setInputQueue(ic.getOutputQueue());
        gc.setOutputQueue(ic.getInputQueue());

        executorService.submit(ic);
        Future<Integer> futureSum = executorService.submit(gc);
        return futureSum.get();
    }

    void printGame() {
        // find size of message
        int minX = tiles.keySet().stream().mapToInt(Position::getX).min().orElse(0);
        int minY = tiles.keySet().stream().mapToInt(Position::getY).min().orElse(0);
        int maxX = tiles.keySet().stream().mapToInt(Position::getX).max().orElse(0);
        int maxY = tiles.keySet().stream().mapToInt(Position::getY).max().orElse(0);

        for (int y = minY; y <= maxY; y++) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%02d ", y));
            for (int x = minX; x <= maxX; x++) {
                Position pos = new Position(x, y);
                if (tiles.containsKey(pos)) {
                    switch (tiles.get(pos).id) {
                        case 0:
                            sb.append('.');     // empty
                            break;
                        case 1:
                            sb.append("#");     // wall
                            break;
                        case 2:
                            sb.append("%");     // block
                            break;
                        case 3:
                            sb.append("_");     // paddle
                            break;
                        case 4:
                            sb.append("*");     // ball
                            break;
                    }
                } else {
                    sb.append(' ');
                }
            }
            System.out.println(sb.toString());
        }
    }
}

// 44 x 24 playing field

// bounces back in the opposite direction when hitting a block. Block is destroyed