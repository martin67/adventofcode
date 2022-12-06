package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day22CrabCombat {
    private final Deque<Integer> deck1 = new ArrayDeque<>();
    private final Deque<Integer> deck2 = new ArrayDeque<>();

    public Day22CrabCombat(List<String> inputLines) {
        Queue<Integer> player = null;

        for (String line : inputLines) {
            if (line.equals("Player 1:")) {
                player = deck1;
            } else if (line.equals("Player 2:")) {
                player = deck2;
            } else if (line.length() > 0) {
                player.add(Integer.parseInt(line));
            }
        }
    }

    int problem1() {
        int round = 1;
        while (deck1.size() > 0 && deck2.size() > 0) {
            log.info("Round {}", round);
            log.info("Player 1's deck: {}", deck1);
            log.info("Player 2's deck: {}", deck2);
            int card1 = deck1.poll();
            int card2 = deck2.poll();
            log.info("Player 1 plays {}", card1);
            log.info("Player 2 plays {}", card2);
            if (card1 > card2) {
                log.info("Player 1 wins the round!");
                deck1.add(card1);
                deck1.add(card2);
            } else {
                log.info("Player 2 wins the round!");
                deck2.add(card2);
                deck2.add(card1);
            }
            round++;
        }
        Deque<Integer> winner;
        if (deck1.size() > 0) {
            winner = deck1;
        } else {
            winner = deck2;
        }

        int score = 0;
        for (int i = winner.size(); i > 0; i--) {
            score += winner.poll() * i;
        }
        return score;
    }

    int problem2() {
        recursiveCombat(1, deck1, deck2);

        Deque<Integer> winner;
        if (deck1.size() > 0) {
            winner = deck1;
        } else {
            winner = deck2;
        }

        int score = 0;
        for (int i = winner.size(); i > 0; i--) {
            score += winner.poll() * i;
        }
        return score;
    }

    int recursiveCombat(int game, Deque<Integer> deck1, Deque<Integer> deck2) {
        Set<String> previousGames = new HashSet<>();
        int round = 1;

        while (deck1.size() > 0 && deck2.size() > 0) {
            log.debug("-- Round {} (Game {}) --", round, game);
            log.debug("Player 1's deck: {}", deck1);
            log.debug("Player 2's deck: {}", deck2);
            // Infinite game check
            if (previousGames.contains(deck1 + "-" + deck2)) {
                log.debug("Player 1 wins round {} (infinity check)!", round);
                return 1;
            } else {
                previousGames.add(deck1 + "-" + deck2);
            }

            int card1 = deck1.poll();
            int card2 = deck2.poll();
            log.debug("Player 1 plays {}", card1);
            log.debug("Player 2 plays {}", card2);

            if (deck1.size() >= card1 && deck2.size() >= card2) {
                Deque<Integer> deck1copy = new ArrayDeque<>();
                Iterator<Integer> a = deck1.iterator();
                for (int i = 0; i < card1; i++) {
                    deck1copy.add(a.next());
                }
                Deque<Integer> deck2copy = new ArrayDeque<>();
                Iterator<Integer> b = deck2.iterator();
                for (int i = 0; i < card2; i++) {
                    deck2copy.add(b.next());
                }
                log.debug("Playing a sub-game to determine the winner...");
                int winner = recursiveCombat(game + 1, deck1copy, deck2copy);
                if (winner == 1) {
                    log.debug("Player 1 wins the round!");
                    deck1.add(card1);
                    deck1.add(card2);
                } else {
                    log.debug("Player 2 wins the round!");
                    deck2.add(card2);
                    deck2.add(card1);
                }
            } else if (card1 > card2) {
                log.debug("Player 1 wins the round!");
                deck1.add(card1);
                deck1.add(card2);
            } else {
                log.debug("Player 2 wins the round!");
                deck2.add(card2);
                deck2.add(card1);
            }
            round++;
        }

        int winner;
        if (deck1.size() > 0) {
            winner = 1;
        } else {
            winner = 2;
        }

        return winner;
    }
}
