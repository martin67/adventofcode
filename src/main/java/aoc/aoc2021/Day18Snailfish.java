package aoc.aoc2021;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Day18Snailfish {

    public Day18Snailfish() {
    }

    public Day18Snailfish(List<String> inputLines) {
        inputLines.forEach(line -> {
        });
    }

    String explode(String input) {
        SnailFishNumber snailFishNumber = new SnailFishNumber(null);
        input = input.substring(1, input.length() - 1);
        snailFishNumber.init(input, 0);
        log.info("After init: {}", snailFishNumber);
        snailFishNumber.explode(0);
        log.info("After explode: {}", snailFishNumber);
        return snailFishNumber.toString();
    }

    int problem1() {
        return 0;
    }

    int problem2() {
        return 0;
    }

    static class SnailFishNumber {
        final SnailFishNumber parent;
        int leftNumber;
        int rightNumber;
        SnailFishNumber leftSnailFish;
        SnailFishNumber rightSnailFish;
        boolean abort;

        public SnailFishNumber(SnailFishNumber parent) {
            this.parent = parent;
        }

        String init(String input, int level) {
            log.debug("Init: {}, level: {}", input, level);
            String output;
            if (input.startsWith("[")) {
                leftSnailFish = new SnailFishNumber(this);
                output = leftSnailFish.init(input.substring(1), level + 1);
            } else {
                leftNumber = Integer.parseInt(input.substring(0, 1));
                output = input.substring(1);
            }
            log.debug("left = {}", leftSnailFish == null ? leftNumber : leftSnailFish);

            if (output.isEmpty()) {
                log.debug("Final return: {}", this);
                return this.toString();
            }

            // skip comma
            output = output.substring(1);

            if (output.startsWith("[")) {
                rightSnailFish = new SnailFishNumber(this);
                output = rightSnailFish.init(output.substring(1), level + 1);
            } else {
                rightNumber = Integer.parseInt(output.substring(0, 1));
                output = output.substring(1);
            }
            log.debug("right = {}", rightSnailFish == null ? rightNumber : rightSnailFish);

            // skip closing ]

            if (output.isEmpty()) {
                log.debug("reached end");
            } else {
                output = output.substring(1);
            }
            log.debug("Return: {}, level {}", this, level);
            return output;
        }

        boolean explode(int level) {
            if (level == 4) {
                log.info("Explode! {}", this);
                return (true);
            }

            boolean hasExploded = false;
            if (leftSnailFish != null) {
                hasExploded = leftSnailFish.explode(level + 1);
                if (hasExploded) {
                    log.info("left exploded");
                    SnailFishNumber rightMostSnailFish = getRightMostNumber(leftSnailFish);
                    if (rightMostSnailFish != null) {
                        rightNumber += rightMostSnailFish.rightNumber;
                    }

                    SnailFishNumber leftMostSnailFish = getLeftMostNumber(parent);
                    if (leftMostSnailFish != null) {
                        leftMostSnailFish.leftNumber += leftNumber;
                    }

                    leftSnailFish = null;
                    abort = true;
                }
            } else if (rightSnailFish != null) {
                hasExploded = rightSnailFish.explode(level + 1);
                if (hasExploded) {
                    log.info("right exploded");
                    SnailFishNumber leftMostSnailFish = getLeftMostNumber(rightSnailFish);
                    if (leftMostSnailFish != null) {
                        leftNumber += leftMostSnailFish.leftNumber;
                    }

                    SnailFishNumber rightMostSnailFish = getRightMostNumber(parent);
                    if (rightMostSnailFish != null) {
                        rightMostSnailFish.rightNumber += rightSnailFish.rightNumber;
                    }

                    rightSnailFish = null;
                    abort = true;
                }
            }

            return false;
        }

        // get the leftmost snailfishnumber that has an integer as left or null if there is no number
        SnailFishNumber getLeftMostNumber(SnailFishNumber input) {
            SnailFishNumber result;
            if (input.leftSnailFish == null) {
                result = input;
            } else {
                SnailFishNumber lsf = input.parent;
                while (lsf != null && lsf.leftSnailFish != null) {
                    lsf = lsf.parent;
                }
                result = lsf;
            }
            log.info("getLeftMostNumber, in: {} -> out: {}", input, result);
            return result;
        }

        // get the rightmost snailfishnumber that has an integer as left or null if there is no number
        SnailFishNumber getRightMostNumber(SnailFishNumber input) {
            SnailFishNumber result;
            if (input.rightSnailFish == null) {
                result = input;
            } else {
                SnailFishNumber rsf = input.parent;
                while (rsf != null && rsf.rightSnailFish != null) {
                    rsf = rsf.parent;
                }
                result = rsf;
            }
            log.info("getRightMostNumber, in: {} -> out: {}", input, result);
            return result;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            if (leftSnailFish != null) {
                sb.append(leftSnailFish);
            } else {
                sb.append(leftNumber);
            }
            sb.append(",");
            if (rightSnailFish != null) {
                sb.append(rightSnailFish);
            } else {
                sb.append(rightNumber);
            }
            sb.append("]");
            return sb.toString();
        }
    }

}
