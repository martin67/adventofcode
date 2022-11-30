package aoc.aoc2020;

import aoc.Direction;
import aoc.Position;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static aoc.Direction.*;

@Slf4j
public class Day20JurassicJigsaw {
    private final Graph<Integer, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    final Set<Tile> tiles = new HashSet<>();

    public Day20JurassicJigsaw(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^Tile (\\d+):$");

        Tile tile = null;
        int x;
        int y = 0;

        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                tile = new Tile(Integer.parseInt(matcher.group(1)), 10, 10);
                tiles.add(tile);
                y = 0;
            } else if (line.startsWith(".") || line.startsWith("#")) {
                x = 0;
                for (char c : line.toCharArray()) {
                    if (c == '#') {
                        tile.positions.add(new Position(x, y));
                    }
                    x++;
                }
                y++;
            }
        }

        log.info("Tile size, width: {}, height: {}", tile.width, tile.height);
        log.info("Number of tiles: {}", tiles.size());
    }

    long problem1() {

        Map<String, Integer> freq = new HashMap<>();
        for (Tile tile : tiles) {
            for (String edge : tile.allEdges()) {
                freq.putIfAbsent(edge, 0);
                freq.put(edge, freq.get(edge) + 1);
            }
        }

        long value = 1;
        for (Tile tile : tiles) {
            for (String edge : tile.allEdges()) {
                if (freq.get(edge) == 1) {
                    log.debug("Tile {}, external edge {}", tile.id, edge);
                    tile.externalEdges.add(edge);
                }
            }
            log.debug("Tile {}, total external edges {}", tile.id, tile.externalEdges.size());
            // Tiles with four external edges are corners. It is two, but they are also reversed
            if (tile.isCorner()) {
                value *= tile.id;
            }
        }

        return value;
    }

    long problem2() throws Exception {
        // Find all edges
        problem1();

        // Find first corner
        Tile firstCorner = tiles.stream().filter(Tile::isCorner).findFirst().orElseThrow(() -> new Exception("hej"));
        // Find correct orientation
        log.info("Found first corner on tile {}", firstCorner);
        log.debug("External edges: {}", firstCorner.externalEdges);
        log.debug("Internal edges: {}", firstCorner.internalEdges());


        Map<Position, Tile> bigMap = new HashMap<>();
        // start in upper left corner
        // which alternative has external edges on left and top? Two will match, pick the first
        Tile start = firstCorner.alternatives().stream()
                .filter(t -> firstCorner.externalEdges.contains(t.edge(Up)))
                .filter(t -> firstCorner.externalEdges.contains(t.edge(Left)))
                .findFirst().get();

        log.debug("Start tile (0,0) is {}", start);
        bigMap.put(new Position(0, 0), start);


        // Go down
        Tile previous = start;
        for (int y = 1; y < Math.sqrt(tiles.size()); y++) {
            log.debug("Going down, checking for edge {}", previous.edge(Down));
            boolean foundIt = false;
            for (Tile tile : tiles) {
                if (tile.id != previous.id) {
                    for (Tile alt : tile.alternatives()) {
                        if (alt.edge(Up).equals(previous.edge(Down))) {
                            log.debug("Next down: {}", alt);
                            bigMap.put(new Position(0, y), alt);
                            previous = alt;
                            foundIt = true;
                            break;
                        }
                    }
                }
                if (foundIt) {
                    break;
                }
            }
        }

        // Go right
        previous = start;
        for (int x = 1; x < Math.sqrt(tiles.size()); x++) {
            log.debug("Going right, checking for edge {}", previous.edge(Right));
            boolean foundIt = false;
            for (Tile tile : tiles) {
                if (tile.id != previous.id) {
                    for (Tile alt : tile.alternatives()) {
                        if (alt.edge(Left).equals(previous.edge(Right))) {
                            log.debug("Next right: {}", alt);
                            bigMap.put(new Position(x, 0), alt);
                            previous = alt;
                            foundIt = true;
                            break;
                        }
                    }
                }
                if (foundIt) {
                    break;
                }
            }
        }

        // then go from left to right
        for (int y = 1; y < Math.sqrt(tiles.size()); y++) {
            for (int x = 1; x < Math.sqrt(tiles.size()); x++) {
                Tile leftTile = bigMap.get(new Position(x - 1, y));
                log.debug("Row {}, column {}, checking for edge {}", y, x, leftTile.edge(Right));

                for (Tile tile : tiles) {
                    if (tile.id != leftTile.id) {
                        for (Tile alt : tile.alternatives()) {
                            if (alt.edge(Left).equals(leftTile.edge(Right))) {
                                log.debug("Next right: {}", alt);
                                bigMap.put(new Position(x, y), alt);
                                break;
                            }
                        }
                    }
                }
            }
        }

        bigMap.values().forEach(Tile::stripBorder);

        int bigMapsize = 8 * (int) Math.sqrt(tiles.size());
        Tile bigTile = new Tile(1, bigMapsize, bigMapsize);

        for (int y = 0; y < Math.sqrt(tiles.size()); y++) {
            for (int x = 0; x < Math.sqrt(tiles.size()); x++) {
                for (Position position : bigMap.get(new Position(x, y)).positions) {
                    bigTile.positions.add(new Position((position.getX() - 1) + x * 8, (position.getY() - 1) + y * 8));
                }
            }
        }
        //bigTile.print();

        // Find sea monster
        Tile seaMonsterTile = null;
        Set<Set<Position>> seaMonsters = new HashSet<>();
        for (Tile bt : bigTile.alternatives()) {
            for (int y = 0; y < bigMapsize; y++) {
                for (int x = 0; x < bigMapsize; x++) {
                    Set<Position> seaMonster = createSeaMonster(x, y);
                    if (bt.positions.containsAll(seaMonster)) {
                        log.info("Sea Monster found at {},{}", x, y);
                        seaMonsterTile = bt;
                        seaMonsters.add(seaMonster);
                    }
                }
            }
        }

        // Strip Sea Monsters
        for (Set<Position> seaMonster : seaMonsters) {
            seaMonsterTile.positions.removeAll(seaMonster);
        }

        return seaMonsterTile.positions.size();
    }

    Set<Position> createSeaMonster(int xstart, int ystart) {
        String[] seaMonsterPattern = {"                  # ",
                "#    ##    ##    ###",
                " #  #  #  #  #  #   "};

        Set<Position> seaMonster = new HashSet<>();
        int y = ystart;
        for (String s : seaMonsterPattern) {
            int x = xstart;
            for (char c : s.toCharArray()) {
                if (c == '#') {
                    seaMonster.add(new Position(x, y));
                }
                x++;
            }
            y++;
        }
        return seaMonster;
    }

    static class Tile {
        final int id;
        final int width;
        final int height;
        final Set<Position> positions = new HashSet<>();

        final Set<String> externalEdges = new HashSet<>();

        public Tile(int id, int width, int height) {
            this.id = id;
            this.width = width;
            this.height = height;
        }

        Tile rotate(Direction dir) {
            // 3,1 -> 1,6 -> 6,8 -> 8,3
            Tile t = new Tile(id, width, height);
            switch (dir) {
                case Left:
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            if (positions.contains(new Position(x, y))) {
                                t.positions.add(new Position(y, height - x - 1));
                            }
                        }
                    }
                    break;
                case Right:
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            if (positions.contains(new Position(x, y))) {
                                t.positions.add(new Position(width - y - 1, x));
                            }
                        }
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + dir);
            }
            return t;
        }

        Tile flip(Direction dir) {
            Tile t = new Tile(id, width, height);
            switch (dir) {
                case Up:
                case Down:
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            if (positions.contains(new Position(x, y))) {
                                t.positions.add(new Position(x, height - y - 1));
                            }
                        }
                    }
                    break;
                case Left:
                case Right:
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            if (positions.contains(new Position(x, y))) {
                                t.positions.add(new Position(width - x - 1, y));
                            }
                        }
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + dir);
            }
            return t;
        }

        Set<Tile> alternatives() {
            Set<Tile> alternatives = new HashSet<>();
            alternatives.add(this);
            alternatives.add(rotate(Left));
            alternatives.add(rotate(Left).rotate(Left));
            alternatives.add(rotate(Left).rotate(Left).rotate(Left));
            alternatives.add(flip(Up));
            alternatives.add(rotate(Left).flip(Up));
            alternatives.add(rotate(Left).rotate(Left).flip(Up));
            alternatives.add(rotate(Left).rotate(Left).rotate(Left).flip(Up));
            return alternatives;
        }

        Set<String> allEdges() {
            Set<String> allEdges = new HashSet<>();
            allEdges.add(edge(Up));
            allEdges.add(edge(Down));
            allEdges.add(edge(Left));
            allEdges.add(edge(Right));
            allEdges.add(reverse(edge(Up)));
            allEdges.add(reverse(edge(Down)));
            allEdges.add(reverse(edge(Left)));
            allEdges.add(reverse(edge(Right)));

            return allEdges;
        }

        Set<String> internalEdges() {
            Set<String> internalEdges = allEdges();
            internalEdges.removeAll(externalEdges);
            return internalEdges;
        }

        public boolean isEdge() {
            return externalEdges.size() == 2;
        }

        public boolean isCorner() {
            return externalEdges.size() == 4;
        }

        String reverse(String in) {
            StringBuilder sb = new StringBuilder(in);
            return sb.reverse().toString();
        }

        public String edge(Direction dir) {
            StringBuilder sb = new StringBuilder();
            switch (dir) {
                case Up:
                    for (int x = 0; x < width; x++) {
                        Position p = new Position(x, 0);
                        if (positions.contains(p)) {
                            sb.append('#');
                        } else {
                            sb.append('.');
                        }
                    }
                    break;
                case Down:
                    for (int x = 0; x < width; x++) {
                        Position p = new Position(x, height - 1);
                        if (positions.contains(p)) {
                            sb.append('#');
                        } else {
                            sb.append('.');
                        }
                    }
                    break;
                case Left:
                    for (int y = 0; y < height; y++) {
                        Position p = new Position(0, y);
                        if (positions.contains(p)) {
                            sb.append('#');
                        } else {
                            sb.append('.');
                        }
                    }
                    break;
                case Right:
                    for (int y = 0; y < height; y++) {
                        Position p = new Position(width - 1, y);
                        if (positions.contains(p)) {
                            sb.append('#');
                        } else {
                            sb.append('.');
                        }
                    }
                    break;
            }
            return sb.toString();
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
                System.out.println(sb);
            }
            System.out.println();
        }

        void stripBorder() {
            positions.removeIf(p -> (p.getX() == 0 || p.getX() == 9 || p.getY() == 0 || p.getY() == 9));
        }


        @Override
        public String toString() {
            return "Tile{" +
                    "id=" + id +
                    '}';
        }
    }
}
