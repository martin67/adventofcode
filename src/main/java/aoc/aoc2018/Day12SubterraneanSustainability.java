package aoc.aoc2018;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.*;

@Slf4j
public class Day12SubterraneanSustainability {

    LinkedList<Pot> pots = new LinkedList<>();
    List<Note> notes = new ArrayList<>();
    int potOffset;
    private Tunnel tunnel;
    private int generation = 0;

    public Day12SubterraneanSustainability(List<String> inputLines) {

        // First row is the initial state
        // initial state: #..#.#..##......###...###
        for (char c : inputLines.get(0).toCharArray()) {
            if (c == '.' || c == '#') {
                pots.add(new Pot(c));
            }
        }
        log.info("Reading {} states", pots.size());


        // Then it's the growth patterns
        // ...## => #
        for (String line : inputLines.subList(2, inputLines.size())) {
            notes.add(new Note(line.substring(0, 5), line.charAt(9)));
        }
        log.info("Reading {}", notes.size());
    }

    public Day12SubterraneanSustainability(String input, int totalGenerations) {
        tunnel = new Tunnel();
        tunnel.init(input);
    }

    long problem1(long generations) {
        for (long i = 0; i < generations; i++) {
            grow();
        }

        int checksum = 0;
        for (int i = 0; i < pots.size(); i++)
            if (pots.get(i).getState() == '#') {
                checksum += i + potOffset;
            }
        return checksum;
    }

    public String ComputeBigPlantSum(BigInteger generations) {
        // After a while, the pattern is constant, only moving to the right
        //tunnel.print();
        int previousSum = -1;
        int currentSum = tunnel.sumOfPlants(0);
        while (previousSum != currentSum) {
            previousSum = currentSum;
            tunnel.grow();
            generation++;
            currentSum = tunnel.sumOfPlants(0);
            //tunnel.print();
        }

        // Now the tunnel does not change. Check how many plants there are
        int numberOfPlants = tunnel.numberOfPlants();

        log.info("After " + (generation - 1) + " generations, the plant pattern does not change");
        log.info("There are now " + numberOfPlants + " plants in the pattern");
        log.info("For generation " + generation + ", the value is " + currentSum);
        log.info("With offset " + tunnel.getPotOffset());
        log.info("bigsum: " + tunnel.sumOfPlantsWithOffset());
        BigInteger generationsLeft = generations.subtract(new BigInteger(String.valueOf(generation)));
        log.info("Generations left to go: " + generationsLeft);

        BigInteger sumLeft = generationsLeft.multiply(new BigInteger(String.valueOf(numberOfPlants)));
        log.info("Final sum: " + sumLeft);

        BigInteger totalSum = sumLeft.add(new BigInteger(String.valueOf(tunnel.sumOfPlantsWithOffset())));
        log.info("Total sum: " + totalSum);

        return totalSum.toString();

    }

    void grow() {
        // add four empty pots to the left of the list and four to the right
        for (int i = 0; i < 4; i++) {
            pots.addFirst(new Pot('.'));
        }
        for (int i = 0; i < 4; i++) {
            pots.addLast(new Pot('.'));
        }

        // Create a new temporary potlist to store results
        LinkedList<Pot> newPots = new LinkedList<>();

        // Start comparing patterns from pos 2 till end-2
        for (int i = 2; i < pots.size() - 2; i++) {
            //log.info("Checking pot " + (i-4) + " for pattern match");

            Pot newPot = new Pot('.');
            // Go through all patterns and check if there's a hit
            for (Note note : notes) {
                if (note.getPattern().charAt(0) == pots.get(i - 2).getState() &&
                        note.getPattern().charAt(1) == pots.get(i - 1).getState() &&
                        note.getPattern().charAt(2) == pots.get(i).getState() &&
                        note.getPattern().charAt(3) == pots.get(i + 1).getState() &&
                        note.getPattern().charAt(4) == pots.get(i + 2).getState()) {
                    //log.info("Found match on pot " + (i-4) + " for note: " + note);
                    newPot.setState(note.getNextState());
                }
            }

            newPots.add(newPot);
        }
        pots = newPots;

        // remove empty pots on each end
        ListIterator<Pot> pi = pots.listIterator();
        int potsAddedToStart = -2;
        while (pi.hasNext() && pi.next().getState() == '.') {
            pi.remove();
            potsAddedToStart++;
        }
        //log.info("Pots added to start: " + potsAddedToStart);
        potOffset += potsAddedToStart;

        pi = pots.listIterator(pots.size());
        while (pi.hasPrevious() && pi.previous().getState() == '.') {
            pi.remove();
        }
    }

