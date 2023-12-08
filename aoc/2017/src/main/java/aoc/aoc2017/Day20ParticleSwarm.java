package aoc.aoc2017;

import aoc.common.SpacePosition;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day20ParticleSwarm {
    private final List<Particle> particles = new ArrayList<>();

    public Day20ParticleSwarm(List<String> inputLines) {
        int index = 0;
        var pattern = Pattern.compile("p=<(-?\\d+),(-?\\d+),(-?\\d+)>, v=<(-?\\d+),(-?\\d+),(-?\\d+)>, a=<(-?\\d+),(-?\\d+),(-?\\d+)>");

        for (String line : inputLines) {
            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                particles.add(new Particle(index,
                        new SpacePosition(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3))),
                        new SpacePosition(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6))),
                        new SpacePosition(Integer.parseInt(matcher.group(7)), Integer.parseInt(matcher.group(8)), Integer.parseInt(matcher.group(9)))
                ));
                index++;
            }
        }
    }

    public int problem1() {
        // Lowest acceleration will be closest in the long run
        TreeMap<Integer, Set<Particle>> accelerationMap = new TreeMap<>();

        for (Particle particle : particles) {
            accelerationMap.computeIfAbsent(particle.totalAcceleration(), k -> new HashSet<>());
            accelerationMap.get(particle.totalAcceleration()).add(particle);
        }
        // Map entries are sorted by key as it is a tree map
        int shortestDistance = Integer.MAX_VALUE;
        Particle closestParticle = null;
        SpacePosition origo = new SpacePosition(0, 0, 0);
        for (Particle particle : accelerationMap.firstEntry().getValue()) {
            // select the one closest to (0,0,0)
            if (particle.distance(origo) < shortestDistance) {
                closestParticle = particle;
                shortestDistance = particle.distance(origo);
            }
        }

        return closestParticle.index;
    }

    public int problem2() {
        Set<Particle> remaining = new HashSet<>(particles);
        for (int i = 0; i < 100; i++) {

            Map<SpacePosition, Set<Particle>> collisions = new HashMap<>();
            for (Particle particle : remaining) {
                particle.move();
                collisions.computeIfAbsent(particle.position, k -> new HashSet<>());
                collisions.get(particle.position).add(particle);
            }
            remaining = collisions.values().stream()
                    .filter(s -> s.size() == 1)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
        }
        return remaining.size();
    }

    @AllArgsConstructor
    static class Particle {
        int index;
        SpacePosition position;
        SpacePosition velocity;
        SpacePosition acceleration;

        void move() {
            velocity.setX(velocity.getX() + acceleration.getX());
            velocity.setY(velocity.getY() + acceleration.getY());
            velocity.setZ(velocity.getZ() + acceleration.getZ());
            position.setX(position.getX() + velocity.getX());
            position.setY(position.getY() + velocity.getY());
            position.setZ(position.getZ() + velocity.getZ());
        }

        int totalAcceleration() {
            return Math.abs(acceleration.getX()) + Math.abs(acceleration.getY()) + Math.abs(acceleration.getZ());
        }

        int distance(SpacePosition sp) {
            return Math.abs(position.getX() - sp.getX()) + Math.abs(position.getY() - sp.getY()) + Math.abs(position.getZ() - sp.getZ());
        }
    }
}
