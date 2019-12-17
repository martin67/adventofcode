package aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day11SpacePolice {

    @Data
    static class Panel {
        Position position;
        int color;          // black = 0, white = 1
        int timesPainted;

        public Panel(Position position) {
            this.position = position;
        }
    }

    @Data
    class Robot {
        Panel location;
        Direction direction;

        public Robot(Panel location, Direction direction) {
            this.location = location;
            this.direction = direction;
        }

        void paintAndMove(int color, int turn) {
            log.debug("Painting {} and turning {}", color, turn);
            // Paint
            location.color = color;
            location.timesPainted++;
            // Move, turn 0 = left, 1 = right
            switch (direction) {
                case Up:
                    direction = (turn == 0) ? Direction.Left : Direction.Right;
                    break;
                case Right:
                    direction = (turn == 0) ? Direction.Up : Direction.Down;
                    break;
                case Down:
                    direction = (turn == 0) ? Direction.Right : Direction.Left;
                    break;
                case Left:
                    direction = (turn == 0) ? Direction.Down : Direction.Up;
                    break;
            }
            Position newPosition = location.position.adjacent(direction);
            if (panels.containsKey(newPosition)) {
                location = panels.get(newPosition);
            } else {
                Panel panel = new Panel(newPosition);
                location = panel;
                panels.put(newPosition, panel);
            }
        }
    }

    private final List<String> opcodes;
    private final Map<Position, Panel> panels;

    public Day11SpacePolice(List<String> inputLines) {
        opcodes = Stream.of(inputLines.get(0).split(","))
                .collect(Collectors.toList());
        panels = new HashMap<>();
    }

    int numberOfPanelsPainted(boolean printIdentifier) throws InterruptedException {

        Panel start = new Panel(new Position(0, 0));
        panels.put(start.position, start);
        if (printIdentifier) {
            start.color = 1;
        }
        Robot robot = new Robot(start, Direction.Up);

        ExecutorService executorService = Executors.newCachedThreadPool();
        IntcodeComputer ic = new IntcodeComputer(opcodes);
        executorService.submit(ic);
        boolean quit = false;

        while (!quit) {
            ic.getInputQueue().add(new BigInteger(String.valueOf(robot.location.color)));
            BigInteger color = ic.getOutputQueue().poll(1, TimeUnit.SECONDS);
            BigInteger turn = ic.getOutputQueue().poll(1, TimeUnit.SECONDS);
            if (color == null || turn == null) {
                quit = true;
            } else {
                robot.paintAndMove(color.intValue(), turn.intValue());
            }
        }
        log.info("DONE! Number of panels: {}", panels.size());
        if (printIdentifier) {
            printPanel();
        }
        return panels.size();
    }

    void printPanel() {
        // find size of message
        int minX = panels.keySet().stream().mapToInt(Position::getX).min().orElse(0);
        int minY = panels.keySet().stream().mapToInt(Position::getY).min().orElse(0);
        int maxX = panels.keySet().stream().mapToInt(Position::getX).max().orElse(0);
        int maxY = panels.keySet().stream().mapToInt(Position::getY).max().orElse(0);

        for (int y = minY; y <= maxY; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = minX; x < maxX; x++) {
                Position pos = new Position(x, y);
                if (panels.containsKey(pos)) {
                    sb.append(panels.get(pos).color == 0 ? '.' : '#');
                } else {
                    sb.append('.');
                }
            }
            System.out.println(sb.toString());
        }
    }
}
