package aoc.aoc2019;

import aoc.common.Direction;
import aoc.common.Position;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

@Slf4j
public class Day20DonutMaze {

    private final Map<Position, Portal> portals = new HashMap<>();
    private final Map<Position, Portal> innerPortals = new HashMap<>();
    private final Map<Position, Portal> outerPortals = new HashMap<>();
    private final Graph<Position, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    private Position start;
    private Position end;

    public Day20DonutMaze(List<String> inputLines) {
        int x;
        int y = 0;
        Set<Position> map = new HashSet<>();
        for (String line : inputLines) {
            x = 0;
            for (char c : line.toCharArray()) {
                Position pos = new Position(x, y);
                if (c == '.') {
                    map.add(pos);
                    graph.addVertex(pos);
                    if (map.contains(pos.adjacent(Direction.Left))) {
                        graph.addEdge(pos, pos.adjacent(Direction.Left));
                    }
                    if (map.contains(pos.adjacent(Direction.Up))) {
                        graph.addEdge(pos, pos.adjacent(Direction.Up));
                    }
                } else if (Character.isLetter(c)) {
                    // first or second part of portal?
                    if (portals.containsKey(pos.adjacent(Direction.Left))) {
                        Portal portal = portals.get(pos.adjacent(Direction.Left));
                        portal.char2 = pos;
                        portal.name += c;
                    } else if (portals.containsKey(pos.adjacent(Direction.Up))) {
                        Portal portal = portals.get(pos.adjacent(Direction.Up));
                        portal.char2 = pos;
                        portal.name += c;
                    } else {
                        Portal portal = new Portal();
                        portal.char1 = pos;
                        portal.name = String.valueOf(c);
                        portals.put(pos, portal);
                    }
                }
                x++;
            }
            y++;
        }

        for (Portal portal : portals.values()) {
            Position target = null;
            if (portal.char1.getX() == portal.char2.getX()) {
                if (map.contains(portal.char1.adjacent(Direction.Up))) {
                    target = portal.char1.adjacent(Direction.Up);
                } else if (map.contains(portal.char2.adjacent(Direction.Down))) {
                    target = portal.char2.adjacent(Direction.Down);
                } else {
                    log.error("Oops");
                }
            } else if (portal.char1.getY() == portal.char2.getY()) {
                if (map.contains(portal.char1.adjacent(Direction.Left))) {
                    target = portal.char1.adjacent(Direction.Left);
                } else if (map.contains(portal.char2.adjacent(Direction.Right))) {
                    target = portal.char2.adjacent(Direction.Right);
                } else {
                    log.error("Oops");
                }
            } else {
                log.error("Oops");
            }
            portal.pos = target;

            if (portal.name.equals("AA")) {
                start = target;
            } else if (portal.name.equals("ZZ")) {
                end = target;
            }
        }
        log.info("Found {} portals", portals.size());
    }

    int shortestPath() {
        // Map the portals
        log.info("Mapping portals");
        for (Portal src : portals.values()) {
            for (Portal dst : portals.values()) {
                if (src.name.equals(dst.name) && src != dst) {
                    log.debug("Mapping {} to {}", src, dst);
                    graph.addEdge(src.pos, dst.pos);
                }
            }
        }

        DijkstraShortestPath<Position, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<Position, DefaultEdge> iPaths = dijkstraAlg.getPaths(start);
        return iPaths.getPath(end).getLength();
    }

    int shortestRecursivePath() {
        LayerPosition start = null;
        LayerPosition end = null;
        int layer = 1;
        Graph<LayerPosition, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        // copy graph from part 1
        for (Position vertex : this.graph.vertexSet()) {
            graph.addVertex(new LayerPosition(0, vertex));
        }
        for (DefaultEdge edge : this.graph.edgeSet()) {
            Position src = this.graph.getEdgeSource(edge);
            Position dst = this.graph.getEdgeTarget(edge);
            graph.addEdge(new LayerPosition(0, src), new LayerPosition(0, dst));
        }

        // setup portals
        int width = portals.values().stream().mapToInt(p -> p.getPos().getX())
                .max().orElseThrow(NoSuchElementException::new);
        int height = portals.values().stream().mapToInt(p -> p.getPos().getY())
                .max().orElseThrow(NoSuchElementException::new);
        for (Portal portal : portals.values()) {
            if (portal.name.equals("AA")) {
                start = new LayerPosition(0, portal.pos);
            } else if (portal.name.equals("ZZ")) {
                end = new LayerPosition(0, portal.pos);
            } else if (portal.pos.getX() == 2 || portal.pos.getX() == width || portal.pos.getY() == 2 || portal.pos.getY() == height) {
                outerPortals.put(portal.pos, portal);
            } else {
                innerPortals.put(portal.pos, portal);
            }
        }

        while (layer < 100) {
            log.info("Adding layer {} to graph", layer);
            addLayer(graph, layer);

            DijkstraShortestPath<LayerPosition, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
            assert start != null;
            ShortestPathAlgorithm.SingleSourcePaths<LayerPosition, DefaultEdge> iPaths = dijkstraAlg.getPaths(start);
            if (iPaths.getPath(end) != null) {
                log.debug("Path: {}", iPaths.getPath(end).getVertexList());
                return iPaths.getPath(end).getLength();
            } else {
                log.info("No path found");
                layer++;
            }
        }
        return 0;
    }

    void addLayer(Graph<LayerPosition, DefaultEdge> graph, int layer) {
        // copy graph from part 1
        for (Position vertex : this.graph.vertexSet()) {
            graph.addVertex(new LayerPosition(layer, vertex));
        }
        for (DefaultEdge edge : this.graph.edgeSet()) {
            Position src = this.graph.getEdgeSource(edge);
            Position dst = this.graph.getEdgeTarget(edge);
            graph.addEdge(new LayerPosition(layer, src), new LayerPosition(layer, dst));
        }

        // Connect outer portals to inner portals from previous layer
        for (Portal outerPortal : outerPortals.values()) {
            LayerPosition src = new LayerPosition(layer, outerPortal.pos);
            Portal innerPortal = innerPortals.values().stream()
                    .filter(p -> p.name.equals(outerPortal.name))
                    .findFirst().orElseThrow(NoSuchElementException::new);
            LayerPosition dst = new LayerPosition(layer - 1, innerPortal.pos);
            graph.addEdge(src, dst);
        }
    }

    @Data
    static class Portal {
        String name;
        Position char1;
        Position char2;
        Position pos;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    static class LayerPosition extends Position {
        int layer;

        public LayerPosition(int layer, Position pos) {
            super(pos);
            this.layer = layer;
        }

        @Override
        public String toString() {
            return "{" + layer +
                    ": " + this.getX() +
                    "," + this.getY() +
                    '}';
        }
    }
}
