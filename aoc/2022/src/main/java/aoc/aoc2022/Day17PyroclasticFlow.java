package aoc.aoc2022;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static aoc.common.Direction.Down;

@Slf4j
public class Day17PyroclasticFlow {

    final List<Shape> shapes = new ArrayList<>();
    final Set<Position> cave = new HashSet<>();
    final String jets;
    int jetPosition = 0;

    public Day17PyroclasticFlow(List<String> inputLines) {
        shapes.add(new Shape(4, 1, Set.of(new Position(0, 0), new Position(1, 0),
                new Position(2, 0), new Position(3, 0))));
        shapes.add(new Shape(3, 3, Set.of(new Position(1, 0), new Position(0, 1),
                new Position(1, 1), new Position(2, 1), new Position(1, 2))));
        shapes.add(new Shape(3, 3, Set.of(new Position(2, 0), new Position(2, 1),
                new Position(0, 2), new Position(1, 2), new Position(2, 2))));
        shapes.add(new Shape(1, 4, Set.of(new Position(0, 0), new Position(0, 1),
                new Position(0, 2), new Position(0, 3))));
        shapes.add(new Shape(2, 2, Set.of(new Position(0, 0), new Position(1, 0),
                new Position(0, 1), new Position(1, 1))));

        jets = inputLines.get(0);
        log.info("Number of jets: {}", jets.length());
    }

    int problem1() {
        int highestRock = 0;
        for (int i = 0; i < 2022; i++) {
            Shape s = new Shape(shapes.get(i % 5));
            s.position = new Position(2, highestRock - s.height - 3);
            do {
                s.moveSideways(getJetDirection());
            } while (s.moveDown());
            cave.addAll(s.getPositions());
            if (s.position.getY() < highestRock) {
                highestRock = s.position.getY();
            }
        }
        return -highestRock;
    }

    long problem2() {
        boolean keepGoing = true;
        int highestRock = 0;
        int previous = 0;
        int iterations = 1;
        int iterationsBeforeRepeat = 15581 + 15582 + 15584 + 15573 + 15587 + 15600 + 15603 + 15554 + 15599 + 15569 + 15590 + 15592 + 15560 + 15559 + 15565;
        while (keepGoing) {
            int loop = jets.length();
            for (int i = 0; i < iterationsBeforeRepeat; i++) {
                Shape s = new Shape(shapes.get(i % 5));
                s.position = new Position(2, highestRock - s.height - 3);
                do {
                    s.moveSideways(getJetDirection());
                } while (s.moveDown());
                cave.addAll(s.getPositions());
                if (s.position.getY() < highestRock) {
                    highestRock = s.position.getY();
                }
            }
            log.info("1 i: {} {}, highest: {}, diff from previous {}", loop * iterations, loop, highestRock, highestRock - previous);

            for (int i = 0; i < loop; i++) {
                Shape s = new Shape(shapes.get(i % 5));
                s.position = new Position(2, highestRock - s.height - 3);
                do {
                    s.moveSideways(getJetDirection());
                } while (s.moveDown());
                cave.addAll(s.getPositions());
                if (s.position.getY() < highestRock) {
                    highestRock = s.position.getY();
                }
            }
            log.info("2 i: {} {}, highest: {}, diff from previous {}", loop * iterations, loop, highestRock, highestRock - previous);

            for (int i = 0; i < 43618; i++) {
                Shape s = new Shape(shapes.get(i % 5));
                s.position = new Position(2, highestRock - s.height - 3);
                do {
                    s.moveSideways(getJetDirection());
                } while (s.moveDown());
                cave.addAll(s.getPositions());
                if (s.position.getY() < highestRock) {
                    highestRock = s.position.getY();
                }
            }
            log.info("3 i: {} {}, highest: {}, diff from previous {}", loop * iterations, loop, highestRock, highestRock - previous);
            previous = highestRock;
            iterations++;
        }
        return 0;
    }

