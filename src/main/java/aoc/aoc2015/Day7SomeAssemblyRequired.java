package aoc.aoc2015;

import java.util.List;
import java.util.Set;

public class Day7SomeAssemblyRequired {

    class Wire {
        String name;
    }

    class Gate {
        String name;
        int parameter;
        Set<Wire> inputs;
        Wire output;

        void execute() {}
    }

    public Day7SomeAssemblyRequired(List<String> inputLines) {
        for (String line : inputLines) {

        }
    }

    int signalWire(String wireName) {
        return 0;
    }
}
