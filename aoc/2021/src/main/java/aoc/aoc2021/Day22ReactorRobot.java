package aoc.aoc2021;

import aoc.common.SpacePosition;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day22ReactorRobot {

    private final Set<SpacePosition> reactor = new HashSet<>();
    private final List<Step> steps = new ArrayList<>();

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

    public int problem1() {
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

    public long problem1b() {
        List<Cuboid> cuboids = new ArrayList<>();
        for (Step step : steps) {

            if (!step.cuboid.invalid()) {
                if (cuboids.isEmpty()) {
                    // The first cuboid should always be added
                    if (step.on) {
                        cuboids.add(step.cuboid);
                        log.info("Initial cuboid {}, total size {} cubes", step.cuboid, step.cuboid.volume());
                    } else {
                        log.warn("Initial cuboid is off");
                    }
                } else {
                    List<Cuboid> nextCuboids = new ArrayList<>();
                    if (step.on) {
                        log.info("Step on, {}", step.cuboid);
                        // The step is an "on" - add the new step to the list of cuboids and also go through all the
                        // other cuboids and split then if they intersect with the step cuboid.
                        nextCuboids.add(step.cuboid);
                        //Set<Cuboid> cuboidsToAdd = new HashSet<>();
                        for (Cuboid cuboid : cuboids) {
                            if (cuboid.isFullyInsideOf(step.cuboid)) {
                                // cuboid is fully enclosed by step.cuboid, so do nothing. No need to add it to next as
                                // step.cuboid is already added.
                                log.info("cuboid {} is fully inside of {}", cuboid, step.cuboid);
                            } else if (cuboid.isFullyOutsideOf(step.cuboid)) {
                                log.info("cuboid {} is fully outside of {}", cuboid, step.cuboid);
                                // remove the step.cuboid and instead add the cuboid
                                nextCuboids.remove(step.cuboid);
                                nextCuboids.add(cuboid);
                            } else if (step.cuboid.intersect(cuboid)) {
                                // split the intersecting cuboid and add its parts
                                nextCuboids.addAll(Cuboid.complement(step.cuboid, cuboid));
                            } else {
                                // cuboid is not intercepted by step.cuboid, so add it
                                nextCuboids.add(cuboid);
                            }
                        }
                        log.info("Created {} new cuboids", nextCuboids.size());
                    } else {
                        log.info("Step off, {}", step.cuboid);
                        // An "off" step. Go thorough all cuboids and see which intercepts with the step cuboid. These
                        // need to be splitted and added to the list
                        for (Cuboid cuboid : cuboids) {
                            if (step.cuboid.intersect(cuboid)) {
                                log.info("step cuboid {} intersection with {} is {}", step.cuboid, cuboid, Cuboid.intersection(step.cuboid, cuboid));
                                nextCuboids.addAll(Cuboid.complement(step.cuboid, cuboid));
                            } else {
                                nextCuboids.add(cuboid);
                            }
                        }
                    }
                    // check that no cuboids overlap
                    for (Cuboid a : nextCuboids) {
                        for (Cuboid b : nextCuboids) {
                            if (a != b) {
                                //if (!Cuboid.complement(a,b).isEmpty()) {
                                if (a.intersect(b)) {
                                    log.warn("{} intersect {}", a, b);
                                }
                            }
                        }
                    }
                    cuboids = nextCuboids;

                    Set<SpacePosition> cubes = new HashSet<>();
                    long size2 = 0;
                    for (Cuboid cuboid : cuboids) {
                        cubes.addAll(cuboid.getAllCubes());
                        size2 += cuboid.volume();
                    }
                    log.info("After step, {} cuboids, total size1 {}, size2 {}", cuboids.size(), cubes.size(), size2);
                }
            }
        }

        long size = 0L;
        for (Cuboid cuboid : cuboids) {
            size += cuboid.volume();
        }
        return size;
    }

    public String problem2() {
        // Keep a list of non-overlapping cubes that are on.
        // when new step is taken add (and split) the new cube
        return "0";
    }

    record Step(boolean on, Cuboid cuboid) {
    }

    public static class Cuboid {
        final SpacePosition lowCorner;
        final SpacePosition highCorner;

        public Cuboid(SpacePosition lowCorner, SpacePosition highCorner) {
            this.lowCorner = lowCorner;
            this.highCorner = highCorner;
            assert (lowCorner.getX() <= highCorner.getX() && lowCorner.getY() <= highCorner.getY() && lowCorner.getZ() <= highCorner.getZ());
        }

        public Cuboid(int lowX, int lowY, int lowZ, int highX, int highY, int highZ) {
            this(new SpacePosition(lowX, lowY, lowZ), new SpacePosition(highX, highY, highZ));
        }

        // returns new cuboid with the parts that are in both cuboid
        static Cuboid intersection(Cuboid a, Cuboid b) {
            return new Cuboid(
                    new SpacePosition(Integer.max(a.lowCorner.getX(), b.lowCorner.getX()),
                            Integer.max(a.lowCorner.getY(), b.lowCorner.getY()),
                            Integer.max(a.lowCorner.getZ(), b.lowCorner.getZ())),
                    new SpacePosition(Integer.min(a.highCorner.getX(), b.highCorner.getX()),
                            Integer.min(a.highCorner.getY(), b.highCorner.getY()),
                            Integer.min(a.highCorner.getZ(), b.highCorner.getZ())));
        }

        static Set<Cuboid> leftComplement(Cuboid a, Cuboid b) {
            return complement(a, b);
        }

        static Set<Cuboid> rightComplement(Cuboid a, Cuboid b) {
            return complement(b, a);
        }

        // Relative complement of A in B
        static Set<Cuboid> complement(Cuboid a, Cuboid b) {
            Set<Cuboid> result = new HashSet<>();

            // There can be a maximum of 26 (=27-1) new Cuboids created when removing the intersection cuboid. Some of the new
            // cuboids can be joined with others to form new, but that does not matter here.
            Cuboid intersection = intersection(a, b);
            log.info("complement intersection: {}", intersection);

            // Remove intersection from cuboid b. This will create a number of new cuboids from b.
            // Check which of intersection and b that is the lowest
            Cuboid lc;
            Cuboid is;
            if (intersection.lowCorner.getX() <= b.lowCorner.getX() && intersection.lowCorner.getY() <= b.lowCorner.getY() && intersection.lowCorner.getZ() <= b.lowCorner.getZ()) {
                lc = intersection;
                is = b;
            } else {
                lc = b;
                is = intersection;
            }

            is = intersection;
            lc = b;
            // First plane
            // 1 2 3
            result.add(new Cuboid(new SpacePosition(lc.lowCorner.getX(), lc.lowCorner.getY(), lc.lowCorner.getZ()), new SpacePosition(is.lowCorner.getX(), is.lowCorner.getY(), is.lowCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.lowCorner.getX(), lc.lowCorner.getY(), lc.lowCorner.getZ()), new SpacePosition(is.highCorner.getX(), is.lowCorner.getY(), is.lowCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.highCorner.getX(), lc.lowCorner.getY(), lc.lowCorner.getZ()), new SpacePosition(lc.highCorner.getX(), is.lowCorner.getY(), is.lowCorner.getZ())));
            // 4 5 6
            result.add(new Cuboid(new SpacePosition(lc.lowCorner.getX(), is.lowCorner.getY(), lc.lowCorner.getZ()), new SpacePosition(is.lowCorner.getX() - 1, is.highCorner.getY(), is.lowCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.lowCorner.getX(), is.lowCorner.getY(), lc.lowCorner.getZ()), new SpacePosition(is.highCorner.getX(), is.highCorner.getY(), is.lowCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.highCorner.getX(), is.lowCorner.getY(), lc.lowCorner.getZ()), new SpacePosition(lc.highCorner.getX(), is.highCorner.getY(), is.lowCorner.getZ())));
            // 7 8 9
            result.add(new Cuboid(new SpacePosition(lc.lowCorner.getX(), is.highCorner.getY(), lc.lowCorner.getZ()), new SpacePosition(is.lowCorner.getX(), lc.highCorner.getY(), is.lowCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.lowCorner.getX(), is.highCorner.getY(), lc.lowCorner.getZ()), new SpacePosition(is.highCorner.getX(), lc.highCorner.getY(), is.lowCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.highCorner.getX(), is.highCorner.getY(), lc.lowCorner.getZ()), new SpacePosition(lc.highCorner.getX(), lc.highCorner.getY(), is.lowCorner.getZ())));
            // Second plane
            // 1 2 3
            result.add(new Cuboid(new SpacePosition(lc.lowCorner.getX(), lc.lowCorner.getY(), is.lowCorner.getZ()), new SpacePosition(is.lowCorner.getX(), is.lowCorner.getY(), is.highCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.lowCorner.getX(), lc.lowCorner.getY(), is.lowCorner.getZ()), new SpacePosition(is.highCorner.getX(), is.lowCorner.getY(), is.highCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.highCorner.getX(), lc.lowCorner.getY(), is.lowCorner.getZ()), new SpacePosition(lc.highCorner.getX(), is.lowCorner.getY(), is.highCorner.getZ())));
            // 4 5 6
            result.add(new Cuboid(new SpacePosition(lc.lowCorner.getX(), is.lowCorner.getY(), is.lowCorner.getZ()), new SpacePosition(is.lowCorner.getX(), is.highCorner.getY(), is.highCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.lowCorner.getX(), is.lowCorner.getY(), is.lowCorner.getZ()), new SpacePosition(is.highCorner.getX(), is.highCorner.getY(), is.highCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.highCorner.getX(), is.lowCorner.getY(), is.lowCorner.getZ()), new SpacePosition(lc.highCorner.getX(), is.highCorner.getY(), is.highCorner.getZ())));
            // 7 8 9
            result.add(new Cuboid(new SpacePosition(lc.lowCorner.getX(), is.highCorner.getY(), is.lowCorner.getZ()), new SpacePosition(is.lowCorner.getX(), lc.highCorner.getY(), is.highCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.lowCorner.getX(), is.highCorner.getY(), is.lowCorner.getZ()), new SpacePosition(is.highCorner.getX(), lc.highCorner.getY(), is.highCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.highCorner.getX(), is.highCorner.getY(), is.lowCorner.getZ()), new SpacePosition(lc.highCorner.getX(), lc.highCorner.getY(), is.highCorner.getZ())));
            // Third plane
            // 1 2 3
            result.add(new Cuboid(new SpacePosition(lc.lowCorner.getX(), lc.lowCorner.getY(), is.highCorner.getZ()), new SpacePosition(is.lowCorner.getX(), is.lowCorner.getY(), lc.highCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.lowCorner.getX(), lc.lowCorner.getY(), is.highCorner.getZ()), new SpacePosition(is.highCorner.getX(), is.lowCorner.getY(), lc.highCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.highCorner.getX(), lc.lowCorner.getY(), is.highCorner.getZ()), new SpacePosition(lc.highCorner.getX(), is.lowCorner.getY(), lc.highCorner.getZ())));
            // 4 5 6
            result.add(new Cuboid(new SpacePosition(lc.lowCorner.getX(), is.lowCorner.getY(), is.highCorner.getZ()), new SpacePosition(is.lowCorner.getX(), is.highCorner.getY(), lc.highCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.lowCorner.getX(), is.lowCorner.getY(), is.highCorner.getZ()), new SpacePosition(is.highCorner.getX(), is.highCorner.getY(), lc.highCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.highCorner.getX(), is.lowCorner.getY(), is.highCorner.getZ()), new SpacePosition(lc.highCorner.getX(), is.highCorner.getY(), lc.highCorner.getZ())));
            // 7 8 9
            result.add(new Cuboid(new SpacePosition(lc.lowCorner.getX(), is.highCorner.getY(), is.highCorner.getZ()), new SpacePosition(is.lowCorner.getX(), lc.highCorner.getY(), lc.highCorner.getZ())));
            result.add(new Cuboid(new SpacePosition(is.lowCorner.getX(), is.highCorner.getY(), is.highCorner.getZ()), new SpacePosition(is.highCorner.getX(), lc.highCorner.getY(), lc.highCorner.getZ())));
            result.add(new Cuboid(is.highCorner, lc.highCorner));


            // remove all cuboids that have a zero volume
            //result.removeIf(Cuboid::isNonCuboid);
            Set<Cuboid> result2 = new HashSet<>();
            result.forEach(cuboid -> {
                cuboid.highCorner.setX(cuboid.highCorner.getX() - 1);
                cuboid.highCorner.setY(cuboid.highCorner.getY() - 1);
                cuboid.highCorner.setZ(cuboid.highCorner.getZ() - 1);
                result2.add(cuboid);
            });
            result2.removeIf(cuboid ->
                    (cuboid.lowCorner.getX() > cuboid.highCorner.getX() || cuboid.lowCorner.getY() > cuboid.highCorner.getY() || cuboid.lowCorner.getZ() > cuboid.highCorner.getZ())
            );
            result.remove(intersection);

            // At least one dimension should have all cuboids intact. Join all these to a bigger Cuboid
            Set<Cuboid> cuboidsInXPositivePlane = new HashSet<>();
            Set<Cuboid> cuboidsInXNegativePlane = new HashSet<>();
            Set<Cuboid> cuboidsInYPositivePlane = new HashSet<>();
            Set<Cuboid> cuboidsInYNegativePlane = new HashSet<>();
            Set<Cuboid> cuboidsInZPositivePlane = new HashSet<>();
            Set<Cuboid> cuboidsInZNegativePlane = new HashSet<>();
            for (Cuboid cuboid : result) {
                if (cuboid.lowCorner.getX() == b.lowCorner.getX() && cuboid.highCorner.getX() <= intersection.lowCorner.getX()) {
                    cuboidsInXPositivePlane.add(cuboid);
                }
                if (cuboid.lowCorner.getX() == b.lowCorner.getX() && cuboid.highCorner.getX() <= intersection.lowCorner.getX()) {
                    cuboidsInXNegativePlane.add(cuboid);
                }
                if (cuboid.lowCorner.getY() == b.lowCorner.getY() && cuboid.highCorner.getY() <= intersection.lowCorner.getY()) {
                    cuboidsInYPositivePlane.add(cuboid);
                }
                if (cuboid.lowCorner.getZ() == b.lowCorner.getZ() && cuboid.highCorner.getZ() <= intersection.lowCorner.getZ()) {
                    cuboidsInZPositivePlane.add(cuboid);
                }
            }
            return result2;
        }

        long volume() {
            return ((long) (Math.abs(lowCorner.getX() - highCorner.getX()) + 1) *
                    (long) (Math.abs(lowCorner.getY() - highCorner.getY()) + 1) *
                    (long) (Math.abs(lowCorner.getZ() - highCorner.getZ()) + 1));
        }

        long volumeWithoutEdges() {
            int x = Math.abs(lowCorner.getX() - highCorner.getX());
            x = x - 1 > 0 ? x : 0;

            return ((long) (Math.abs(lowCorner.getX() - highCorner.getX()) + 1) *
                    (long) (Math.abs(lowCorner.getY() - highCorner.getY()) + 1) *
                    (long) (Math.abs(lowCorner.getZ() - highCorner.getZ()) + 1));
        }

        boolean invalid() {
            return (lowCorner.getX() < -50 || highCorner.getX() > 50 ||
                    lowCorner.getY() < -50 || highCorner.getY() > 50 ||
                    lowCorner.getZ() < -50 || highCorner.getZ() > 50);
        }

        // True if the cuboid somehow overlap
        boolean intersect(Cuboid other) {
//            if (isFullyInsideOf(other) || isFullyOutsideOf(other)) {
//                return true;
//            }
            if (other.lowCorner.getX() >= highCorner.getX() || other.lowCorner.getY() >= highCorner.getY() || other.lowCorner.getZ() >= highCorner.getZ())
                return false;
            if (other.highCorner.getX() <= lowCorner.getX() || other.highCorner.getY() <= lowCorner.getY() || other.highCorner.getZ() <= lowCorner.getZ())
                return false;
            return true;
            //return (other.lowCorner.getX() < highCorner.getX() && other.lowCorner.getY() < highCorner.getY() && other.lowCorner.getZ() < highCorner.getZ());
        }

        // returns new cuboid with the parts that are in both cuboid
        Cuboid intersection(Cuboid other) {
            return intersection(this, other);
        }

        // True if cuboid is fully outside other cuboid
        public boolean isFullyOutsideOf(Cuboid other) {
            return lowCorner.getX() <= other.lowCorner.getX() &&
                    highCorner.getX() >= other.highCorner.getX() &&
                    lowCorner.getY() <= other.lowCorner.getY() &&
                    highCorner.getY() >= other.highCorner.getY() &&
                    lowCorner.getZ() <= other.lowCorner.getZ() &&
                    highCorner.getZ() >= other.highCorner.getZ();
        }
        // if the cuboids have the exact same size, they could be both inside and outside..

        // True if cuboid is fully inside other cuboid
        public boolean isFullyInsideOf(Cuboid other) {
            return lowCorner.getX() >= other.lowCorner.getX() &&
                    highCorner.getX() <= other.highCorner.getX() &&
                    lowCorner.getY() >= other.lowCorner.getY() &&
                    highCorner.getY() <= other.highCorner.getY() &&
                    lowCorner.getZ() >= other.lowCorner.getZ() &&
                    highCorner.getZ() <= other.highCorner.getZ();
        }

        // Is the cuboid only in one or two dimensions
        boolean isNonCuboid() {
            return lowCorner.getX() == highCorner.getX() ||
                    lowCorner.getY() == highCorner.getY() ||
                    lowCorner.getZ() == highCorner.getZ();
        }

        Set<SpacePosition> getAllCubes() {
            Set<SpacePosition> result = new HashSet<>();
            for (int x = lowCorner.getX(); x <= highCorner.getX(); x++) {
                for (int y = lowCorner.getY(); y <= highCorner.getY(); y++) {
                    for (int z = lowCorner.getZ(); z <= highCorner.getZ(); z++) {
                        result.add(new SpacePosition(x, y, z));
                    }
                }
            }
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Cuboid other) {
                return lowCorner.getX() == other.lowCorner.getX() &&
                        highCorner.getX() == other.highCorner.getX() &&
                        lowCorner.getY() == other.lowCorner.getY() &&
                        highCorner.getY() == other.highCorner.getY() &&
                        lowCorner.getZ() == other.lowCorner.getZ() &&
                        highCorner.getZ() == other.highCorner.getZ();
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(lowCorner, highCorner);
        }

        @Override
        public String toString() {
            return String.format("Cuboid {%d,%d,%d - %d,%d,%d}",
                    lowCorner.getX(), lowCorner.getY(), lowCorner.getZ(),
                    highCorner.getX(), highCorner.getY(), highCorner.getZ());
        }

    }
}
