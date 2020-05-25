package aoc.aoc2016;

import aoc.Position;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day22GridComputing {
    Set<Node> nodes = new HashSet<>();

    public Day22GridComputing(@NotNull List<String> inputLines) {

        // /dev/grid/node-x0-y0     94T   73T    21T   77%
        Pattern pattern = Pattern.compile("^/dev/grid/node-x(\\d+)-y(\\d+)\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)%$");

        for (String row : inputLines) {
            Matcher matcher = pattern.matcher(row);
            if (matcher.find()) {
                nodes.add(new Node(Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4)),
                        Integer.parseInt(matcher.group(5)),
                        Integer.parseInt(matcher.group(6))));
            }
        }
    }

    int viableNodes() {
        int viableNodes = 0;

        for (Node a : nodes) {
            for (Node b : nodes) {
                if (a.used != 0 && a != b && a.used <= b.avail) {
                    viableNodes++;
                }
            }
        }
        return viableNodes;
    }

    int fewestSteps() {
        return 0;
    }

    static class Node {
        Position pos;
        int size;
        int used;
        int avail;
        int use;

        public Node(int x, int y, int size, int used, int avail, int use) {
            this.pos = new Position(x, y);
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
