package aoc.aoc2017;

import aoc.Direction;
import aoc.HexPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11HexEd {
    List<String> directions = new ArrayList<>();
    int furthestAway = 0;

    public Day11HexEd(String input) {
        directions.addAll(Arrays.asList(input.split(",")));
    }

    int fewestSteps() {
        HexPosition start = new HexPosition(0, 0);
        HexPosition position = start;
        int distance;

        for (String direction : directions) {
            switch (direction) {
                case "ne":
                    position = position.adjacent(Direction.NorthEast);
                    break;
                case "nw":
                    position = position.adjacent(Direction.NorthWest);
                    break;
                case "se":
                    position = position.adjacent(Direction.SouthEast);
                    break;
                case "sw":
                    position = position.adjacent(Direction.SouthWest);
                    break;
                case "n":
                    position = position.adjacent(Direction.North);
                    break;
                case "s":
                    position = position.adjacent(Direction.South);
                    break;
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
