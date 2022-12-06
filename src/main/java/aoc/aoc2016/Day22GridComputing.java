package aoc.aoc2016;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day22GridComputing {
    private final Map<Position, Node> nodes = new HashMap<>();
    private int maxX = 0;
    private int maxY = 0;

    public Day22GridComputing(@NotNull List<String> inputLines) {

        // /dev/grid/node-x0-y0     94T   73T    21T   77%
        Pattern pattern = Pattern.compile("^/dev/grid/node-x(\\d+)-y(\\d+)\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)%$");

        for (String row : inputLines) {
            Matcher matcher = pattern.matcher(row);
            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                if (x > maxX) {
                    maxX = x;
                }
                if (y > maxY) {
                    maxY = y;
                }
                Position pos = new Position(x, y);
                int size = Integer.parseInt(matcher.group(3));
                int used = Integer.parseInt(matcher.group(4));
                int avail = Integer.parseInt(matcher.group(5));
                int use = Integer.parseInt(matcher.group(6));

                nodes.put(pos, new Node(pos, size, used, avail, use));

            }
        }
        log.info("Read nodes ({}x{})", maxX + 1, maxY + 1);
    }

    int viableNodes() {
        int viableNodes = 0;

        for (Node a : nodes.values()) {
            for (Node b : nodes.values()) {
                if (a.used != 0 && a != b && a.used <= b.avail) {
                    viableNodes++;
                }
            }
        }
        return viableNodes;
    }

    int fewestSteps() {
        printNodes();
        Node emptyNode = nodes.values().stream().filter(n -> n.used == 0).findFirst().orElse(null);
        log.info("Empty node: {}", emptyNode);
        Position goalNodePosition = new Position(maxX, 0);
        // Cant' go directly as there is a row of nodes with more than 92T used (won't fit in the empty node)
        Node fullNode = nodes.values().stream().filter(n -> n.used > 0).findFirst().orElse(null);
        log.info("Full nodes at: {}", fullNode);
        // full rows at y = 10, from x = 5. So the empty node must first pass through pos 4,10

        int distance;
        if (maxX == 2) {
            distance = emptyNode.pos.distance(goalNodePosition.adjacent(Direction.Left));
        } else {
            Position halfway = new Position(4, 10);
            distance = emptyNode.pos.distance(halfway);
            distance += halfway.distance(goalNodePosition.adjacent(Direction.Left));
        }
        log.info("Distance to left of goal node: {}", distance);

        int distanceToStart = goalNodePosition.getX() - 1;
        log.info("Distance to start: {}", distanceToStart);
        // takes 5 steps to move goal one position to the left
        int totalNumberOfSteps = distance + distanceToStart * 5;
        // and one final step to get to the start
        totalNumberOfSteps++;
        log.info("Total number of steps: {}", totalNumberOfSteps);
        // assume that the quickest is to move in a direct line?
        return totalNumberOfSteps;
    }

    void printNodes() {
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                Node node = nodes.get(new Position(x, y));
                if (node.used == 0) {
                    System.out.print('_');
                } else if (node.used > 92) {
                    System.out.print('#');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }
    }

    static class Node {
        final Position pos;
        final int size;
        final int used;
        final int avail;
        final int use;

        public Node(Position pos, int size, int used, int avail, int use) {
            this.pos = pos;
            this.size = size;
            this.used = used;
            this.avail = avail;
            this.use = use;
        }

        @Override
        public String toString() {
            return "Node [" + pos.getX() + "," + pos.getY() + "] " + used + "T/" + size + "T";
        }
    }
}
