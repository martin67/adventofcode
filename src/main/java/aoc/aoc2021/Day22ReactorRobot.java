package aoc.aoc2021;

import aoc.SpacePosition;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day22ReactorRobot {

    Set<SpacePosition> reactor = new HashSet<>();
    List<Step> steps = new ArrayList<>();

    public Day22ReactorRobot(List<String> inputLines) {
        Pattern pattern = Pattern.compile("(\\w+) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");
        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                steps.add(new Step(
                        matcher.group(1).equals("on"),
                        new Cuboid(
                                new SpacePosition(Integer.parseInt(matcher.group(2)),
                                        Integer.parseInt(matcher.group(4)),
                                        Integer.parseInt(matcher.group(6))),
                                new SpacePosition(Integer.parseInt(matcher.group(3)),
                                        Integer.parseInt(matcher.group(5)),
                                        Integer.parseInt(matcher.group(7)))

                        )
                ));
            }
        }
    }

    int problem1() {
        for (Step step : steps) {
            if (!step.cuboid.invalid()) {
                for (int x = step.cuboid.lowCorner.getX(); x < step.cuboid.highCorner.getX() + 1; x++) {
                    for (int y = step.cuboid.lowCorner.getY(); y < step.cuboid.highCorner.getY() + 1; y++) {
                        for (int z = step.cuboid.lowCorner.getZ(); z < step.cuboid.highCorner.getZ() + 1; z++) {
                            SpacePosition pos = new SpacePosition(x, y, z);
                            if (step.on) {
                                reactor.add(pos);
                            } else {
                                reactor.remove(pos);
                            }
                        }
                    }
                }
            }

        }
        return reactor.size();
    }

    int problem2() {
        return 0;
    }

    static class Step {
        boolean on;
        Cuboid cuboid;

        public Step(boolean on, Cuboid cuboid) {
            this.on = on;
            this.cuboid = cuboid;
        }
    }

    static class Cuboid {
        SpacePosition lowCorner;
        SpacePosition highCorner;

        public Cuboid(SpacePosition lowCorner, SpacePosition highCorner) {
            this.lowCorner = lowCorner;
            this.highCorner = highCorner;
        }

        boolean invalid() {
            return (lowCorner.getX() < -50 || highCorner.getX() > 50 ||
                    lowCorner.getY() < -50 || highCorner.getY() > 50 ||
                    lowCorner.getZ() < -50 || highCorner.getZ() > 50);
        }
    }
}
