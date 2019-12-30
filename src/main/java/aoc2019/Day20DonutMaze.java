package aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

@Slf4j
public class Day20DonutMaze {

    @Data
    static
    class Portal {
        String name;
        Position char1;
        Position char2;
        Position pos;
    }

    final Set<Position> map = new HashSet<>();
    Map<Position, Portal> portals = new HashMap<>();
    Position start;
    Position end;
    private final Graph<Position, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

    public Day20DonutMaze(List<String> inputLines) {
        int x;
        int y = 0;
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
        log.info("Found {} portals", portals.size());
    }

    int shortestPath() {
        for (Portal portal : portals.values()) {
            Position target = null;
            if (portal.char1.x == portal.char2.x) {
                if (map.contains(portal.char1.adjacent(Direction.Up))) {
                    target = portal.char1.adjacent(Direction.Up);
                } else if (map.contains(portal.char2.adjacent(Direction.Down))) {
                    target = portal.char2.adjacent(Direction.Down);
                } else {
                    log.error("Oops");
                }
            } else if (portal.char1.y == portal.char2.y) {
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

        // Map the portals
        log.info("Mapping portals");
        for (Portal src : portals.values()) {
            for (Portal dst : portals.values()) {
                if (src.name.equals(dst.name) && src != dst) {
                    log.info("Mapping {} to {}", src, dst);
                    graph.addEdge(src.pos, dst.pos);
                }
            }
        }

        DijkstraShortestPath<Position, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<Position, DefaultEdge> iPaths = dijkstraAlg.getPaths(start);
        return iPaths.getPath(end).getLength();
    }

    int shortestRecursivePath() {
        DijkstraShortestPath<Position, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<Position, DefaultEdge> iPaths = dijkstraAlg.getPaths(start);
        return iPaths.getPath(end).getLength();
    }
}
