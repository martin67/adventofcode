import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.ALTAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

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
        final Region region;
        Tool toolUsed;
        final Map<Node, Integer> adjacentNodes = new HashMap<>();


        Node(Region region) {
            this.region = region;
        }

        void addDestination(Node destination, int time) {
            adjacentNodes.put(destination, time);
        }

        int getNumberOfDestinations() {
            return adjacentNodes.size();
        }

        @Override   // [1,2]/Rocky/Gear
        public String toString() {
            return "[" + region.position.x + "," + region.position.y + "]/" + region.type + "/" + toolUsed;
        }
    }


    private final HashMap<Position, Region> cave = new HashMap<>();
    private final Set<Node> nodes = new HashSet<>();
    private final Position mouth;
    private final Position target;
    private final Position maxCave;

    private Graph<String, DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);


    public Day22ModeMaze(int depth, Position target) {
        this.mouth = new Position(0, 0);
        this.target = target;
        int extraEdges = 15;
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

            if (r.position.equals(mouth) || r.position.equals(target)) {
                Node n = new Node(r);
                n.toolUsed = Tool.Torch;
                nodes.add(n);

                g.addVertex(n.toString());

            } else {
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

                g.addVertex(n1.toString());
                g.addVertex(n2.toString());

            }
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
//                    System.out.printf("Adding connections from: %s/%s/%s to %s/%s/%s, time %d\n",
//                            n.region.position, n.region.type, n.toolUsed,
//                            nodeToAdd.region.position, nodeToAdd.region.type, nodeToAdd.toolUsed, time);
                    n.addDestination(nodeToAdd, time);

                    DefaultWeightedEdge e = g.addEdge(n.toString(), nodeToAdd.toString());
                    if (e != null) {
                        g.setEdgeWeight(e, time);
                    }
                }
            }
//            System.out.println();
        }
    }

    private int computeTime(Node src, Node dst) {

        // src  dst Rocky   Wet   Narrow
        // Rocky  |  g,t  |  g  |   t
        // Wet    |   g   | g,n |   n
        // Narrow |   t   |  n  |  t,n

        int time = 0;

        if (src.region.type == dst.region.type) {
            // time = (src.toolUsed == dst.toolUsed) ? 1 : 8;
            // skip the the case of switching tools between to regions of same type
            time = (src.toolUsed == dst.toolUsed) ? 1 : 0;
        } else if (src.toolUsed == dst.toolUsed) {
            time = 1;
        } else if (src.region.type == Type.Rocky && dst.region.type == Type.Wet && dst.toolUsed == Tool.Gear) {
            time = 8;
        } else if (src.region.type == Type.Rocky && dst.region.type == Type.Narrow && dst.toolUsed == Tool.Torch) {
            time = 8;
        } else if (src.region.type == Type.Wet && dst.region.type == Type.Rocky && dst.toolUsed == Tool.Gear) {
            time = 8;
        } else if (src.region.type == Type.Wet && dst.region.type == Type.Narrow && dst.toolUsed == Tool.Neither) {
            time = 8;
        } else if (src.region.type == Type.Narrow && dst.region.type == Type.Rocky && dst.toolUsed == Tool.Torch) {
            time = 8;
        } else if (src.region.type == Type.Narrow && dst.region.type == Type.Wet && dst.toolUsed == Tool.Neither) {
            time = 8;
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
        Node start = nodes.stream().filter(n -> n.region.position.equals(mouth) && n.toolUsed == Tool.Torch).findFirst().get();
        Node end = nodes.stream().filter(n -> n.region.position.equals(target) && n.toolUsed == Tool.Torch).findFirst().get();

        System.out.println("Size of cave: " + maxCave.x * maxCave.y + " (" + maxCave.x + "x" + maxCave.y + ")");
        System.out.println("Number of nodes: " + nodes.size());
        System.out.println("Number of node connections: " + nodes.stream().map(Node::getNumberOfDestinations).mapToInt(Integer::intValue).sum());
        System.out.println("Number of vertexes: " + (long) g.vertexSet().size());
        System.out.println("Number of edges: " + (long) g.edgeSet().size());


        // Benchmark

        // Algorithm    ExtraEdges  short     long      value (long)
        // Dijkstra     6           174 ms    1m 28s
        // Eppstein     6           268 ms    1m 46s
        // BellmanFord  6           179 ms    1m 45s
        // AStar        6           275 ms    1m 39s    1107

        // Dijkstra     10          210 ms    2m 4s    1102
        // Eppstein     10
        // BellmanFord  10
        // AStar        10          275 ms    2m 8s    1102

        // Dijkstra     100
        // Eppstein     100
        // BellmanFord  100
        // AStar        50          6s 56ms   50m 34s   1064

//        DijkstraShortestPath<String, DefaultWeightedEdge> alg = new DijkstraShortestPath<>(g);
//        GraphPath<String, DefaultWeightedEdge> iPath = alg.getPath(start.toString(), end.toString());
//        return (int) iPath.getWeight();

//        EppsteinKShortestPath<String, DefaultWeightedEdge> alg = new EppsteinKShortestPath<>(g);
//        List<GraphPath<String, DefaultWeightedEdge>> iPaths = alg.getPaths(start.toString(), end.toString(), 1);
//        return (int) iPaths.get(0).getWeight();

//        BellmanFordShortestPath<String, DefaultWeightedEdge> alg = new BellmanFordShortestPath<>(g);
//        GraphPath<String, DefaultWeightedEdge> iPath = alg.getPath(start.toString(), end.toString());
//        return (int) iPath.getWeight();

        Set<String> landmarks = new HashSet<>();
        Node upperRight = nodes.stream().filter(n -> n.region.position.equals(new Position(maxCave.x - 1, 0))).findFirst().get();
        Node lowerRight = nodes.stream().filter(n -> n.region.position.equals(new Position(maxCave.x - 1, maxCave.y - 1))).findFirst().get();
        Node lowerLeft = nodes.stream().filter(n -> n.region.position.equals(new Position(0, maxCave.y - 1))).findFirst().get();
        landmarks.add(upperRight.toString());
        landmarks.add(lowerRight.toString());
        landmarks.add(lowerLeft.toString());
        ALTAdmissibleHeuristic<String, DefaultWeightedEdge> heuristic = new ALTAdmissibleHeuristic<>(g, landmarks);
        AStarShortestPath<String, DefaultWeightedEdge> alg = new AStarShortestPath<>(g, heuristic);
        GraphPath<String, DefaultWeightedEdge> iPath = alg.getPath(start.toString(), end.toString());
        return (int) iPath.getWeight();
    }

}
