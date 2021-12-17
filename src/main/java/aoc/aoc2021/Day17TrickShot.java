package aoc.aoc2021;

import aoc.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day17TrickShot {

    Position upperLeft;
    Position lowerRight;

    public Day17TrickShot(List<String> inputLines) {
        Pattern pattern = Pattern.compile("target area: x=(-?\\d+)..(-?\\d+), y=(-?\\d+)..(-?\\d+)");
        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                upperLeft = new Position(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(4)));
                lowerRight = new Position(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
            }
        }
    }

    int problem0(int xVelocity, int yVelocity) {
        Probe probe = new Probe(upperLeft, lowerRight, xVelocity, yVelocity);
        probe.fire();
        return probe.maxHeight;
    }

    int problem1() {
        int maxHeight = Integer.MIN_VALUE;
        for (int xVelocity = 0; xVelocity < 1000; xVelocity++) {
            for (int yVelocity = -1000; yVelocity < 1000; yVelocity++) {
                Probe probe = new Probe(upperLeft, lowerRight, xVelocity, yVelocity);
                probe.fire();
                if (probe.targetHit) {
                    if (probe.maxHeight > maxHeight) {
                        maxHeight = probe.maxHeight;
                    }
                }
            }
        }
        return maxHeight;
    }

    int problem2() {
        int hits = 0;
        for (int xVelocity = 0; xVelocity < 1000; xVelocity++) {
            for (int yVelocity = -1000; yVelocity < 1000; yVelocity++) {
                Probe probe = new Probe(upperLeft, lowerRight, xVelocity, yVelocity);
                probe.fire();
                if (probe.targetHit) {
                    hits++;
                }
            }
        }
        return hits;
    }


    static class Probe {
        Position position = new Position(0, 0);
        Position upperLeft;
        Position lowerRight;
        int xVelocity;
        int yVelocity;
        int maxHeight = Integer.MIN_VALUE;
        boolean targetHit = false;

        public Probe(Position upperLeft, Position lowerRight, int xVelocity, int yVelocity) {
            this.upperLeft = upperLeft;
            this.lowerRight = lowerRight;
            this.xVelocity = xVelocity;
            this.yVelocity = yVelocity;
        }

        public void fire() {
            while (!hasPassedTarget()) {
                move();
                //log.info("Moving to {}", position);
                if (isInsideTarget()) {
                    targetHit = true;
                }
            }
        }

        void move() {
            position.setX(position.getX() + xVelocity);
            position.setY(position.getY() + yVelocity);
            if (xVelocity > 0) {
                xVelocity--;
            } else if (xVelocity < 0) {
                xVelocity++;
            }
            yVelocity--;
            if (position.getY() > maxHeight) {
                maxHeight = position.getY();
            }
        }

        boolean isInsideTarget() {
            return position.getX() >= upperLeft.getX() &&
                    position.getX() <= lowerRight.getX() &&
                    position.getY() <= upperLeft.getY() &&
                    position.getY() >= lowerRight.getY();
        }

        boolean hasPassedTarget() {
            if (position.getX() > lowerRight.getX()) {
                return true;
            }
            return position.getY() < lowerRight.getY() && position.getY() < maxHeight;
        }
    }

}