    @Data
    @AllArgsConstructor
    static class Pot {
        char state;
    }

    @Data
    @AllArgsConstructor
    static class Note {
        String pattern;
        char nextState;
    }

    @Data
    static
    class Tunnel {
        LinkedList<Pot> pots = new LinkedList<>();
        List<Note> notes = new ArrayList<>();
        int potOffset;

        void init(String input) {
            List<String> inputStrings = Arrays.stream(input.trim().split("\\n+")).toList();

            // First row is the initial state
            // initial state: #..#.#..##......###...###
            for (char c : inputStrings.get(0).toCharArray()) {
                if (c == '.' || c == '#') {
                    pots.add(new Pot(c));
                }
            }
            log.info("Reading " + pots.size() + " states.");


            // Then it's the growth patterns
            // ...## => #
            for (String line : inputStrings.subList(2, inputStrings.size())) {
                notes.add(new Note(line.substring(0, 5), line.charAt(9)));
            }
            log.info("Reading " + notes.size() + " notes.");
        }

        void grow() {
            // add four empty pots to the left of the list and four to the right
            for (int i = 0; i < 4; i++) {
                pots.addFirst(new Pot('.'));
            }
            for (int i = 0; i < 4; i++) {
                pots.addLast(new Pot('.'));
            }

            // Create a new temporary potlist to store results
            LinkedList<Pot> newPots = new LinkedList<>();

            // Start comparing patterns from pos 2 till end-2
            for (int i = 2; i < pots.size() - 2; i++) {
                //log.info("Checking pot " + (i-4) + " for pattern match");
                Pot pot = pots.get(i);

                Pot newPot = new Pot('.');
                // Go through all patterns and check if there's a hit
                for (Note note : notes) {
                    if (note.getPattern().charAt(0) == pots.get(i - 2).getState() &&
                            note.getPattern().charAt(1) == pots.get(i - 1).getState() &&
                            note.getPattern().charAt(2) == pots.get(i).getState() &&
                            note.getPattern().charAt(3) == pots.get(i + 1).getState() &&
                            note.getPattern().charAt(4) == pots.get(i + 2).getState()) {
                        //log.info("Found match on pot " + (i-4) + " for note: " + note);
                        newPot.setState(note.getNextState());
                    }
                }

                newPots.add(newPot);
            }
            pots = newPots;
            trim();
        }

        void trim() {
            // remove empty pots on each end
            ListIterator<Pot> pi = pots.listIterator();
            int potsAddedToStart = -2;
            while (pi.hasNext() && pi.next().getState() == '.') {
                pi.remove();
                potsAddedToStart++;
            }
            //log.info("Pots added to start: " + potsAddedToStart);
            potOffset += potsAddedToStart;

            pi = pots.listIterator(pots.size());
            while (pi.hasPrevious() && pi.previous().getState() == '.') {
                pi.remove();
            }
        }

        int sumOfPlants(int offset) {
            int checksum = 0;

            for (int i = 0; i < pots.size(); i++)
                if (pots.get(i).getState() == '#') {
                    checksum += i + offset;
                }
            return checksum;
        }

        int sumOfPlantsWithOffset() {
            int checksum = 0;

            for (int i = 0; i < pots.size(); i++)
                if (pots.get(i).getState() == '#') {
                    checksum += i + potOffset;
                }
            return checksum;
        }

        int numberOfPlants() {
            int plants = 0;

            for (Pot pot : pots) {
                if (pot.getState() == '#') {
                    plants++;
                }
            }
            return plants;
        }
    }

}
