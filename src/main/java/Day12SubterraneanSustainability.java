import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day12SubterraneanSustainability {

    Tunnel tunnel;
    int generation = 0;


    @Data
    @AllArgsConstructor
    class Pot {
        char state;
    }

    @Data
    @AllArgsConstructor
    class Note {
        String pattern;
        char nextState;
    }

    @Data
    class Tunnel {
        LinkedList<Pot> pots = new LinkedList<>();
        List<Note> notes = new ArrayList<>();
        int potOffset;

        void init(String input) {
            List<String> inputStrings = Arrays.stream(input.trim().split("\\n+"))
                    .collect(Collectors.toList());

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
            for (String line : inputStrings.subList(1, inputStrings.size())) {
                notes.add(new Note( line.substring(0,5), line.charAt(9)));
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

        void print() {

            System.out.printf("%2d: ", generation);
            for(Pot pot: pots) {
                System.out.print(pot.getState());
            }
            System.out.printf("  Offset: %d", potOffset);
            System.out.println();
        }

        int sumOfPlants() {
            int checksum = 0;

            for (int i = 0; i < pots.size(); i++)
                if (pots.get(i).getState() == '#') {
                    checksum += i + potOffset;
                    //log.info("Checksum: " + checksum + ", i: " + i);
                }
            return checksum;
        }
    }

    public Day12SubterraneanSustainability(String input) {
        tunnel = new Tunnel();
        tunnel.init(input);

    }

    public void CheckInitialState() {
        tunnel.print();
        for (int i = 0; i < 20; i++) {
            tunnel.grow();
            generation++;
            log.info("potOffset: " + tunnel.getPotOffset());
            tunnel.print();
        }
    }

    public int ComputePlantSum() {
        tunnel.print();
        for (int i = 0; i < 20; i++) {
            tunnel.grow();
            generation++;
            tunnel.print();
        }

        return tunnel.sumOfPlants();
    }
}
