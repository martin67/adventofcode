import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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
        char state;
    }

    class Tunnel {
        List<Pot> Pots = new LinkedList<>();
        List<Note> Notes = new ArrayList<>();

        void init(String input) {
            List<String> inputStrings = Arrays.stream(input.trim().split("\\n+"))
                    .collect(Collectors.toList());

            // First row is the initial state
            // initial state: #..#.#..##......###...###
            for (char c : inputStrings.get(0).toCharArray()) {
              if (c == '.' || c == '#') {
                  Pots.add(new Pot(c));
              }
            }
            log.info("Reading " + Pots.size() + " states.");

            // Then it's the growth patterns
            // ...## => #
            for (String line : inputStrings.subList(1, inputStrings.size())) {
                Notes.add(new Note( line.substring(0,5), line.charAt(9)));
            }
            log.info("Reading " + Notes.size() + " notes.");
        }

        void grow() {
            for (Pot pot : Pots) {

            }
        }

        void print() {

            System.out.printf("%2d: ", generation);
            for(Pot pot: Pots) {
                System.out.print(pot.getState());
            }
            System.out.println();
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
            tunnel.print();
        }
    }
}
