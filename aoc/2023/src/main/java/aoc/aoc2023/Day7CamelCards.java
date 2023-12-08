package aoc.aoc2023;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day7CamelCards {
    final List<Hand> hands = new ArrayList<>();

    public Day7CamelCards(List<String> inputLines) {
        for (String line : inputLines) {
            String[] s = line.split(" ");
            hands.add(new Hand(s[0], Integer.parseInt(s[1])));
        }
    }

    public int problem1() {
        hands.sort(new BasicHandComparator());
        return hands.stream()
                .mapToInt(h -> h.bid * (hands.size() - hands.indexOf(h)))
                .sum();
    }

    public int problem2() {
        hands.sort(new JokerHandComparator());
        return hands.stream()
                .mapToInt(h -> h.bid * (hands.size() - hands.indexOf(h)))
                .sum();
    }

    record Hand(String cards, int bid) {
        Type getType() {
            Map<Character, Integer> cardMap = new HashMap<>();
            for (char c : cards.toCharArray()) {
                cardMap.merge(c, 1, Integer::sum);
            }

            return switch (cardMap.size()) {
                case 1 -> Type.FIVE_OF_A_KIND;
                case 2 -> cardMap.containsValue(4) ? Type.FOUR_OF_A_KIND : Type.FULL_HOUSE;
                case 3 -> cardMap.containsValue(3) ? Type.THREE_OF_A_KIND : Type.TWO_PAIR;
                case 4 -> Type.ONE_PAIR;
                case 5 -> Type.HIGH_CARD;
                default -> throw new IllegalStateException();
            };
        }

        Type getTypeWithJoker() {
            int numberOfJokers = (int) cards.chars().filter(c -> c == 'J').count();
            if (numberOfJokers == 0) {
                return getType();
            }
            if (numberOfJokers == 5) {
                return Type.FIVE_OF_A_KIND;
            }

            return switch (getType()) {
                case FIVE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE -> Type.FIVE_OF_A_KIND;
                case THREE_OF_A_KIND -> switch (numberOfJokers) {
                    case 1, 3 -> Type.FOUR_OF_A_KIND;
                    case 2 -> Type.FIVE_OF_A_KIND;
                    default -> throw new IllegalStateException();
                };
                case TWO_PAIR -> switch (numberOfJokers) {
                    case 1 -> Type.FULL_HOUSE;
                    case 2 -> Type.FOUR_OF_A_KIND;
                    default -> throw new IllegalStateException();
                };
                case ONE_PAIR -> Type.THREE_OF_A_KIND;
                case HIGH_CARD -> Type.ONE_PAIR;
            };
        }
    }

    static class BasicHandComparator implements Comparator<Hand> {
        static final String CARD_VALUES = "AKQJT98765432";

        @Override
        public int compare(Hand o1, Hand o2) {
            int comparison = o1.getType().compareTo(o2.getType());
            if (comparison == 0) {
                for (int i = 0; i < 5; i++) {
                    char a = o1.cards.charAt(i);
                    char b = o2.cards.charAt(i);

                    if (a != b) {
                        comparison = Integer.compare(CARD_VALUES.indexOf(a), CARD_VALUES.indexOf(b));
                        break;
                    }
                }
            }
            return comparison;
        }
    }

    static class JokerHandComparator implements Comparator<Hand> {
        static final String CARD_VALUES = "AKQT98765432J";

        @Override
        public int compare(Hand o1, Hand o2) {
            int comparison = o1.getTypeWithJoker().compareTo(o2.getTypeWithJoker());
            if (comparison == 0) {
                for (int i = 0; i < 5; i++) {
                    char a = o1.cards.charAt(i);
                    char b = o2.cards.charAt(i);

                    if (a != b) {
                        comparison = Integer.compare(CARD_VALUES.indexOf(a), CARD_VALUES.indexOf(b));
                        break;
                    }
                }
            }
            return comparison;
        }
    }

    enum Type {
        FIVE_OF_A_KIND,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        THREE_OF_A_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD
    }
}
