package aoc.aoc2017;

import java.util.ArrayList;
import java.util.List;

public class Day16PermutationPromenade {
    List<String> danceMoves = new ArrayList<>();
    final int programSize;
    char[] programs;

    public Day16PermutationPromenade(List<String> inputLines, int programSize) {
        this.programSize = programSize;

        for (String line : inputLines) {
            danceMoves = List.of(line.split(","));
        }
        programs = new char[programSize];

        for (int i = 0; i < programSize; i++) {
            programs[i] = (char) (i + 97);
        }
    }

    String danceOrder() {
        for (String danceMove : danceMoves) {
            switch (danceMove.charAt(0)) {
                case 's':
                    // shift left
                    int spin = Integer.parseInt(danceMove.substring(1));
                    spin = spin % 16;
                    char[] p2 = new char[programSize];
                    for (int i = 0; i < programSize; i++) {
                        int pos = (i - spin) % programSize;
                        if (pos < 0) {
                            pos = programSize + pos;
                        }
                        p2[i] = programs[pos];
                    }
                    programs = p2;
                    break;
                case 'x':
                    String[] s = danceMove.substring(1).split("/");
                    int posA = Integer.parseInt(s[0]);
                    int posB = Integer.parseInt(s[1]);
                    swap(programs, posA, posB);
                    break;
                case 'p':
                    char a = danceMove.charAt(1);
                    char b = danceMove.charAt(3);
                    posA = -1;
                    posB = -1;
                    for (int i = 0; i < programSize; i++) {
                        if (programs[i] == a) {
                            posA = i;
                        }
                        if (programs[i] == b) {
                            posB = i;
                        }
                    }
                    swap(programs, posA, posB);
                    break;
            }
        }
        return String.valueOf(programs);
    }

    String secondDanceOrder() {
        String firstResult;
        String result = "";
        int cycleLength = 0;
        // find cycle
        firstResult = danceOrder();
        while (!result.equals(firstResult)) {
            result = danceOrder();
            cycleLength++;
        }
        int cycleRemainder = 1000000000 % cycleLength;

        for (int i = 0; i < cycleRemainder - 1; i++) {
            result = danceOrder();
        }
        return result;
    }

    private void swap(char[] chars, int posA, int posB) {
        char temp = chars[posA];
        chars[posA] = chars[posB];
        chars[posB] = temp;
    }
}
