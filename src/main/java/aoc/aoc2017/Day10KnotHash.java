package aoc.aoc2017;

import java.util.ArrayList;
import java.util.List;

public class Day10KnotHash {
    final int listSize;
    List<Integer> list = new ArrayList<>();
    List<Integer> lengthsWithCharacters = new ArrayList<>();
    List<Integer> lengths = new ArrayList<>();

    public Day10KnotHash(int listSize, List<String> inputLines) {
        this.listSize = listSize;
        for (String line : inputLines) {
            String[] numbers = line.split(",");
            for (String number : numbers) {
                lengths.add(Integer.parseInt(number));
            }

            for (char c : line.toCharArray()) {
                lengthsWithCharacters.add((int) c);
            }
        }
        for (int i = 0; i < listSize; i++) {
            list.add(i);
        }
        lengthsWithCharacters.addAll(List.of(17, 31, 73, 47, 23));
    }

    int checkKnot() {
        return runRounds(lengths, 1);
    }

    String knotHash() {
        runRounds(lengthsWithCharacters, 64);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int hash = 0;
            for (int j = 0; j < 16; j++) {
                hash ^= list.get(i * 16 + j);
            }
            sb.append(Integer.toHexString(hash));
        }
        return sb.toString();
    }

    int runRounds(List<Integer> lengths, int numberOfRounds) {
        int currentPosition = 0;
        int skipSize = 0;

        for (int round = 0; round <numberOfRounds ; round++) {

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
