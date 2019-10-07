package adventofcode2016;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10BalanceBots {

    @Data
    static class Bot {
        List<Integer> chips = new ArrayList<>();

        Bot(int chip) {
            chips.add(chip);
        }

        void add(int chip) {
            chips.add(chip);
        }

        int lower() {
            return chips.stream().min(Integer::compare).orElse(0);
        }

        int higher() {
            return chips.stream().max(Integer::compare).orElse(0);
        }
    }

    @Data
    @AllArgsConstructor
    static class Instruction {
        int sourceBot;
        int lowDestination;
        int highDestination;
    }

    private HashMap<Integer, Bot> bots = new HashMap<>();
    private Set<Instruction> instructions = new HashSet<>();

    public Day10BalanceBots(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));
        Pattern initPattern = Pattern.compile("^value (\\d+) goes to bot (\\d+)$");
        Pattern instructionPattern = Pattern.compile("^bot (\\d+) gives low to (bot|output) (\\d+) and high to (bot|output) (\\d+)$");

        for (String row : inputStrings) {
            Matcher matcher = initPattern.matcher(row);
            if (matcher.find()) {
                int value = Integer.parseInt(matcher.group(1));
                int botNumber = Integer.parseInt(matcher.group(2));
                if (bots.containsKey(botNumber)) {
                    bots.get(botNumber).add(value);
                } else {
                    bots.put(botNumber, new Bot(value));
                }
            }
            matcher = instructionPattern.matcher(row);
            if (matcher.find()) {
                int sourceBot = Integer.parseInt(matcher.group(1));
                int lowDestination = -1;
                if (matcher.group(2).equals("bot")) {
                    lowDestination = Integer.parseInt(matcher.group(3));
                }
                int highDestination = -1;
                if (matcher.group(4).equals("bot")) {
                    highDestination = Integer.parseInt(matcher.group(5));
                }
                instructions.add(new Instruction(sourceBot, lowDestination, highDestination));
            }
            // value 41 goes to bot 12

            //bot 113 gives low to bot 127 and high to bot 196
            //bot 169 gives low to output 20 and high to bot 83
        }
    }

    int botNumber(int compareOne, int compareTwo) {

        boolean done = false;
        while (!done) {
            for (Iterator<Instruction> i = instructions.iterator(); i.hasNext(); ) {
                Instruction instruction = i.next();
                // Find sourceBots that have both chips available
                if (bots.containsKey(instruction.sourceBot) && bots.get(instruction.sourceBot).chips.size() == 2) {
                    Bot sourceBot = bots.get(instruction.sourceBot);
                    int lowDestination = instruction.lowDestination;
                    int highDestination = instruction.highDestination;
                    System.out.printf("Bot: %d, putting lower chip %d to bot %d and higher chip %d to bot %d.\n",
                            instruction.sourceBot,
                            sourceBot.lower(), lowDestination,
                            sourceBot.higher(), highDestination);

                    if (lowDestination != -1) {
                        if (bots.containsKey(lowDestination)) {
                            bots.get(lowDestination).add(sourceBot.lower());
                        } else {
                            bots.put(lowDestination, new Bot(sourceBot.lower()));
                        }
                    }

                    if (highDestination != -1) {
                        if (bots.containsKey(highDestination)) {
                            bots.get(highDestination).add(sourceBot.higher());
                        } else {
                            bots.put(highDestination, new Bot(sourceBot.higher()));
                        }
                    }

                    i.remove();
                }
            }

            if (instructions.isEmpty()) {
                done = true;
            }
        }

        // Find target bot
        List target = new ArrayList<>();
        target.add(compareOne);
        target.add(compareTwo);
        Optional<Map.Entry<Integer, Bot>> tt = Optional.ofNullable(bots.entrySet().stream().filter(e -> e.getValue().chips.containsAll(target)).findFirst().orElse(null));
        return tt.get().getKey();
    }
}
