package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
public class Day5BinaryBoarding {
    final List<BoardingPass> boardingPasses = new ArrayList<>();

    Day5BinaryBoarding(List<String> inputLines) {
        for (String line : inputLines) {
            boardingPasses.add(new BoardingPass(line));
        }
    }

    int problem1() {
        return boardingPasses.stream()
                .max(Comparator.comparing(BoardingPass::id))
                .orElseThrow(NoSuchElementException::new).id();
    }

    int problem2() {
        var sortedPasses = boardingPasses.stream()
                .sorted(Comparator.comparing(BoardingPass::id)).toList();

        for (int i = 1; i < sortedPasses.size(); i++) {
            if (sortedPasses.get(i - 1).id() != sortedPasses.get(i).id() - 1) {
                return sortedPasses.get(i).id() - 1;
            }
        }
        return 0;
    }

    record BoardingPass(String code) {

        int row() {
            int maxRowNumber = 128;
            for (int i = 0; i < 7; i++) {
                if (code.charAt(i) == 'F') {
                    maxRowNumber -= Math.pow(2, 6 - i);
                }
            }
            return maxRowNumber - 1;
        }

        int column() {
            int maxColumnNumber = 8;
            for (int i = 0; i < 3; i++) {
                if (code.charAt(i + 7) == 'L') {
                    maxColumnNumber -= Math.pow(2, 2 - i);
                }
            }
            return maxColumnNumber - 1;
        }

        int id() {
            return row() * 8 + column();
        }
    }
}
