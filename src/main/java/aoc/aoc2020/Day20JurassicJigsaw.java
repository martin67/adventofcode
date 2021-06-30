package aoc.aoc2020;

import aoc.Direction;
import aoc.Position;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static aoc.Direction.*;

@Slf4j
public class Day20JurassicJigsaw {
    Map<Integer, Tile> tiles = new HashMap<>();
    int width = 0;
    int height = 0;

    public Day20JurassicJigsaw(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^Tile (\\d+):$");

        Tile tile = null;
        int x;
        int y = 0;

        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                int id = Integer.parseInt(matcher.group(1));
                tile = new Tile(id);
                tiles.put(id, tile);
                y = 0;
            } else if (line.startsWith(".") || line.startsWith("#")) {
                x = 0;
                for (char c : line.toCharArray()) {
                    if (c == '#') {
                        tile.initialPositions.add(new Position(x, y));
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

        for (Tile t : tiles.values()) {
            for (int w = 0; w < width; w++) {

                if (t.initialPositions.contains(new Position(w, 0))) {
                    t.top += Math.pow(2, width - w - 1);
                }
                if (t.initialPositions.contains(new Position(w, height - 1))) {
                    t.bottom += Math.pow(2, width - w - 1);
                }
            }
            for (int h = 0; h < height; h++) {
                if (t.initialPositions.contains(new Position(0, h))) {
                    t.left += Math.pow(2, height - h - 1);
                }
                if (t.initialPositions.contains(new Position(width - 1, h))) {
                    t.right += Math.pow(2, height - h - 1);
                }
            }
        }

        log.info("Tile size, width: {}, height: {}", width, height);
        log.info("Number of tiles: {}", tiles.size());
    }

    long problem1() {

        Map<Integer, Integer> freq = new HashMap<>();
        for (Tile tile : tiles.values()) {

            for (Tile alt : tile.alternatives()) {
                freq.putIfAbsent(alt.top, 0);
                freq.put(alt.top, freq.get(alt.top) + 1);
                freq.putIfAbsent(alt.bottom, 0);
                freq.put(alt.bottom, freq.get(alt.bottom) + 1);
                freq.putIfAbsent(alt.left, 0);
                freq.put(alt.left, freq.get(alt.left) + 1);
                freq.putIfAbsent(alt.right, 0);
                freq.put(alt.right, freq.get(alt.right) + 1);
            }
        }

        long value = 1;
        for (Tile tile : tiles.values()) {
            for (int edge : tile.allEdges()) {
                if (freq.get(edge) == 2) {
                    log.debug("Tile {}, external edge {}", tile.id, edge);
                    tile.externalEdges++;
                }
            }
            log.info("Tile {}, total external edges {}", tile.id, tile.externalEdges);
            // Tiles with two external edges are corners
            if (tile.externalEdges == 2) {
                value *= tile.id;
            }
        }

        return value;
    }


    static class Tile {
        int id;
        Set<Position> initialPositions = new HashSet<>();
        int top;
        int bottom;
        int left;
        int right;
        int externalEdges;

        public Tile(int id) {
            this.id = id;
        }

        public Tile(int id, int top, int bottom, int left, int right) {
            this.id = id;
            this.top = top;
            this.bottom = bottom;
            this.left = left;
            this.right = right;
        }

        Tile rotate(Direction dir) {
            Tile t;
            switch (dir) {
                case Left:
                    t = new Tile(id, right, left, top, bottom);
                    break;
                case Right:
                    t = new Tile(id, left, right, bottom, top);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + dir);
            }
            return t;
        }

        Tile flip(Direction dir) {
            Tile t;
            switch (dir) {
                case Up:
                case Down:
                    t = new Tile(id, bottom, top, flipNumber(left), flipNumber(right));
                    break;
                case Left:
                case Right:
                    t = new Tile(id, flipNumber(top), flipNumber(bottom), right, left);
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
            alternatives.add(rotate(Right));
            alternatives.add(rotate(Left).rotate(Left));
            alternatives.add(flip(Up));
            alternatives.add(flip(Left));
            alternatives.add(rotate(Left).flip(Up));
            alternatives.add(rotate(Left).flip(Left));
            return alternatives;
        }

        Set<Integer> allEdges() {
            Set<Integer> allEdges = new HashSet<>();
            allEdges.add(top);
            allEdges.add(bottom);
            allEdges.add(left);
            allEdges.add(right);
            allEdges.add(flipNumber(top));
            allEdges.add(flipNumber(bottom));
            allEdges.add(flipNumber(left));
            allEdges.add(flipNumber(right));
            return allEdges;
        }

        private int flipNumber(int i) {
            String s = StringUtils.leftPad(Integer.toBinaryString(i), 10, '0');
            s = StringUtils.reverse(s);
            return Integer.parseInt(s, 2);
        }

        @Override
        public String toString() {
            return "Tile{" +
                    "id=" + id +
                    ", top=" + top +
                    ", bottom=" + bottom +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }
}
