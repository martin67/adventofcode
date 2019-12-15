package aoc2019;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day14SpaceStoichiometry {

    @Data
    @AllArgsConstructor
    class Chemical {
        String name;
    }

    @Data
    @AllArgsConstructor
    class ChemicalQuantity {
        Chemical chemical;
        int amount;
    }

    @Data
    @AllArgsConstructor
    class Reaction {
        Set<ChemicalQuantity> inputs;
        ChemicalQuantity output;
    }

    Set<Chemical> chemicals = new HashSet<>();
    Set<Reaction> reactions = new HashSet<>();

    public Day14SpaceStoichiometry(List<String> inputLines) {
        Pattern pattern = Pattern.compile("^(((\\d+) (\\w+),? )+)=> (?<outputAmount>\\d+) (?<outputChemical>\\w+)$");
        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                Set<ChemicalQuantity> inputs = new HashSet<>();
                String indata = matcher.group(1);
                for (String quantity : indata.split(",")) {
                    String[] split = quantity.trim().split(" ");
                    Chemical inputChemical = new Chemical(split[1]);
                    chemicals.add(inputChemical);
                    ChemicalQuantity inputQuantity = new ChemicalQuantity(inputChemical, Integer.parseInt(split[0]));
                    inputs.add(inputQuantity);
                }
                Chemical outputChemical = new Chemical(matcher.group("outputChemical"));
                chemicals.add(outputChemical);
                ChemicalQuantity output = new ChemicalQuantity(outputChemical, Integer.parseInt(matcher.group("outputAmount")));
                reactions.add(new Reaction(inputs, output));
            }
        }
    }

    int minimumAmountOfOre() {
        log.info("C: {}", reactions);
        return 0;
    }
}
