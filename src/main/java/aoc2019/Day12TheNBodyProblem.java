package aoc2019;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12TheNBodyProblem {

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
        Position position;
        Velocity velocity;

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
    }

    Set<Moon> moons;

    public Day12TheNBodyProblem(List<String> inputLines) {
        String regexStr = "^<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>$";
        Pattern pattern = Pattern.compile(regexStr);
        moons = new HashSet<>();

        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                moons.add(new Moon(new Position(Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)))));
            }
        }
    }

    int totalEnergy(int steps) {

        Set<Set<Moon>> moonPairs = Sets.combinations(moons, 2);

        for (int step = 0; step < steps; step++) {

            for (Set<Moon> moonPair : moonPairs) {
                List<Moon> moonList = new ArrayList<>(moonPair);
                moonList.get(0).applyGravity(moonList.get(1));
            }

            moons.forEach(Moon::applyVelocity);

            //System.out.println("After " + (step +1) + " steps:");
            //moons.forEach(System.out::println);
            //System.out.println();
        }
        return moons.stream().mapToInt(Moon::totalEnergy).sum();
    }
}
