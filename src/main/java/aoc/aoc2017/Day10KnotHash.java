package aoc.aoc2017;

import java.util.ArrayList;
import java.util.List;

public class Day10KnotHash {
    final List<Integer> list = new ArrayList<>();

    public Day10KnotHash(int listSize) {
        for (int i = 0; i < listSize; i++) {
            list.add(i);
        }
    }

    int checkKnot(String line) {
        List<Integer> lengths = new ArrayList<>();

        String[] numbers = line.split(",");
        for (String number : numbers) {
            lengths.add(Integer.parseInt(number));
        }

        return runRounds(lengths, 1);
    }

    String knotHash(String input) {
        List<Integer> lengths = new ArrayList<>();

        for (char c : input.toCharArray()) {
            lengths.add((int) c);
        }
        lengths.addAll(List.of(17, 31, 73, 47, 23));

        runRounds(lengths, 64);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int hash = 0;
            for (int j = 0; j < 16; j++) {
                hash ^= list.get(i * 16 + j);
            }
            sb.append(String.format("%02x", hash));
        }
        return sb.toString();
    }

    int runRounds(List<Integer> lengths, int numberOfRounds) {
        int currentPosition = 0;
        int skipSize = 0;

        for (int round = 0; round < numberOfRounds; round++) {

            for (int length : lengths) {
                // reverse from currentPosition, length: length
                for (int i = 0; i < length / 2; i++) {
                    // Reverse
                    int p1 = currentPosition + i;
                    while (p1 >= list.size()) p1 -= list.size();

                    int p2 = currentPosition + length - i - 1;
                    while (p2 >= list.size()) p2 -= list.size();

                    // swap
                    int temp = list.get(p2);
                    list.set(p2, list.get(p1));
                    list.set(p1, temp);
                }

                currentPosition += length + skipSize;
                while (currentPosition >= list.size()) currentPosition -= list.size();

                skipSize++;
            }
        }
        return list.get(0) * list.get(1);
    }
}
