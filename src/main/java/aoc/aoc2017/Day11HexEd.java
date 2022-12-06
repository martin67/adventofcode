package aoc.aoc2017;

import aoc.common.Direction;
import aoc.common.HexPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11HexEd {
    private final List<String> directions = new ArrayList<>();
    private int furthestAway = 0;

    public Day11HexEd(String input) {
        directions.addAll(Arrays.asList(input.split(",")));
    }

    int fewestSteps() {
        HexPosition start = new HexPosition(0, 0);
        HexPosition position = start;
        int distance;

        for (String direction : directions) {
            switch (direction) {
                case "ne" -> position = position.adjacent(Direction.NorthEast);
                case "nw" -> position = position.adjacent(Direction.NorthWest);
                case "se" -> position = position.adjacent(Direction.SouthEast);
                case "sw" -> position = position.adjacent(Direction.SouthWest);
                case "n" -> position = position.adjacent(Direction.North);
                case "s" -> position = position.adjacent(Direction.South);
            }
            distance = start.distance(position);
            if (distance > furthestAway) {
                furthestAway = distance;
            }
        }
        return start.distance(position);
    }

    int furthestAway() {
        fewestSteps();
        return furthestAway;
    }
}
