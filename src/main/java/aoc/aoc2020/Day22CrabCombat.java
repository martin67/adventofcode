package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day22CrabCombat {
    Queue<Integer> deck1 = new LinkedList<>();
    Queue<Integer> deck2 = new LinkedList<>();
    Map<String, Integer> previousGames = new HashMap<>();

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
        Queue<Integer> winner;
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

        Queue<Integer> winner;
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

    int recursiveCombat(int game, Queue<Integer> deck1, Queue<Integer> deck2) {
        if (previousGames.containsKey(deck1.toString() + deck2.toString())) {
            //log.info("Found game already played (nr {})", previousGames.size());
            return previousGames.get(deck1.toString() + deck2.toString());
        }
        Queue<Integer> initialDeck1 = new LinkedList<>(deck1);
        Queue<Integer> initialDeck2 = new LinkedList<>(deck2);

        int round = 1;
        Set<String> previousDecks1 = new HashSet<>();
        Set<String> previousDecks2 = new HashSet<>();

        while (deck1.size() > 0 && deck2.size() > 0) {
            //log.info("-- Round {} (Game {}) --", round, game);
            //log.info("Player 1's deck: {}", deck1);
            //log.info("Player 2's deck: {}", deck2);
            if (previousDecks1.contains(deck1.toString()) && previousDecks2.contains(deck2.toString())) {
                // Infinite game check
                //log.info("Player 1 wins the round (infinity check)!");
                previousGames.put(initialDeck1.toString() + initialDeck2.toString(), 1);
                return 1;
            }

            int card1 = deck1.poll();
            int card2 = deck2.poll();
            //log.info("Player 1 plays {}", card1);
            //log.info("Player 2 plays {}", card2);
            previousDecks1.add(deck1.toString());
            previousDecks2.add(deck2.toString());

            if (deck1.size() >= card1 && deck2.size() >= card2) {
                Queue<Integer> deck1copy = new LinkedList<>(deck1);
                Queue<Integer> deck2copy = new LinkedList<>(deck2);
                //log.info("Playing a sub-game to determine the winner...");
                int winner = recursiveCombat(game + 1, deck1copy, deck2copy);
                if (winner == 1) {
                    //log.info("Player 1 wins the round!");
                    deck1.add(card1);
                    deck1.add(card2);
                } else {
                    //log.info("Player 2 wins the round!");
                    deck2.add(card2);
                    deck2.add(card1);
                }
            } else if (card1 > card2) {
                //log.info("Player 1 wins the round!");
                deck1.add(card1);
                deck1.add(card2);
            } else {
                //log.info("Player 2 wins the round!");
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
        previousGames.put(initialDeck1.toString() + initialDeck2.toString(), winner);
        return winner;
    }
}
