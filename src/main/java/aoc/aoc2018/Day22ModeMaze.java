package aoc.aoc2018;

import aoc.Position;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day22ModeMaze {

    enum Type {Rocky, Wet, Narrow}

    static class Region {
        final Position position;
        int geologicIndex;
        int erosionLevel;
        Type type;
        Set<Node> nodeSet = new HashSet<>();

        Region(Position position) {
            this.position = position;
        }
    }

    enum Tool {Torch, Gear, Neither}

    static class Node {
        final Region region;
        Tool toolUsed;

        Node(Region region) {
            this.region = region;
        }

        @Override   // [1,2]/Rocky/Gear
        public String toString() {
            return "[" + region.position.getX() + "," + region.position.getY() + "]/" + region.type + "/" + toolUsed;
        }
    }


    private final Map<Position, Region> cave = new HashMap<>();
    private final Set<Node> nodes = new HashSet<>();
    private final Position mouth;
    private final Position target;
    private final Position maxCave;

    private Graph<String, DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);


    public Day22ModeMaze(int depth, Position target) {
        this.mouth = new Position(0, 0);
        this.target = target;
        int edgePadding = 100;
        this.maxCave = new Position(target.getX() + edgePadding, target.getY() + edgePadding);
        initCave(depth, target, maxCave);
    }

    private void initCave(int depth, Position target, Position maxCave) {

        for (int x = 0; x < maxCave.getX(); x++) {
            for (int y = 0; y < maxCave.getY(); y++) {
                Region r = new Region(new Position(x, y));

                if ((x == 0 && y == 0) || ((x == target.getX() && y == target.getY()))) {
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
        System.out.println("Creating nodes");
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

            r.nodeSet.add(n1);
            r.nodeSet.add(n2);

            g.addVertex(n1.toString());
            g.addVertex(n2.toString());
        }

        // then setup the edges
        System.out.println("Creating edges");
        for (Node n : nodes) {
            for (Position position : n.region.position.allAdjacent()) {
                if (cave.containsKey(position)) {
                    for (Node targetNode : cave.get(position).nodeSet) {
                        int time = computeTime(n, targetNode);
                        if (time > 0) {
//                            System.out.printf("Adding connections from: %s/%s/%s to %s/%s/%s, time %d\n",
//                                    n.region.position, n.region.type, n.toolUsed,
//                                    targetNode.region.position, targetNode.region.type, targetNode.toolUsed, time);
                            DefaultWeightedEdge e = g.addEdge(n.toString(), targetNode.toString());
                            // returns null if the edge already exist
                            if (e != null) {
                                g.setEdgeWeight(e, time);
                            }
                        }
                    }
                }
            }
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
            // skip the case of switching tools between to regions of same type
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
        for (int y = 0; y < maxCave.getY(); y++) {
            for (int x = 0; x < maxCave.getX(); x++) {
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
            if (r.position.getX() <= target.getX() && r.position.getY() <= target.getY()) {
                riskLevel += r.type.ordinal();
            }
        }
        return riskLevel;
    }

    public int fewestMinutes() {
//        printCave();
        initNodes();

        Node start = nodes.stream().filter(n -> n.region.position.equals(mouth) && n.toolUsed == Tool.Torch).findFirst().get();
        Node end = nodes.stream().filter(n -> n.region.position.equals(target) && n.toolUsed == Tool.Torch).findFirst().get();

        System.out.println("Size of cave: " + maxCave.getX() * maxCave.getY() + " (" + maxCave.getX() + "x" + maxCave.getY() + ")");
        System.out.println("Number of vertexes: " + (long) g.vertexSet().size());
        System.out.println("Number of edges: " + (long) g.edgeSet().size());

        DijkstraShortestPath<String, DefaultWeightedEdge> alg = new DijkstraShortestPath<>(g);
        GraphPath<String, DefaultWeightedEdge> iPath = alg.getPath(start.toString(), end.toString());
        return (int) iPath.getWeight();

//        EppsteinKShortestPath<String, DefaultWeightedEdge> alg = new EppsteinKShortestPath<>(g);
//        List<GraphPath<String, DefaultWeightedEdge>> iPaths = alg.getPaths(start.toString(), end.toString(), 1);
//        return (int) iPaths.get(0).getWeight();

//        BellmanFordShortestPath<String, DefaultWeightedEdge> alg = new BellmanFordShortestPath<>(g);
//        GraphPath<String, DefaultWeightedEdge> iPath = alg.getPath(start.toString(), end.toString());
//        return (int) iPath.getWeight();

//        Set<String> landmarks = new HashSet<>();
//        Node upperRight = nodes.stream().filter(n -> n.region.position.equals(new nu.hagelin.adventofcode.cal2018.Position(maxCave.getX() - 1, 0))).findFirst().get();
//        Node lowerRight = nodes.stream().filter(n -> n.region.position.equals(new nu.hagelin.adventofcode.cal2018.Position(maxCave.getX() - 1, maxCave.getY() - 1))).findFirst().get();
//        Node lowerLeft = nodes.stream().filter(n -> n.region.position.equals(new nu.hagelin.adventofcode.cal2018.Position(0, maxCave.getY() - 1))).findFirst().get();
//        landmarks.add(upperRight.toString());
//        landmarks.add(lowerRight.toString());
//        landmarks.add(lowerLeft.toString());
//        ALTAdmissibleHeuristic<String, DefaultWeightedEdge> heuristic = new ALTAdmissibleHeuristic<>(g, landmarks);
//        AStarShortestPath<String, DefaultWeightedEdge> alg = new AStarShortestPath<>(g, heuristic);
//        GraphPath<String, DefaultWeightedEdge> iPath = alg.getPath(start.toString(), end.toString());
//        return (int) iPath.getWeight();

    }
}
