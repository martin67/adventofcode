package aoc.aoc2017;

public class Day15DuelingGenerators {
    private final Generator generatorA;
    private final Generator generatorB;

    public Day15DuelingGenerators(int startA, int startB) {
        generatorA = new Generator("A", 16807, 4, startA);
        generatorB = new Generator("B", 48271, 8, startB);
    }

    int finalCount() {
        int match = 0;
        for (long i = 0; i < 40000000; i++) {
            if ((generatorA.nextValue() & 0xffff) == (generatorB.nextValue() & 0xffff)) {
                match++;
            }
        }
        return match;
    }

    int finalMultipleCount() {
        int match = 0;
        for (long i = 0; i < 5000000; i++) {
            if ((generatorA.nextMultipleValue() & 0xffff) == (generatorB.nextMultipleValue() & 0xffff)) {
                match++;
            }
        }
        return match;
    }

    static class Generator {
        final String name;
        final int factor;
        final int multiple;
        long current;

        public Generator(String name, int factor, int multiple, int start) {
            this.name = name;
            this.factor = factor;
            this.multiple = multiple;
            this.current = start;
        }

        long nextValue() {
            current = (current * factor) % 2147483647;
            return current;
        }

        long nextMultipleValue() {
            do {
                current = (current * factor) % 2147483647;
            } while (current % multiple != 0);
            return current;
        }
    }
}
