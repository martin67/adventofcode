package aoc2019;

import com.google.common.collect.Sets;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day12TheNBodyProblem {

    @Data
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

    @Data
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

    final Set<Moon> moonSet;
    List<Moon> moonList;
    List<Moon> initialMoonList;

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

    int stepsToOriginalState() {
        Set<Set<Moon>> moonPairs = Sets.combinations(moonSet, 2);
//        Set<String> previousStates = new HashSet<>();
//        previousStates.add(moons.toString());
        String startState = moonSet.toString();
        Set<Integer> diffs = new HashSet<>();

        boolean quit = false;
        int steps = 0;
        int lastStep = 0;

        while (!quit) {

            for (Set<Moon> moonPair : moonPairs) {
                List<Moon> moonList = new ArrayList<>(moonPair);
                moonList.get(0).applyGravity(moonList.get(1));
            }
            moonSet.forEach(Moon::applyVelocity);

            steps++;

//            if (moonSet.hashCode() == moonSetStart) {
//                quit = true;
//            }

            if (moonSet.toString().equals(startState)) {
                quit = true;
            }

            int moon = 0;
            if (moonList.get(moon).position.equals(initialMoonList.get(moon).position)) {
                log.info("Step: {} - diff {} - {}", steps, steps - lastStep, moonList.get(moon));
                if (diffs.contains(steps-lastStep)) {
                    log.info("Found cycle: {} at {}", steps-lastStep, steps);
                } else {
                    diffs.add(steps - lastStep);
                }
                lastStep = steps;
            }

            if (steps % 1000000 == 0) {
                log.info("Step: {}", steps);
            }

//            String state = moons.toString();
//            if (previousStates.contains(state)) {
//                quit = true;
//            } else {
//                previousStates.add(state);
//                if (steps % 1000000 == 0) {
//                    log.info("Step: {}", steps);
//                }
//            }
        }
        return steps;
    }
}


// Different cycles: for example1:
//<x=-1, y=0, z=2>
//<x=2, y=-10, z=-7>
//<x=4, y=-8, z=8>
//<x=3, y=5, z=-1>

// moon0: 462 462 462 462 462 462                          - cycle: 462
// moon1: 1125 261 261 1125                                - cycle: 1386
// moon2: 308 308 154 308 308 308 308 154 308 308          - cycle: 1386
// moon3: 261 201 201 261 261 201 201 261 261 201 201 261  - cycle: 462

// moons are going back and forth in a pattern
// 2772 :

//  <x=13, y=9, z=5>
//  <x=8, y=14, z=-2>
//  <x=-5, y=4, z=11>
//  <x=2, y=-6, z=1