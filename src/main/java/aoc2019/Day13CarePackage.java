package aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
    }

    @Data
    class Ball {
        Position position;
        Direction direction;

        public Ball(Position position, Direction direction) {
            this.position = position;
            this.direction = direction;
        }

        void updatePosition(Position newPosition) {
            direction = position.directionTo(newPosition, true);
            position = newPosition;
        }

        Position predictPaddlePosition() {
            Position newPosition = this.position;
            Direction newDirection = this.direction;

            while (newPosition.getX() != 22) {
                Tile next = tiles.get(newPosition.adjacent(newDirection));
                switch (next.id) {
                    case 0:     // empty
                        newPosition = next.getPosition();
                        break;
                    case 1:     // wall
                        // Bounce (switch direction) 90 degrees
                        newDirection = newPosition.bounce(newDirection);
                        break;
                    case 2:     // block
                        // just switch direction going back the opposite way
                        // no need remove any block. Only one can be removed each round
                        newDirection = newPosition.opposite(newDirection);
                        break;
                    default:
                        log.error("Oops");
                        break;
                }
            }
            if (newDirection == Direction.SouthEast) {
                newPosition.x--;
            } else {
                newPosition.x++;
            }
            return newPosition;
        }
    }

    ExecutorService executorService;
    private final List<String> opcodes;
    Map<Position, Tile> tiles;
    int score;

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
        Future<Integer> futureSum = executorService.submit(ic);
        futureSum.get();

        boolean quit = false;
        Ball ball = new Ball(new Position(19, 18), Direction.SouthEast);
        Position paddlePosition = new Position(22, 22);

        while (!quit) {
            int x = ic.getOutputQueue().take().intValue();
            int y = ic.getOutputQueue().take().intValue();
            int id = ic.getOutputQueue().take().intValue();

            if (x == -1 && y == 0) {
                score = id;
                log.info("Score: {}", score);
            } else {
                // check if game over (all blocks removed)
                long blocks = tiles.values().stream().filter(t -> t.getId() == 2).count();
                if (blocks == 0 && tiles.size() == 1056) {
                    return score;
                }

                // predict ball position
                // move joystick to ball position (or wait if it's already there)

                // temp test
                Position pos = new Position(x, y);
                if (tiles.size() == 1056) {
                    log.info("Game output: type {}, {}", id, pos);
                }
                if (tiles.containsKey(pos)) {
                    tiles.get(pos).id = id;
                } else {
                    tiles.put(pos, new Tile(pos, id));
                }

                if (id == 3) {
                    log.info("Paddle at {}", pos);
                    paddlePosition = pos;
                }
                if (id == 4) {
                    log.info("Ball at {}", pos);
                    ball.updatePosition(pos);
                }

                if (tiles.size() == 1056) {
                    printGame();
                    System.out.println();

                    Position nextPaddlePosition = ball.predictPaddlePosition();
                    log.info("Predicting final paddle position to {}", nextPaddlePosition);
                    if (nextPaddlePosition.x > paddlePosition.x) {
                        log.info("Moving paddle to the right");
                        ic.getInputQueue().add(new BigInteger("1"));
                    } else if (nextPaddlePosition.x < paddlePosition.x) {
                        log.info("Moving paddle to the left");
                        ic.getInputQueue().add(new BigInteger("-1"));
                    } else {
                        log.info("Not moving paddle");
                        ic.getInputQueue().add(new BigInteger("0"));
                    }
                }
            }
        }

        return 0;
    }

    void printGame() {
        // find size of message
        int minX = tiles.keySet().stream().mapToInt(Position::getX).min().orElse(0);
        int minY = tiles.keySet().stream().mapToInt(Position::getY).min().orElse(0);
        int maxX = tiles.keySet().stream().mapToInt(Position::getX).max().orElse(0);
        int maxY = tiles.keySet().stream().mapToInt(Position::getY).max().orElse(0);

        for (int y = minY; y <= maxY; y++) {
            StringBuilder sb = new StringBuilder();
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