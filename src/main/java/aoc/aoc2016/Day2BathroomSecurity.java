package aoc.aoc2016;

import aoc.Direction;
import aoc.Position;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2BathroomSecurity {

    private List<String> instructions = new ArrayList<>();
    private Map<Position, Character> keypad = new HashMap<>();

    public Day2BathroomSecurity(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        instructions.addAll(inputStrings);
    }

    private void setupKeypad() {
        keypad.put(new Position(-1, -1), '1');
        keypad.put(new Position(0, -1), '2');
        keypad.put(new Position(1, -1), '3');
        keypad.put(new Position(-1, 0), '4');
        keypad.put(new Position(0, 0), '5');
        keypad.put(new Position(1, 0), '6');
        keypad.put(new Position(-1, 1), '7');
        keypad.put(new Position(0, 1), '8');
        keypad.put(new Position(1, 1), '9');
    }

    private void setupSecondKeypad() {
        keypad.put(new Position(0, -2), '1');
        keypad.put(new Position(-1, -1), '2');
        keypad.put(new Position(0, -1), '3');
        keypad.put(new Position(1, -1), '4');
        keypad.put(new Position(-2, 0), '5');
        keypad.put(new Position(-1, 0), '6');
        keypad.put(new Position(0, 0), '7');
        keypad.put(new Position(1, 0), '8');
        keypad.put(new Position(2, 0), '9');
        keypad.put(new Position(-1, 1), 'A');
        keypad.put(new Position(0, 1), 'B');
        keypad.put(new Position(1, 1), 'C');
        keypad.put(new Position(0, 2), 'D');
    }

    String bathroomCode() {
        setupKeypad();
        return punchCode(new Position(0, 0)); // key 5
    }

    String secondBathroomCode() {
        setupSecondKeypad();
        return punchCode(new Position(-2, 0)); // key 5
    }

    private String punchCode(Position start) {
        Position pos = start;
        StringBuilder code = new StringBuilder();

        for (String instruction : instructions) {
            Direction direction;
            for (char dir : instruction.toCharArray()) {
                switch (dir) {
                    case 'U':
                        direction = Direction.Up;
                        break;
                    case 'D':
                        direction = Direction.Down;
                        break;
                    case 'R':
                        direction = Direction.Right;
                        break;
                    case 'L':
                        direction = Direction.Left;
                        break;
                    default:
                        direction = Direction.Unknown;
                        break;
                }
                Position next = pos.adjacent(direction);
                if (keypad.containsKey(next)) {
                    pos = next;
                }
            }
            code.append(keypad.get(pos));
        }
        return code.toString();
    }
}
