package aoc.common;

public class WrappingCounter {

    private final String id;
    private final int size;
    private int value;

    public WrappingCounter(String id, int size) {
        this.id = id;
        this.size = size;
        value = 0;
    }

    public void increment() {
        if (value < size - 1) {
            value++;
        } else {
            value = 0;
        }
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return id + ": " + value;
    }

}
