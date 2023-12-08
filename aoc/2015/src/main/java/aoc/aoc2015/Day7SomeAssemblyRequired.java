package aoc.aoc2015;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day7SomeAssemblyRequired {

    final Set<Gate> gates = new HashSet<>();

    public Day7SomeAssemblyRequired(List<String> inputLines) {
        //gates.add(Gate.builder().name("AND").build()
        var pattern = Pattern.compile("^(\\w+) (\\w+) (\\w+) -> (\\w+)$");

        for (String line : inputLines) {
            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                gates.add(new Gate(matcher.group(2), matcher.group(1), matcher.group(3), matcher.group(4)));
            }

            pattern = Pattern.compile("^NOT (\\w+) -> (\\w+)$");
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                gates.add(new Gate("NOT", matcher.group(1), "", matcher.group(2)));
            }

            pattern = Pattern.compile("^(\\w+) -> (\\w+)$");
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                gates.add(new Gate("SRC", matcher.group(1), "", matcher.group(2)));
            }
        }
    }

    int evaluateWire(String wireName) {
        // find gate that has wireName as output
        int result = -99999;
        for (Gate gate : gates) {
            if (gate.output.equals(wireName)) {
                result = gate.compute();
            }
        }
        if (result == -99999) {
            log.error("Not found for wire: {}", wireName);
        }
        return result;
    }

    public int signalWire(String wireName) {
        return evaluateWire(wireName);
    }

    public int signalRewired(String wireName) {
        int result = evaluateWire(wireName);

        for (Gate gate : gates) {
            gate.reset();
            if (gate.output.equals("b")) {
                gate.inputX = Integer.toString(result);
            }
        }
        result = evaluateWire(wireName);

        return result;
    }

    class Gate {
        final String name;
        final String inputY;
        final String output;
        String inputX;
        boolean done;
        int finalResult;

        public Gate(String name, String inputX, String inputY, String output) {
            this.name = name;
            this.inputX = inputX;
            this.inputY = inputY;
            this.output = output;
            this.finalResult = 0;
            this.done = false;
        }

        void reset() {
            this.finalResult = 0;
            this.done = false;
        }

        int compute() {
            int x;
            int y;
            int result;

            if (done) {
                return finalResult;
            }

            switch (name) {
                case "AND":
                    if (inputX.chars().allMatch(Character::isDigit)) {
                        x = Integer.parseInt(inputX);
                    } else {
                        x = evaluateWire(inputX);
                    }
                    y = evaluateWire(inputY);
                    result = x & y;
                    break;
                case "OR":
                    x = evaluateWire(inputX);
                    y = evaluateWire(inputY);
                    result = x | y;
                    break;
                case "LSHIFT":
                    x = evaluateWire(inputX);
                    result = x << Integer.parseInt(inputY);
                    break;
                case "RSHIFT":
                    x = evaluateWire(inputX);
                    result = x >> Integer.parseInt(inputY);
                    break;
                case "NOT":
                    x = evaluateWire(inputX);
                    result = ~x;
                    break;
                case "SRC":
                    if (inputX.chars().allMatch(Character::isDigit)) {
                        result = Integer.parseInt(inputX);
                    } else {
                        result = evaluateWire(inputX);
                    }
                    break;
                default:
                    log.error("Illegal instruction: {}", name);
                    result = -1;
                    break;
            }
            finalResult = result;
            done = true;
            return result;
        }
    }
}
