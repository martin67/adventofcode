package aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day3CrossedWires {

    @Data
    class Wire {
        List<Position> positions = new ArrayList<>();
    }

    List<Wire> wires = new ArrayList<>();

    int distanceToClosestIntersection(List<String> inputLines) {
        for (String line : inputLines) {
            Wire wire = new Wire();
            wires.add(wire);

            Position pos = new Position(0, 0);
            wire.positions.add(pos);

            for (String step : line.split(",")) {
                log.info("Step: " + step);
                String direction = step.substring(0, 1);
                int distance = Integer.parseInt(step.substring(1));
                log.info("Dir: " + direction + " dist: " + distance);

            }
        }
        return 0;
    }
}
