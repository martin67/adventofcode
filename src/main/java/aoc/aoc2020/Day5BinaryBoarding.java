package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
public class Day5BinaryBoarding {
    final List<BoardingPass> boardingPasses = new ArrayList<>();

    public Day5BinaryBoarding(List<String> inputLines) {
        for (String line : inputLines) {
            boardingPasses.add(new BoardingPass(line));
        }
    }

    int highestId() {
        return boardingPasses.stream()
                .max(Comparator.comparing(BoardingPass::id))
                .orElseThrow(NoSuchElementException::new).id();
    }

    int myId() {
        List<BoardingPass> sorted = boardingPasses.stream()
                .sorted(Comparator.comparing(BoardingPass::id))
                .collect(Collectors.toList());

        for (int i = 1; i < sorted.size(); i++) {
            if (sorted.get(i - 1).id() != sorted.get(i).id() - 1) {
                return sorted.get(i).id() - 1;
            }
        }
        return 0;
    }

    static class BoardingPass {
        final String code;

        public BoardingPass(String code) {
            this.code = code;
        }

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
