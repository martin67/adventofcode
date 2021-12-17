package aoc.aoc2021;

import aoc.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day17TrickShot {

    Position topLeft;
    Position lowerRight;

    public Day17TrickShot(List<String> inputLines) {
        Pattern pattern = Pattern.compile("target area: x=(-?\\d+)..(-?\\d+), y=(-?\\d+)..(-?\\d+)");
        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                topLeft = new Position(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(4)));
                lowerRight = new Position(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
            }
        }
    }

    int problem1() {
        int maxHeight = Integer.MIN_VALUE;
        for (int xVelocity = 0; xVelocity < 10; xVelocity++) {
            for (int yVelocity = -10; yVelocity < 10; yVelocity++) {
                Probe probe = new Probe(upperLeft, lowerRight, xVelocity, yVelocity);
                //Probe probe = new Probe(7, 2);
        }
        return maxHeight;
    }

    int problem2() {
        return 0;
    }

    class Probe {
        Position position;
        Position upperLeft;
        Position lowerRight;
        int xVelocity;
        int yVelocity;
        int maxHeight;

        public Probe(int xVelocity, int yVelocity) {
            this.position = new Position(0, 0);
            this.xVelocity = xVelocity;
            this.yVelocity = yVelocity;
            this.maxHeight = Integer.MIN_VALUE;
        }

        public Probe(Position upperLeft, Position lowerRight, int xVelocity, int yVelocity) {
            this.upperLeft = upperLeft;
            this.lowerRight = lowerRight;
            this.xVelocity = xVelocity;
            this.yVelocity = yVelocity;
        }

        public int computeMaxHeight() {
            boolean hitTarget = false;
            while (!hasPassedTarget()) {
                move();
                //log.info("Moving to {}", probe.position);
                if (isInsideTarget()) {
                    hitTarget = true;
                }
            }
            if (hitTarget) {
                if (height > maxHeight) {
                    log.info("Max height: probe: {}", height);
                    maxHeight = height;
                }
            }
            log.info("Probe {} passed target", probe);
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
            if (position.getY() < lowerRight.getY() && position.getY() < maxHeight) {
                return true;
            }
            return false;
        }
    }

}
