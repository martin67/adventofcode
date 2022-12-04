package aoc.aoc2017;

import aoc.common.Position;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day21FractalArt {
    List<Rule> rules = new ArrayList<>();
    Set<Position> grid = new HashSet<>();
    int gridSize = 3;

    public Day21FractalArt(List<String> inputLines) {
        int id = 1;
        Pattern pattern = Pattern.compile("(\\W+) => (\\W+)");
        Matcher matcher;
        for (String line : inputLines) {
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                rules.add(new Rule(id, matcher.group(1), matcher.group(2)));
                id++;
            }
        }
        // Setup initial grid, 0,0 in upper left corner
        //   .#.
        //   ..#
        //   ###
        grid.add(new Position(1, 0));
        grid.add(new Position(2, 1));
        grid.add(new Position(0, 2));
        grid.add(new Position(1, 2));
        grid.add(new Position(2, 2));
    }

    int problem1(int iterations) throws IOException {
        for (int i = 0; i < iterations; i++) {
            int size;
            Set<Position> nextGrid = new HashSet<>();
            int nextGridSize = gridSize;

            if (gridSize % 2 == 0) {
                size = 2;
            } else {
                size = 3;
            }
            log.info("Grid size: {}, square size: {}", gridSize, size);
            // Split into
            for (int x = 0; x < gridSize; x += size) {
                for (int y = 0; y < gridSize; y += size) {
                    String square = extract(x, y, size);
                    log.debug("square: {}", square);
                    for (Rule rule : rules) {
                        if (rule.possibleInputs.contains(square)) {
                            log.debug("Found match ({}) {} -> {}", rule.id, square, rule.output);
                            // add to new grid
                            nextGrid.addAll(toGrid(rule.output, x + x / size, y + y / size, size + 1));
                            break;
                        }
                    }
                }
                nextGridSize++;
            }
            gridSize = nextGridSize;
            grid = nextGrid;
            log.info("next grid size: {}", nextGridSize);
        }
        return grid.size();
    }

    String extract(int x, int y, int size) {
        StringBuilder sb = new StringBuilder();
        for (int dy = y; dy < y + size; dy++) {
            for (int dx = x; dx < x + size; dx++) {
                if (grid.contains(new Position(dx, dy))) {
                    sb.append("#");
                } else {
                    sb.append(".");
                }
            }
            sb.append("/");
        }
        return sb.substring(0, sb.length() - 1);
    }

    Set<Position> toGrid(String in, int x, int y, int size) {
        Set<Position> result = new HashSet<>();
        int stringPos = 0;
        for (int dy = y; dy < y + size; dy++) {
            for (int dx = x; dx < x + size; dx++) {
                if (in.charAt(stringPos) == '#') {
                    result.add(new Position(dx, dy));
                }
                stringPos++;
            }
            stringPos++;
        }
        return result;
    }

    static class Rule {
        int id;
        String input;
        String output;
        Set<String> possibleInputs = new HashSet<>();

        public Rule(int id, String input, String output) {
            this.id = id;
            this.input = input;
            this.output = output;

            // create all possible combinations of the input string (8 total)
            createAllPossibleInputs();
        }

        String rotate(String in) {
            if (in.length() == 5) {
                //  start 12/34:  = 12
                //                  34
                //  rotate right:   31 = 31/42
                //                  42
                return "" + in.charAt(3) + in.charAt(0) + "/" + in.charAt(4) + in.charAt(1);
            } else {
                //  start 123/456/789: = 123
                //                       456
                //                       789
                //  rotate right:        412 = 412/753/896     or   741 = 741/851/963
                //                       753                        852
                //                       896                        963
                return "" + in.charAt(8) + in.charAt(4) + in.charAt(0) + "/" +
                        in.charAt(9) + in.charAt(5) + in.charAt(1) + "/" +
                        in.charAt(10) + in.charAt(6) + in.charAt(2);
            }
        }

        String flip(String in) {
            if (in.length() == 5) {
                //  start 12/34: = 12                     12
                //                 34              or     34
                //  flip:          21 = 21/43             34 = 34/12
                //                 43                     12
                return "" + in.charAt(3) + in.charAt(4) + "/" + in.charAt(0) + in.charAt(1);
            } else {
                //  start 123/456/789:  = 123                       123
                //                        456                       456
                //                        789                       789
                //  flip:                 321 = 321/654/987     or  789 = 789/456/123
                //                        654                       456
                //                        987                       123
                return "" + in.charAt(8) + in.charAt(9) + in.charAt(10) + "/" +
                        in.charAt(4) + in.charAt(5) + in.charAt(6) + "/" +
                        in.charAt(0) + in.charAt(1) + in.charAt(2);
            }
        }

        void createAllPossibleInputs() {
            String tmp = input;
            for (int i = 0; i < 4; i++) {
                possibleInputs.add(tmp);
                tmp = rotate(tmp);
            }
            tmp = flip(input);
            for (int i = 0; i < 4; i++) {
                possibleInputs.add(tmp);
                tmp = rotate(tmp);
            }
        }
    }

}