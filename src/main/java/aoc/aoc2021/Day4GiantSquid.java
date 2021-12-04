package aoc.aoc2021;

import aoc.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day4GiantSquid {

    List<Integer> draws = new ArrayList<>();
    List<Board> boards = new ArrayList<>();

    public Day4GiantSquid(List<String> inputLines) {
        int lineNumber = 0;
        List<String> boardLines = null;

        for (String line : inputLines) {

            if (lineNumber == 0) {
                draws = Stream.of(line.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            } else {
                if (line.isEmpty()) {
                    boardLines = new ArrayList<>();
                } else {
                    boardLines.add(line);
                    if (boardLines.size() == 5) {
                        boards.add(new Board(boardLines));
                    }
                }
            }
            lineNumber++;
        }

    }

    int problem1() {
        for (int draw : draws) {
            for (Board board : boards) {
                board.addDraw(draw);
                if (board.isBingo()) {
                    log.info("Bingo!");
                    return board.getScore(draw);
                }
            }
        }
        return 0;
    }

    int problem2() {
        Map<Board, Integer> winnerDraws = new HashMap<>();
        Map<Board, Integer> winnerScores = new HashMap<>();

        int round = 0;
        for (int draw : draws) {
            for (Board board : boards) {
                board.addDraw(draw);
                if (board.isBingo()) {
                    if (!winnerDraws.containsKey(board)) {
                        winnerDraws.put(board, round);
                        winnerScores.put(board, board.getScore(draw));
                    }
                }
            }
            round++;
        }

        // get the last to win,
        Board lastWinner = Collections.max(winnerDraws.entrySet(), Map.Entry.comparingByValue()).getKey();
        return winnerScores.get(lastWinner);
    }

    static class Board {
        Map<Integer, Position> numbers = new HashMap<>();
        Set<Position> hits = new HashSet<>();

        public Board(List<String> in) {
            for (int y = 0; y < 5; y++) {
                String[] test = in.get(y).trim().split("\\s+");
                List<Integer> values = Stream.of(in.get(y).trim().split("\\s+"))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                for (int x = 0; x < 5; x++) {
                    numbers.put(values.get(x), new Position(x, y));
                }
            }
        }

        void addDraw(int draw) {
            if (numbers.containsKey(draw)) {
                hits.add(numbers.get(draw));
            }
        }

        boolean isBingo() {
            boolean bingo = false;

            for (int y = 0; y < 5; y++) {
                int numberOfHits = 0;
                for (int x = 0; x < 5; x++) {
                    if (hits.contains(new Position(x, y))) {
                        numberOfHits++;
                    }
                }
                if (numberOfHits == 5) {
                    bingo = true;
                }
            }

            for (int x = 0; x < 5; x++) {
                int numberOfHits = 0;
                for (int y = 0; y < 5; y++) {
                    if (hits.contains(new Position(x, y))) {
                        numberOfHits++;
                    }
                }
                if (numberOfHits == 5) {
                    bingo = true;
                }
            }

            return bingo;
        }

        int getScore(int draw) {
            int sum = 0;
            for (Map.Entry<Integer, Position> entry : numbers.entrySet()) {
                if (!hits.contains(entry.getValue())) {
                    sum += entry.getKey();
                }
            }
            return sum * draw;
        }
    }

}
