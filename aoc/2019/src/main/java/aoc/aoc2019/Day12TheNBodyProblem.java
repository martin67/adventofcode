package aoc.aoc2019;

import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static aoc.common.Math.lcm;

@Slf4j
public class Day12TheNBodyProblem {

    private final Set<Moon> moonSet;
    private final List<Moon> moonList;
    private final List<Moon> initialMoonList;

    public Day12TheNBodyProblem(List<String> inputLines) {
        String regexStr = "^<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>$";
        Pattern pattern = Pattern.compile(regexStr);
        moonSet = new HashSet<>();
        moonList = new ArrayList<>();
        initialMoonList = new ArrayList<>();

        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                Moon moon = new Moon(new Position(Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3))));
                Moon moon2 = new Moon(new Position(Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3))));
                moonSet.add(moon);
                moonList.add(moon);
                initialMoonList.add(moon2);
            }
        }
    }

    int totalEnergy(int steps) {
        Set<Set<Moon>> moonPairs = Sets.combinations(moonSet, 2);

        for (int step = 0; step < steps; step++) {

            for (Set<Moon> moonPair : moonPairs) {
                List<Moon> moonList = new ArrayList<>(moonPair);
                moonList.get(0).applyGravity(moonList.get(1));
            }

            moonSet.forEach(Moon::applyVelocity);

            //System.out.println("After " + (step +1) + " steps:");
            //moons.forEach(System.out::println);
            //System.out.println();
        }
        return moonSet.stream().mapToInt(Moon::totalEnergy).sum();
    }

    long stepsToOriginalState() {
        Set<Set<Moon>> moonPairs = Sets.combinations(moonSet, 2);
        String startState = moonSet.toString();
        boolean xFound = false;
        boolean yFound = false;
        boolean zFound = false;
        int lastX = 0;
        int lastY = 0;
        int lastZ = 0;
        int xCycle = 0;
        int yCycle = 0;
        int zCycle = 0;

        int steps = 0;

        while (!xFound || !yFound || !zFound) {

            for (Set<Moon> moonPair : moonPairs) {
                List<Moon> moonList = new ArrayList<>(moonPair);
                moonList.get(0).applyGravity(moonList.get(1));
            }
            moonSet.forEach(Moon::applyVelocity);

            steps++;

            if (!xFound && moonList.get(0).position.x == initialMoonList.get(0).position.x &&
                    moonList.get(1).position.x == initialMoonList.get(1).position.x &&
                    moonList.get(2).position.x == initialMoonList.get(2).position.x &&
                    moonList.get(3).position.x == initialMoonList.get(3).position.x) {
                if (lastX == 0) {
                    lastX = steps;
                } else {
                    if (steps > lastX + 1) {
                        xCycle = steps - lastX;
                        log.info("X cycle {}", xCycle);
                        xFound = true;
                    }
                }
            }

            if (!yFound && moonList.get(0).position.y == initialMoonList.get(0).position.y &&
                    moonList.get(1).position.y == initialMoonList.get(1).position.y &&
                    moonList.get(2).position.y == initialMoonList.get(2).position.y &&
                    moonList.get(3).position.y == initialMoonList.get(3).position.y) {
                if (lastY == 0) {
                    lastY = steps;
                } else {
                    if (steps > lastY + 1) {
                        yCycle = steps - lastY;
                        log.info("Y cycle {}", yCycle);
                        yFound = true;
                    }
                }
            }

            if (!zFound && moonList.get(0).position.z == initialMoonList.get(0).position.z &&
                    moonList.get(1).position.z == initialMoonList.get(1).position.z &&
                    moonList.get(2).position.z == initialMoonList.get(2).position.z &&
                    moonList.get(3).position.z == initialMoonList.get(3).position.z) {
                if (lastZ == 0) {
                    lastZ = steps;
                } else {
                    if (steps > lastZ + 1) {
                        zCycle = steps - lastZ;
                        log.info("Z cycle {}", zCycle);
                        zFound = true;
                    }
                }
            }
        }

        // compute lcm (least common multiplier)
        return lcm(xCycle, yCycle, zCycle);
    }

    static class Position {
        int x;
        int y;
        int z;

        public Position(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    static class Velocity {
        int x;
        int y;
        int z;

        public Velocity(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    static class Moon {
        final Position position;
        final Velocity velocity;

        public Moon(Position position) {
            this.position = position;
            this.velocity = new Velocity(0, 0, 0);
        }

        void applyGravity(Moon target) {
            if (position.x > target.position.x) {
                velocity.x--;
                target.velocity.x++;
            } else if (position.x < target.position.x) {
                velocity.x++;
                target.velocity.x--;
            }

            if (position.y > target.position.y) {
                velocity.y--;
                target.velocity.y++;
            } else if (position.y < target.position.y) {
                velocity.y++;
                target.velocity.y--;
            }

            if (position.z > target.position.z) {
                velocity.z--;
                target.velocity.z++;
            } else if (position.z < target.position.z) {
                velocity.z++;
                target.velocity.z--;
            }
        }

        void applyVelocity() {
            position.x += velocity.x;
            position.y += velocity.y;
            position.z += velocity.z;
        }

        int potentialEnergy() {
            return Math.abs(position.x) + Math.abs(position.y) + Math.abs(position.z);
        }

        int kineticEnergy() {
            return Math.abs(velocity.x) + Math.abs(velocity.y) + Math.abs(velocity.z);
        }

        int totalEnergy() {
            return potentialEnergy() * kineticEnergy();
        }

        @Override
        public String toString() {
            return String.format("pos=<x=%d, y=%d, z=%d>, vel=<x=%d, y=%d, z=%d>",
                    position.x, position.y, position.z, velocity.x, velocity.y, velocity.z);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return position.toString().equals(o.toString());
        }

        @Override
        public int hashCode() {
            return Objects.hash(toString());
        }
    }
}
