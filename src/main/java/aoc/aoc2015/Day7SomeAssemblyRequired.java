package aoc.aoc2015;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Slf4j
public class Day7SomeAssemblyRequired {

    class Wire {
        String name;
    }

    class Gate {
        String name;
        int parameter;
        List<Wire> inputs;
        Wire output;

        void execute() {}
    }

    public Day7SomeAssemblyRequired(List<String> inputLines) {
        Set<Gate> gates = new HashSet<>();
        //gates.add(Gate.builder().name("AND").build()

        for (String line : inputLines) {

        }
    }

    int signalWire(String wireName) {
        return 0;
    }
}
