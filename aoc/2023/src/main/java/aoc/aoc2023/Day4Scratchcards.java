package aoc.aoc2023;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day4Scratchcards {
    final List<Card> cards = new ArrayList<>();

    public Day4Scratchcards(List<String> inputLines) {
        var pattern = Pattern.compile("Card\\s+(\\d+):\\s+(.*) \\|\\s+(.*)");
        Matcher matcher;

        for (String line : inputLines) {
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                var card = new Card();
                card.id = Integer.parseInt(matcher.group(1));
                for (String number : matcher.group(2).split("\\s+")) {
                    card.winningNumbers.add(Integer.parseInt(number));
                }
                for (String number : matcher.group(3).split("\\s+")) {
                    card.numbers.add(Integer.parseInt(number));
                }
                cards.add(card);
            }
        }
    }

    public int problem1() {
        int points = 0;
        for (var card : cards) {
            int hits = 0;
            for (int number : card.numbers) {
                if (card.winningNumbers.contains(number)) {
                    hits++;
                }
            }
            points += (int) Math.pow(2, hits - 1);
        }
        return points;
    }

    public int problem2() {
        for (var card : cards) {
            int hits = 0;
            for (int number : card.numbers) {
                if (card.winningNumbers.contains(number)) {
                    hits++;
                }
            }
            for (int i = 0; i < hits; i++) {
                cards.get(card.id + i).instances += card.instances;
            }
        }

        return cards.stream().mapToInt(Card::getInstances).sum();
    }

    @Data
    static class Card {
        int id;
        int instances = 1;
        List<Integer> winningNumbers = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
    }
}
