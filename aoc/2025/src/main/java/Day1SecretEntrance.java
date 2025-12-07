import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day1SecretEntrance {
    List<Rotation> rotations = new ArrayList<>();

    Day1SecretEntrance(List<String> inputLines) {
        for (String line : inputLines) {
            rotations.add(new Rotation(line.charAt(0), Integer.parseInt(line.substring(1))));
        }
    }

    int problem1() {
        var dial = new Dial();

        for (var rotation : rotations) {
            dial.rotate(rotation);
        }
        return dial.zeros;
    }

    int problem2() {
        var dial = new Dial();

        for (var rotation : rotations) {
            dial.rotate(rotation);
        }
        return dial.allZeros;
    }

    record Rotation(char direction, int distance) {
    }

    static class Dial {
        int position = 50;
        int zeros = 0;
        int allZeros = 0;

        void rotate(Rotation rotation) {
            int newPosition;

            if (rotation.direction == 'R') {
                newPosition = (position + rotation.distance) % 100;
                allZeros += (position + rotation.distance) / 100;
            } else {
                newPosition = (position - rotation.distance) % 100;

                if (newPosition <= 0) {
                    allZeros += Math.abs((position - rotation.distance) / 100) + 1;
                }

                if (newPosition < 0) {
                    newPosition += 100;
                }

                if (position == 0) {
                    allZeros--;
                }
            }
            position = newPosition;
            if (position == 0) {
                zeros++;
            }
        }
    }
}
