package aoc.aoc2015;

import com.google.common.collect.Sets;
import lombok.Data;

import java.util.List;
import java.util.OptionalInt;
import java.util.Set;

public class Day17NoSuchThingAsTooMuch {

    final Set<Container> containers = Sets.newHashSet();

    public Day17NoSuchThingAsTooMuch(List<String> inputLines) {
        int id = 0;
        for (String line : inputLines) {
            int size = Integer.parseInt(line);
            containers.add(new Container(id, size));
            id++;
        }
    }

    public long numberOfCombinations(int liters) {
        return Sets.powerSet(containers).stream()
                .filter(s -> s.stream().mapToInt(Container::getSize).sum() == liters)
                .count();
    }

    public long numberOfMinimumCombinations(int liters) {
        OptionalInt monValue = Sets.powerSet(containers).stream()
                .filter(s -> s.stream().mapToInt(Container::getSize).sum() == liters)
                .mapToInt(Set::size).min();

        return Sets.powerSet(containers).stream()
                .filter(s -> s.stream().mapToInt(Container::getSize).sum() == liters)
                .filter(s2 -> s2.size() == monValue.getAsInt())
                .count();
    }

    @Data
    static class Container {
        int id;
        int size;

        public Container(int id, int size) {
            this.id = id;
            this.size = size;
        }
    }
}
