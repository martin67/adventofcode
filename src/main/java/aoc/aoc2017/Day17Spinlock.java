package aoc.aoc2017;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day17Spinlock {
    final int steps;

    public Day17Spinlock(int steps) {
        this.steps = steps;
    }

    int completedBufferValue() {
        List<Integer> circularBuffer = new LinkedList<>();
        circularBuffer.add(0);
        int currentPosition = 0;
        for (int i = 0; i < 2017; i++) {
            int bufferSize = circularBuffer.size();
            int nextPositionSteps = (steps + bufferSize) % bufferSize;
            //currentPosition = (currentPosition + nextPositionSteps + bufferSize) % bufferSize;
            currentPosition = (currentPosition + steps) % bufferSize;
            if (bufferSize == 1) {
                circularBuffer.add(1);
            } else {
                if (currentPosition == bufferSize - 1) {
                    circularBuffer.add(i + 1);
                } else {
                    circularBuffer.add((currentPosition + 1) % bufferSize, i + 1);
                }
            }
            currentPosition++;
        }
        return circularBuffer.get(currentPosition + 1);
    }

    int secondCompletedBufferValue() {
        List<Integer> circularBuffer = new LinkedList<>();
        Set<Integer> values = new TreeSet<>();

        circularBuffer.add(0);
        int currentPosition = 0;
        for (int i = 0; i < 50000000; i++) {
            int bufferSize = circularBuffer.size();
            int nextPositionSteps = (steps + bufferSize) % bufferSize;
            //currentPosition = (currentPosition + nextPositionSteps + bufferSize) % bufferSize;
            currentPosition = (currentPosition + steps) % bufferSize;
            if (bufferSize == 1) {
                circularBuffer.add(1);
            } else {
                if (currentPosition == bufferSize - 1) {
                    circularBuffer.add(i + 1);
                } else {
                    circularBuffer.add((currentPosition + 1) % bufferSize, i + 1);
                }
            }
            int index = circularBuffer.indexOf(0) + 1;
            if (index == circularBuffer.size()) {
                index = 0;
            }
            values.add(circularBuffer.get(index));
            if ((i % 100000) == 0) {
                log.info("value after {} iterations: {}", i, values);
            }
            currentPosition++;
        }
        return circularBuffer.get(currentPosition + 1);

    }

    int problem2() {
        int pos = 0;
        int value = 0;

        for (int i = 0; i < 50000000; i++) {
            pos = (pos + steps) % (i + 1);
            if (pos == 0) {
                log.info("pos: {}, i: {}", pos, i + 1);
                value = i + 1;
            }
            pos++;
        }
        return value;
    }
}
