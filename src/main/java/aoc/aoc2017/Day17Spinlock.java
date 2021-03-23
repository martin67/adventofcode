package aoc.aoc2017;

import java.util.LinkedList;
import java.util.List;

public class Day17Spinlock {
    int steps;

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

            currentPosition++;
        }
        return circularBuffer.get(currentPosition + 1);

    }
}
