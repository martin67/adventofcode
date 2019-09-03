import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day22ModeMaze {

    enum Type {Rocky, Wet, Narrow}

    static class Region {
        final Position position;
        int geologicIndex;
        int erosionLevel;
        Type type;

        Region(Position position) {
            this.position = position;
        }
    }

    enum Tool {Torch, Gear, Neither}

    static class Node {
        Region region;
        Tool toolUsed;

        Node(Region region) {
            this.region = region;
        }

        Map<Node, Integer> adjacentNodes = new HashMap<>();

        void addDestination(Node destination, int time) {
            adjacentNodes.put(destination, time);
        }
    }


    private final HashMap<Position, Region> cave = new HashMap<>();
    private Set<Node> nodes = new HashSet<>();
    private final Position mouth;
    private final Position target;
    private final Position maxCave;


    public Day22ModeMaze(int depth, Position target) {
        this.mouth = new Position(0, 0);
        this.target = target;
        int extraEdges = 6;
        this.maxCave = new Position(target.x + extraEdges, target.y + extraEdges);
        initCave(depth, target, maxCave);
    }

    private void initCave(int depth, Position target, Position maxCave) {

        for (int x = 0; x < maxCave.x; x++) {
            for (int y = 0; y < maxCave.y; y++) {
                Region r = new Region(new Position(x, y));

                if ((x == 0 && y == 0) || ((x == target.x && y == target.y))) {
                    r.geologicIndex = 0;
                } else if (x == 0) {
                    r.geologicIndex = y * 48271;
                } else if (y == 0) {
                    r.geologicIndex = x * 16807;
                } else {
                    r.geologicIndex = cave.get(new Position(x - 1, y)).erosionLevel *
                            cave.get(new Position(x, y - 1)).erosionLevel;
                }
                r.erosionLevel = (r.geologicIndex + depth) % 20183;
                switch (r.erosionLevel % 3) {
                    case 0:
                        r.type = Type.Rocky;
                        break;
                    case 1:
                        r.type = Type.Wet;
                        break;
                    case 2:
                        r.type = Type.Narrow;
                        break;
                }
                cave.put(r.position, r);
            }
        }
    }

    private void initNodes() {
        // create nodes
        for (Region r : cave.values()) {

            Node n1 = new Node(r);
            Node n2 = new Node(r);

            switch (r.type) {
                case Rocky:
                    n1.toolUsed = Tool.Torch;
                    n2.toolUsed = Tool.Gear;
                    break;
                case Wet:
                    n1.toolUsed = Tool.Gear;
                    n2.toolUsed = Tool.Neither;
                    break;
                case Narrow:
                    n1.toolUsed = Tool.Torch;
                    n2.toolUsed = Tool.Neither;
                    break;
            }
            nodes.add(n1);
            nodes.add(n2);
        }

        // then setup the edges
        for (Node n : nodes) {

            Set<Node> nodesToAdd = new HashSet<>();
            // up
            if (n.region.position.y > 0) { // skip upper edge
                nodesToAdd.addAll(nodes.stream()
                        .filter(n2 -> n2.region.position.equals(new Position(n.region.position.x, n.region.position.y - 1)))
                        .collect(Collectors.toSet()));
            }

            // down
            if (n.region.position.y < maxCave.y) { // skip bottom edge
                nodesToAdd.addAll(nodes.stream()
                        .filter(n2 -> n2.region.position.equals(new Position(n.region.position.x, n.region.position.y + 1)))
                        .collect(Collectors.toSet()));
            }

            // left
            if (n.region.position.x > 0) { // skip left edge
                nodesToAdd.addAll(nodes.stream()
                        .filter(n2 -> n2.region.position.equals(new Position(n.region.position.x - 1, n.region.position.y)))
                        .collect(Collectors.toSet()));
            }

            // right
            if (n.region.position.x < maxCave.x) { // skip tight edge
                nodesToAdd.addAll(nodes.stream()
                        .filter(n2 -> n2.region.position.equals(new Position(n.region.position.x + 1, n.region.position.y)))
                        .collect(Collectors.toSet()));
            }

            for (Node nodeToAdd : nodesToAdd) {
                int time = computeTime(n, nodeToAdd);
                if (time > 0) {
                    System.out.printf("Adding connections from: %s/%s to %s/%s, time %d, tool used: %s\n",
                            n.region.position, n.region.type, nodeToAdd.region.position, nodeToAdd.region.type, time,
                            n.toolUsed);
                    n.addDestination(nodeToAdd, time);
                }
            }
            System.out.println();
        }
    }

    private int computeTime(Node src, Node dst) {

        // src  dst Rocky   Wet   Narrow
        // Rocky  |  g,t  |  g  |   t
        // Wet    |   g   | g,n |   n
        // Narrow |   t   |  n  |  t,n

        int time = 0;

        if (src.region.type == Type.Rocky && dst.region.type == Type.Rocky) {
            time = 1;
        } else if (src.region.type == Type.Rocky && dst.region.type == Type.Wet && src.toolUsed == Tool.Gear) {
            time = 1;
        } else if (src.region.type == Type.Rocky && dst.region.type == Type.Wet && src.toolUsed == Tool.Torch) {
            time = 8;
        } else if (src.region.type == Type.Rocky && dst.region.type == Type.Narrow && src.toolUsed == Tool.Gear) {
            time = 8;
        } else if (src.region.type == Type.Rocky && dst.region.type == Type.Narrow && src.toolUsed == Tool.Torch) {
            time = 1;
        } else if (src.region.type == Type.Wet && dst.region.type == Type.Rocky && src.toolUsed == Tool.Gear) {
            time = 1;
        } else if (src.region.type == Type.Wet && dst.region.type == Type.Rocky && src.toolUsed == Tool.Neither) {
            time = 8;
        } else if (src.region.type == Type.Wet && dst.region.type == Type.Wet) {
            time = 1;
        } else if (src.region.type == Type.Wet && dst.region.type == Type.Narrow && src.toolUsed == Tool.Gear) {
            time = 8;
        } else if (src.region.type == Type.Wet && dst.region.type == Type.Narrow && src.toolUsed == Tool.Neither) {
            time = 1;
        } else if (src.region.type == Type.Narrow && dst.region.type == Type.Rocky && src.toolUsed == Tool.Torch) {
            time = 1;
        } else if (src.region.type == Type.Narrow && dst.region.type == Type.Rocky && src.toolUsed == Tool.Neither) {
            time = 8;
        } else if (src.region.type == Type.Narrow && dst.region.type == Type.Wet && src.toolUsed == Tool.Torch) {
            time = 8;
        } else if (src.region.type == Type.Narrow && dst.region.type == Type.Wet && src.toolUsed == Tool.Neither) {
            time = 1;
        } else if (src.region.type == Type.Narrow && dst.region.type == Type.Narrow) {
            time = 1;
        }

        return time;
    }

    private void printCave() {
        for (int y = 0; y < maxCave.y; y++) {
            for (int x = 0; x < maxCave.x; x++) {
                Region r = cave.get(new Position(x, y));
                if (r.position.equals(mouth)) {
                    System.out.print('M');
                } else if (r.position.equals(target)) {
                    System.out.print('T');
                } else if (r.type == Type.Rocky) {
                    System.out.print('.');
                } else if (r.type == Type.Wet) {
                    System.out.print('=');
                } else {
                    System.out.print('|');
                }
            }
            System.out.println();
        }
    }

    public int computeRiskLevel() {
        //printCave();

        int riskLevel = 0;
        for (Region r : cave.values()) {
            if (r.position.x <= target.x && r.position.y <= target.y) {
                riskLevel += r.type.ordinal();
            }
        }
        return riskLevel;
    }

    public int fewestMinutes() {
        initNodes();
        return 0;
    }


}
