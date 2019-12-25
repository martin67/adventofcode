package aoc2019;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day22SlamShuffle {

    class Stack {
        List<Integer> cards = new ArrayList<>();

        public Stack(int numberOfCards) {
            for (int i = 0; i < numberOfCards; i++) {
                cards.add(i);
            }
        }

        void dealIntoNewStack() {
            Collections.reverse(cards);
        }

        void dealWithIncrement(int increment) {
            List<Integer> newDeck = new ArrayList<>(cards);
            for (int i = 0; i < cards.size(); i++) {
                int newPos = i * increment;
                while (newPos > cards.size()) {
                    newPos -= cards.size();
                }
                newDeck.set(newPos, cards.get(i));
            }
            cards = newDeck;
        }

        void cut(int n) {
            List<Integer> newDeck = new ArrayList<>();
            if (n > 0) {
                newDeck.addAll(cards.subList(n, cards.size()));
                newDeck.addAll(cards.subList(0, n));
            } else {
                n = -n;
                newDeck.addAll(cards.subList(cards.size() - n, cards.size()));
                newDeck.addAll(cards.subList(0, cards.size() - n));
            }
            cards = newDeck;
        }
    }

    List<String> inputLines;

    public Day22SlamShuffle(List<String> inputLines) {
        this.inputLines = inputLines;
    }


    int cardPosition(int numberOfCards) {
        Stack stack = new Stack(numberOfCards);

        Pattern patternDealIntoNewStack = Pattern.compile("^deal into new stack$");
        Pattern patternDealWithIncrement = Pattern.compile("^deal with increment (-?\\d+)$");
        Pattern patternCut = Pattern.compile("^cut (-?\\d+)$");

        for (String line : inputLines) {
            log.info(line);
            Matcher matcher = patternDealIntoNewStack.matcher(line);
            if (matcher.find()) {
                stack.dealIntoNewStack();
            } else {
                matcher = patternDealWithIncrement.matcher(line);
                if (matcher.find()) {
                    stack.dealWithIncrement(Integer.parseInt(matcher.group(1)));
                } else {
                    matcher = patternCut.matcher(line);
                    if (matcher.find()) {
                        stack.cut(Integer.parseInt(matcher.group(1)));
                    }
                }
            }
            log.info(stack.cards.toString());
        }

        if (numberOfCards == 10) {
            return 0;
        } else {
            return stack.cards.indexOf(2019);
        }
    }
}
