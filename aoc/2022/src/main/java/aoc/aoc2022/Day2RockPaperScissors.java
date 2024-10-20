package aoc.aoc2022;

import java.util.List;
import java.util.Map;

import static aoc.aoc2022.Day2RockPaperScissors.GameType.*;
import static aoc.aoc2022.Day2RockPaperScissors.Shape.*;

public class Day2RockPaperScissors {

    final List<String> games;
    final Map<Shape, Integer> scores = Map.of(Rock, 1, Paper, 2, Scissors, 3);
    final Map<String, Shape> opponentShapes = Map.of("A", Rock, "B", Paper, "C", Scissors);
    final Map<String, Shape> playerShapes = Map.of("X", Rock, "Y", Paper, "Z", Scissors);
    final Map<String, GameType> gameTypes = Map.of("X", Lose, "Y", Draw, "Z", Win);

    Day2RockPaperScissors(List<String> inputLines) {
        this.games = inputLines;
    }

    int problem1() {
        int score = 0;
        for (String game : games) {
            score += play1(game);
        }
        return score;
    }

    int play1(String game) {
        var opponent = opponentShapes.get(game.substring(0, 1));
        var player = playerShapes.get(game.substring(2, 3));
        int score = 0;

        if (opponent.equals(player)) {
            score = 3 + scores.get(player);
        }
        if (opponent == Rock && player == Paper) {
            score = 6 + scores.get(player);
        }
        if (opponent == Rock && player == Scissors) {
            score = scores.get(player);
        }
        if (opponent == Paper && player == Rock) {
            score = scores.get(player);
        }
        if (opponent == Paper && player == Scissors) {
            score = 6 + scores.get(player);
        }
        if (opponent == Scissors && player == Rock) {
            score = 6 + scores.get(player);
        }
        if (opponent == Scissors && player == Paper) {
            score = scores.get(player);
        }
        return score;
    }

    int problem2() {
        int score = 0;
        for (String game : games) {
            score += play2(game);
        }
        return score;
    }

    int play2(String game) {
        Shape opponent = opponentShapes.get(game.substring(0, 1));
        GameType gameType = gameTypes.get(game.substring(2, 3));
        int score = 0;

        switch (gameType) {
            case Draw -> score = 3 + scores.get(opponent);
            case Lose -> {
                switch (opponent) {
                    case Rock -> score = scores.get(Scissors);
                    case Paper -> score = scores.get(Rock);
                    case Scissors -> score = scores.get(Paper);
                }
            }
            case Win -> {
                switch (opponent) {
                    case Rock -> score = 6 + scores.get(Paper);
                    case Paper -> score = 6 + scores.get(Scissors);
                    case Scissors -> score = 6 + scores.get(Rock);
                }
            }
        }
        return score;
    }

    enum Shape {Rock, Paper, Scissors}

    enum GameType {Lose, Draw, Win}
}
