package aoc.aoc2021;

import java.util.ArrayList;
import java.util.List;

public class Day3BinaryDiagnostic {

    private final List<Integer> numbers = new ArrayList<>();
    private int width;

    public Day3BinaryDiagnostic(List<String> inputLines) {
        for (String line : inputLines) {
            width = line.length();
            numbers.add(Integer.parseInt(line, 2));
        }
    }

    public int problem1() {
        int gamma = 0;
        int epsilon = 0;

        for (int position = 0; position < width; position++) {
            int mask = 1 << width - position - 1;

            int ones = 0;
            int zeros = 0;
            for (int i : numbers) {
                if ((i & mask) != 0) {
                    ones++;
                } else {
                    zeros++;
                }
            }
            if (ones > zeros) {
                gamma += mask;
            } else {
                epsilon += mask;
            }
        }
        return gamma * epsilon;
    }

    public int problem2() {

        List<Integer> numbersCopy = new ArrayList<>(numbers);
        for (int i = 0; i < width; i++) {
            numbersCopy = bitCriteria(numbersCopy, i, true);
        }
        int oxygen = numbersCopy.get(0);

        numbersCopy = new ArrayList<>(numbers);
        for (int i = 0; i < width; i++) {
            numbersCopy = bitCriteria(numbersCopy, i, false);
        }
        int co2 = numbersCopy.get(0);

        return oxygen * co2;
    }

    private List<Integer> bitCriteria(List<Integer> in, int position, boolean oxygen) {
        List<Integer> zeros = new ArrayList<>();
        List<Integer> ones = new ArrayList<>();

        if (in.size() == 1) {
            return in;
        }

        for (int i : in) {
            if ((i & (1 << width - position - 1)) != 0) {
                ones.add(i);
            } else {
                zeros.add(i);
            }
        }

        if (zeros.size() > ones.size()) {
            return oxygen ? zeros : ones;
        } else {
            return oxygen ? ones : zeros;
        }
    }
}
