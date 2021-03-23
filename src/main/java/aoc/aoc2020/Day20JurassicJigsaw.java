package aoc.aoc2020;

import aoc.Direction;
import aoc.Position;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day20JurassicJigsaw {
    private final Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    Set<Map> maps = new HashSet<>();
    int width = 0;
    int height = 0;
    int checkId = 0;
    int numberOfConnections = 0;

    public Day20JurassicJigsaw(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^Tile (\\d+):$");

        Map map = null;
        int x;
        int y = 0;

        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                map = new Map(Integer.parseInt(matcher.group(1)));
                maps.add(map);
                y = 0;
            } else if (line.startsWith(".") || line.startsWith("#")) {
                x = 0;
                for (char c : line.toCharArray()) {
                    if (c == '#') {
                        map.positions.add(new Position(x, y));
                    }
                    x++;
                    if (x > width) {
                        width = x;
                    }
                }
                y++;
                if (y > height) {
                    height = y;
                }
            }
        }
        for (Map m : maps) {
            m.generateAlternatives();
        }
    }

    long problem1() {
        for (Map firstMap : maps) {
            for (Map otherMap : maps)
                if (otherMap != firstMap) {
                    firstMap.match(otherMap, Direction.Left);
                    firstMap.match(otherMap, Direction.Right);
                    firstMap.match(otherMap, Direction.Up);
                    firstMap.match(otherMap, Direction.Down);
                }
        }
        log.info("Number of connections {}", numberOfConnections);
        ConnectivityInspector<String, DefaultEdge> connectivityInspector = new ConnectivityInspector<>(graph);
        List<Set<String>> connectedSets = connectivityInspector.connectedSets();
        long chek2 = 0;
        log.info("Number of connected sets: {}", connectedSets.size());

        for (Set<String> set : connectedSets) {
            if (set.size() == maps.size()) {
                long checksum = 1;
                for (String vertex : set) {
                    log.info("Nod {} har {} edges", vertex, graph.degreeOf(vertex));
                    if (graph.degreeOf(vertex) == 2) {
                        log.info("Hörnnod: {}", vertex);
                        checksum *= Integer.parseInt(vertex.substring(0, 4));
                    }
                }
                log.info("--------------- Checksum: {}", checksum);
                chek2 = checksum;
            }
        }
        return chek2;
    }

    class Map {
        int id;
        Set<Position> positions = new HashSet<>();
        List<Set<Position>> alternatives;

        public Map(int id) {
            this.id = id;
        }

        Set<Position> rotate(Direction direction) {
            Set<Position> rotatedPositions = new HashSet<>();

            switch (direction) {
                case Right:
                    for (int x = 0; x < width / 2; x++) {
                        for (int y = x; y < width - x - 1; y++) {
                            boolean temp = positions.contains(new Position(x, y));

                            // Move values from right to top
                            Position p = new Position(y, width - 1 - x);
                            if (positions.contains(p)) {
                                rotatedPositions.add(new Position(x, y));
                            }

                            // Move values from bottom to right
                            p = new Position(width - 1 - x, width - 1 - y);
                            if (positions.contains(p)) {
                                rotatedPositions.add(new Position(y, width - 1 - x));
                            }

                            // Move values from left to bottom
                            p = new Position(width - 1 - y, x);
                            if (positions.contains(p)) {
                                rotatedPositions.add(new Position(width - 1 - x, width - 1 - y));
                            }

                            // Assign temp to left
                            if (temp) {
                                rotatedPositions.add(new Position(width - 1 - y, x));
                            }
                        }
                    }
                    break;
            }
            return rotatedPositions;
        }

        Set<Position> flip(Direction direction) {
            Set<Position> flippedPositions = new HashSet<>();

            switch (direction) {
                case Up:
                case Down:
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            if (positions.contains(new Position(x, y))) {
                                flippedPositions.add(new Position(x, 10 - y - 1));
                            }
                        }
                    }
                    break;

                case Left:
                case Right:
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            if (positions.contains(new Position(x, y))) {
                                flippedPositions.add(new Position(10 - x - 1, y));
                            }
                        }
                    }
                    break;
            }
            return flippedPositions;
        }

        void generateAlternatives() {
            alternatives = new ArrayList<>();
            alternatives.add(this.positions);
            alternatives.add(rotate(Direction.Right));
            Map temp = new Map(0);
            temp.positions = rotate(Direction.Right);
            alternatives.add(temp.rotate(Direction.Right));
            Map temp2 = new Map(0);
            temp2.positions = temp.rotate(Direction.Right);
            alternatives.add(temp2.rotate(Direction.Right));
            alternatives.add(flip(Direction.Up));
            alternatives.add(flip(Direction.Left));
        }

        int match(Map map, Direction direction) {

            for (Set<Position> ownPosition : alternatives) {
                for (Set<Position> oppositePosition : map.alternatives) {

                    boolean matchSide = true;
                    switch (direction) {

                        case Right:
                            for (int y = 0; y < height; y++) {
                                if (ownPosition.contains(new Position(width - 1, y)) != oppositePosition.contains(new Position(0, y))) {
                                    matchSide = false;
                                }
                            }
                            break;
                        case Left:
                            for (int y = 0; y < height; y++) {
                                if (ownPosition.contains(new Position(0, y)) != oppositePosition.contains(new Position(width - 1, y))) {
                                    matchSide = false;
                                }
                            }
                            break;
                        case Up:
                            for (int x = 0; x < width; x++) {
                                if (ownPosition.contains(new Position(x, 0)) != oppositePosition.contains(new Position(x, height - 1))) {
                                    matchSide = false;
                                }
                            }
                            break;
                        case Down:
                            for (int x = 0; x < width; x++) {
                                if (ownPosition.contains(new Position(x, height - 1)) != oppositePosition.contains(new Position(x, 0))) {
                                    matchSide = false;
                                }
                            }
                            break;
                    }
                    if (matchSide) {
                        String src = id + "-" + alternatives.indexOf(ownPosition);
                        String dst = map.id + "-" + map.alternatives.indexOf(oppositePosition);
                        numberOfConnections++;

                        //log.info("**** Map {} matched {} side with map {}", src, direction, dst);
                        if (!graph.containsVertex(src)) {
                            graph.addVertex(src);
                        }
                        if (!graph.containsVertex(dst)) {
                            graph.addVertex(dst);
                        }
                        graph.addEdge(src, dst);
                        return map.id;
                    }
                }
            }
            //log.info("No {} match for map {} with map {}", direction, id, map.id);
            return 0;
        }

        void print() {
            for (int y = 0; y < height; y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < width; x++) {
                    Position p = new Position(x, y);
                    if (positions.contains(p)) {
                        sb.append('#');
                    } else {
                        sb.append('.');
                    }
                }
                System.out.println(sb.toString());
            }
            System.out.println();
        }

        void print(Set<Position> positions) {
            for (int y = 0; y < height; y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < width; x++) {
                    Position p = new Position(x, y);
                    if (positions.contains(p)) {
                        sb.append('#');
                    } else {
                        sb.append('.');
                    }
                }
                System.out.println(sb.toString());
            }
            System.out.println();
        }
    }
}