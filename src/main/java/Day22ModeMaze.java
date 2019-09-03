import java.util.*;
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

        Node(Region region) {
            this.region = region;
        }

        private List<Node> shortestPath = new LinkedList<>();
        private Integer distance = Integer.MAX_VALUE;
        final Map<Node, Integer> adjacentNodes = new HashMap<>();

        void addDestination(Node destination, int time) {
            adjacentNodes.put(destination, time);
        }

        void setDistance(Integer distance) {
            this.distance = distance;
        }

        Integer getDistance() {
            return distance;
        }

        Map<Node, Integer> getAdjacentNodes() {
            return adjacentNodes;
        }

        List<Node> getShortestPath() {
            return shortestPath;
        }

        void setShortestPath(List<Node> shortestPath) {
            this.shortestPath = shortestPath;
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

    List<Astar.Node> astarNodeList = new ArrayList<>();
    List<Astar.Edge> astarEdgeList = new ArrayList<>();


    public Day22ModeMaze(int depth, Position target) {
        this.mouth = new Position(0, 0);
        this.target = target;
        int extraEdges = 20;
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

                astarNodeList.add(new Astar.Node(n.toString(), n.region.position.x, n.region.position.y));

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

                astarNodeList.add(new Astar.Node(n1.toString(), n1.region.position.x, n1.region.position.y));
                astarNodeList.add(new Astar.Node(n2.toString(), n2.region.position.x, n2.region.position.y));
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

                    Astar.Node start = astarNodeList.stream().filter(an -> an.toString().equals(n.toString())).findFirst().get();
                    Astar.Node end = astarNodeList.stream().filter(an -> an.toString().equals(nodeToAdd.toString())).findFirst().get();
                    astarEdgeList.add(new Astar.Edge(n.toString() + "->" + nodeToAdd.toString(), start, end, time));
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
            time = (src.toolUsed == dst.toolUsed) ? 1 : 8;
        } else if (src.toolUsed == dst.toolUsed) {
            time = 1;
        } else if (src.region.type == Type.Rocky && dst.region.type == Type.Wet && src.toolUsed == Tool.Torch) {
            time = 8;
        } else if (src.region.type == Type.Rocky && dst.region.type == Type.Narrow && src.toolUsed == Tool.Gear) {
            time = 8;
        } else if (src.region.type == Type.Wet && dst.region.type == Type.Rocky && src.toolUsed == Tool.Neither) {
            time = 8;
        } else if (src.region.type == Type.Wet && dst.region.type == Type.Narrow && src.toolUsed == Tool.Gear) {
            time = 8;
        } else if (src.region.type == Type.Narrow && dst.region.type == Type.Rocky && src.toolUsed == Tool.Neither) {
            time = 8;
        } else if (src.region.type == Type.Narrow && dst.region.type == Type.Wet && src.toolUsed == Tool.Torch) {
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
        Graph graph = new Graph();
        graph.nodes = nodes;
        Node start = nodes.stream().filter(n -> n.region.position.equals(mouth) && n.toolUsed == Tool.Torch).findFirst().get();
        Node end = nodes.stream().filter(n -> n.region.position.equals(target) && n.toolUsed == Tool.Torch).findFirst().get();
        //graph = calculateShortestPathFromSource(graph, start);

        //end.shortestPath.forEach(n -> System.out.printf("%s/%s/%s, dist %d\n", n.region.position, n.region.type, n.toolUsed, n.distance));

        // Astar version
        Astar.Graph astarGraph = new Astar.Graph(astarNodeList, astarEdgeList);
        Astar.Astar astar = new Astar.Astar(astarGraph);

        Astar.Node astarStart = astarNodeList.stream().filter(an -> an.toString().equals(start.toString())).findFirst().get();
        Astar.Node astarEnd = astarNodeList.stream().filter(an -> an.toString().equals(end.toString())).findFirst().get();

        astar.run(astarStart, astarEnd);
        return end.distance;
    }


    // from Baeldung...

    static class Graph {

        private Set<Node> nodes = new HashSet<>();

        public void addNode(Node nodeA) {
            nodes.add(nodeA);
        }
    }

    private static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }



}