    long problem21() {
        long rounds = 1000000000000L;
        long height = 0L;
        long step = 200L;
        long numberOfStepsPerIteration = 7L;
        long increase = 300 + 306 + 303 + 303 + 301 + 306 + 301;
        // first iteration
        rounds -= step;
        height += 308L;

        long a = rounds / (step * numberOfStepsPerIteration);
        long b = rounds % (step * numberOfStepsPerIteration);

        log.info("a: {}, b: {}", a, b);
        height += a * increase;
        height += 300;
        return height;
    }

    long problem22() {
        long rounds = 1000000000000L;
        long height = 0L;
        long step = 10091L;
        long numberOfStepsPerIteration = 15L;
        long increase = 15581 + 15582 + 15584 + 15573 + 15587 + 15600 + 15603 + 15554 + 15599 + 15569 + 15590 + 15592 + 15560 + 15559 + 15565;
        // first iteration
        rounds -= step * 12;
        height += 15576 + 15610 + 15603 + 15598 + 15602 + 15612 + 15565 + 15588 + 15588 + 15564 + 15564 + 15580;

        long a = rounds / (step * numberOfStepsPerIteration);
        long b = rounds % (step * numberOfStepsPerIteration);
        // a: 6606546
        // b: 43618  så många extra rundor behövs

        log.info("a: {}, b: {}", a, b);
        height += a * increase;
        height += 443492;
        return height;
    }

    long problem23() {
        // Find repeating pattern. Run the whole jetstream 100 times and see if we can find a pattern
        int highestRock = 0;
        List<Integer> delta = new ArrayList<>();
        int previous = 0;
        int numberOfShapes = 100;
        int numberOfJets = jets.length();
        int iterations = 5 * numberOfJets;
        for (int i = 0; i < numberOfShapes; i++) {
            for (int j = 0; j < iterations; j++) {
                Shape s = new Shape(shapes.get(j % 5));
                s.position = new Position(2, highestRock - s.height - 3);
                do {
                    s.moveSideways(getJetDirection());
                } while (s.moveDown());
                cave.addAll(s.getPositions());
                if (s.position.getY() < highestRock) {
                    highestRock = s.position.getY();
                }
            }
            log.info("i: {}, highest: {}, diff from previous {}", i, highestRock, highestRock - previous);
            delta.add(previous -  highestRock);
            previous = highestRock;
        }
        return 0;
    }

    void findRepeatingPatttern(List<Integer> pattern) {
        // start with an initial offset of 1
        for (int i = 1; i < pattern.size(); i++) {

        }
    }
    Direction getJetDirection() {
        Direction dir = switch (jets.charAt(jetPosition)) {
            case '>' -> Direction.Right;
            case '<' -> Direction.Left;
            default -> throw new IllegalStateException("Unexpected value: " + jets.charAt(jetPosition));
        };
        jetPosition++;
        if (jetPosition == jets.length()) {
            jetPosition = 0;
        }
        return dir;
    }

    class Shape {
        Position position;
        final int width;
        final int height;
        final Set<Position> pattern;

        public Shape(int width, int height, Set<Position> pattern) {
            this.width = width;
            this.height = height;
            this.pattern = pattern;
        }

        public Shape(Shape shape) {
            this.width = shape.width;
            this.height = shape.height;
            this.pattern = new HashSet<>();
            for (Position p : shape.pattern) {
                this.pattern.add(new Position(p));
            }
        }

        Set<Position> getPositions() {
            Set<Position> result = new HashSet<>();
            for (Position p : pattern) {
                result.add(new Position(p.getX() + position.getX(), p.getY() + position.getY()));
            }
            return result;
        }

        void moveSideways(Direction dir) {
            boolean okToMove = true;
            for (Position p : getPositions()) {
                Position newP = new Position(p.adjacent(dir));
                if (cave.contains(newP) || newP.getX() < 0 || newP.getX() > 6) {
                    okToMove = false;
                }
            }
            if (okToMove) {
                position = position.adjacent(dir);
            }
        }

        boolean moveDown() {
            boolean okToMove = true;
            for (Position p : getPositions()) {
                Position newP = new Position(p.adjacent(Down));
                if (cave.contains(newP) || newP.getY() == 0) {
                    okToMove = false;
                }
            }
            if (okToMove) {
                position = position.adjacent(Down);
            }
            return okToMove;
        }

    }
}
