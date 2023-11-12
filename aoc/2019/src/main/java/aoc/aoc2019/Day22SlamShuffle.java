package aoc.aoc2019;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day22SlamShuffle {

    private final List<String> inputLines;

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
            log.debug(stack.cards.toString());
        }

        if (numberOfCards == 10) {
            return 0;
        } else {
            return stack.cards.indexOf(2019);
        }
    }

    String numberOnCard(String numberOfCards, String numberOfShuffles) {
        NewStack ns = new NewStack(new BigInteger(numberOfCards));

        ns.shuffle(new BigInteger(numberOfShuffles));
        return ns.cardAtPosition(new BigInteger("2020")).toString();
    }

    static class Stack {
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

    class NewStack {
        final BigInteger MOD;
        BigInteger offset;
        BigInteger increment;

        public NewStack(BigInteger numberOfCards) {
            MOD = numberOfCards;
            offset = new BigInteger("0");
            increment = new BigInteger("1");
        }

        BigInteger cardAtPosition(BigInteger n) {
            // number = offset + increment * n
            return offset.add(increment.multiply(n)).mod(MOD);
        }

        void dealIntoNewStack() {
            // increment *= -1
            // offset += increment
            increment = increment.multiply(new BigInteger("-1"));
            offset = offset.add(increment).mod(MOD);

            assert (increment.compareTo(MOD) < 0);
            assert (offset.compareTo(MOD) < 0);
        }

        void cut(BigInteger n) {
            // offset += increment * n
            offset = offset.add(increment.multiply(n)).mod(MOD);

            assert (increment.compareTo(MOD) < 0);
            assert (offset.compareTo(MOD) < 0);
        }

        void dealWithIncrement(BigInteger n) {
            // increment *= inv(n)
            increment = increment.multiply(inv(n)).mod(MOD);

            assert (increment.compareTo(MOD) < 0);
            assert (offset.compareTo(MOD) < 0);
        }

        BigInteger inv(BigInteger n) {
            // inv(n) = pow(n, MOD-2, MOD)
            return n.modPow(MOD.subtract(new BigInteger("2")), MOD);
        }

        void shuffle(BigInteger iterations) {
            BigInteger offset_diff;
            BigInteger increment_mul;

            Pattern patternDealIntoNewStack = Pattern.compile("^deal into new stack$");
            Pattern patternDealWithIncrement = Pattern.compile("^deal with increment (-?\\d+)$");
            Pattern patternCut = Pattern.compile("^cut (-?\\d+)$");

            for (String line : inputLines) {
                Matcher matcher = patternDealIntoNewStack.matcher(line);
                if (matcher.find()) {
                    dealIntoNewStack();
                } else {
                    matcher = patternDealWithIncrement.matcher(line);
                    if (matcher.find()) {
                        dealWithIncrement(new BigInteger(matcher.group(1)));
                    } else {
                        matcher = patternCut.matcher(line);
                        if (matcher.find()) {
                            cut(new BigInteger(matcher.group(1)));
                        }
                    }
                }
            }
            increment_mul = increment;
            offset_diff = offset;

            if (!iterations.equals(new BigInteger("1"))) {
                // increment = pow(increment_mul, n, MOD)
                increment = increment_mul.modPow(iterations, MOD);
                // offset = offset_diff * (1 - pow(increment_mul, iterations, MOD)) * inv(1 - increment_mul)
                offset = offset_diff.multiply(
                        (new BigInteger("1").subtract(increment_mul.modPow(iterations, MOD)))).multiply(
                        inv(new BigInteger("1").subtract(increment_mul)));

            }
            assert (increment.compareTo(MOD) < 0);
            assert (offset.compareTo(MOD) < 0);
        }
    }
}
