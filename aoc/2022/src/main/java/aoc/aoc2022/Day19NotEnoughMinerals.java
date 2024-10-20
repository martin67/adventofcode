package aoc.aoc2022;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static aoc.aoc2022.Day19NotEnoughMinerals.Mineral.*;

@Slf4j
public class Day19NotEnoughMinerals {
    final List<Blueprint> blueprints = new ArrayList<>();

    Day19NotEnoughMinerals(List<String> inputLines) {
        var pattern = Pattern.compile("Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.");

        for (String line : inputLines) {
            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                Blueprint blueprint = new Blueprint();
                blueprints.add(blueprint);
                blueprint.oreRobot = new Robot(Ore);
                blueprint.oreRobot.buildCost.put(Ore, Integer.parseInt(matcher.group(2)));
                blueprint.clayRobot = new Robot(Clay);
                blueprint.clayRobot.buildCost.put(Ore, Integer.parseInt(matcher.group(3)));
                blueprint.obsidianRobot = new Robot(Obsidian);
                blueprint.obsidianRobot.buildCost.put(Ore, Integer.parseInt(matcher.group(4)));
                blueprint.obsidianRobot.buildCost.put(Clay, Integer.parseInt(matcher.group(5)));
                blueprint.geodeRobot = new Robot(Geode);
                blueprint.geodeRobot.buildCost.put(Ore, Integer.parseInt(matcher.group(6)));
                blueprint.geodeRobot.buildCost.put(Obsidian, Integer.parseInt(matcher.group(7)));
            }
        }
    }

    int problem1() {
        for (var blueprint : blueprints) {
            Map<Mineral, Integer> minerals = new HashMap<>();
            for (var mineral : Mineral.values()) {
                minerals.put(mineral, 0);
            }
            Map<Mineral, Integer> robots = new HashMap<>();
            for (var mineral : Mineral.values()) {
                robots.put(mineral, 0);
            }
            robots.put(Ore, 1);

            for (int i = 0; i < 24; i++) {
                log.info("== Minute {} ==", i + 1);
                int oreRobots = 0;
                int clayRobots = 0;
                int obsidianRobots = 0;
                int geodeRobots = 0;

                Map<Mineral, Integer> additionalRobots = new HashMap<>();

                // Assume we can only build one robot per minute
                // First build geode robot if possible
                if (blueprint.geodeRobot.canBuild(minerals)) {
                    minerals = blueprint.geodeRobot.build(minerals);
                    additionalRobots.put(Geode, 1);
                } else if (blueprint.obsidianRobot.canBuild(minerals)) {
                    minerals = blueprint.obsidianRobot.build(minerals);
                    additionalRobots.put(Obsidian, 1);
                } else if (blueprint.clayRobot.canBuild(minerals)) {
                    minerals = blueprint.clayRobot.build(minerals);
                    additionalRobots.put(Clay, 1);
                } else if (blueprint.oreRobot.canBuild(minerals)) {
                    minerals = blueprint.oreRobot.build(minerals);
                    additionalRobots.put(Ore, 1);
                }

                // Produce
                for (var mineral : Mineral.values()) {
                    if (robots.get(mineral) > 0) {
                        log.info("{} {}-collecting robot collects {} {}; you now have {} {}.",
                                robots.get(mineral), mineral, robots.get(mineral), mineral, minerals.get(mineral) + robots.get(mineral), mineral);
                        minerals.put(mineral, minerals.get(mineral) + robots.get(mineral));
                    }
                }

                for (var mineral : Mineral.values()) {
                    if (additionalRobots.containsKey(mineral)) {
                        robots.put(mineral, robots.get(mineral) + additionalRobots.get(mineral));
                    }
                }
            }

        }

        return 0;
    }

    int problem2() {
        return 0;
    }

    enum Mineral {
        Ore, Clay, Obsidian, Geode
    }

    static class Blueprint {
        Robot oreRobot;
        Robot clayRobot;
        Robot obsidianRobot;
        Robot geodeRobot;
    }

    static class Robot {
        final Mineral produces;
        final Map<Mineral, Integer> buildCost = new HashMap<>();

        Robot(Mineral produces) {
            this.produces = produces;
        }

        boolean canBuild(Map<Mineral, Integer> minerals) {
            boolean canBuild = true;
            for (var mineral : buildCost.keySet()) {
                if (buildCost.get(mineral) > minerals.get(mineral)) {
                    canBuild = false;
                }
            }
            return canBuild;
        }

        Map<Mineral, Integer> build(Map<Mineral, Integer> minerals) {
            for (var mineral : buildCost.keySet()) {
                minerals.put(mineral, minerals.get(mineral) - buildCost.get(mineral));
            }
            return minerals;
        }

    }
}
