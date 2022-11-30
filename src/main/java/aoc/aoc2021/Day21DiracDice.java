package aoc.aoc2021;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day21DiracDice {
    final List<Player> players = new ArrayList<>();

    public Day21DiracDice(List<String> inputLines) {
        Pattern pattern = Pattern.compile("Player (\\d+) starting position: (\\d+)");
        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                players.add(new Player(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
            }
        }
    }

    int problem1() {
        Dice dice = new Dice();
        Player winner = null;

        while (winner == null) {
            for (Player player : players) {
                if (winner == null) {
                    int roll = dice.rollThree();
                    player.move(roll);
                    log.debug("Player {} rolls {} and moves to space {} for a total score of {}.",
                            player.id, roll, player.position, player.score);
                    if (player.score >= 1000) {
                        winner = player;
                    }
                }
            }
        }
        players.remove(winner);
        Player loser = players.get(0);
        return loser.score * dice.rolls;
    }

    int problem2() {
        return 0;
    }

    static class Player {
        final int id;
        int score;
        int position;

        public Player(int id, int position) {
            this.id = id;
            this.position = position;
        }

        void move(int distance) {
            position += distance;
            while (position > 10) {
                position -= 10;
            }
            score += position;
        }
    }

    static class Dice {
        int score = 0;
        int rolls;

        int roll() {
            score++;
            if (score > 100) {
                score = 1;
            }
            rolls++;
            return score;
        }

        int rollThree() {
            return roll() + roll() + roll();
        }
    }
}
