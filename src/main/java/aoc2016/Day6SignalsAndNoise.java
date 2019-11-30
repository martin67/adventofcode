package aoc2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day6SignalsAndNoise {

    private List<String> messages;

    public Day6SignalsAndNoise(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        messages = Files.readAllLines(Paths.get(fileName));
    }

    private List<Map<Character, Integer>> buildFrequencyMap() {
        List<Map<Character, Integer>> charFrequencies = new ArrayList<>();

        for (String row : messages) {
            int index = 0;
            for (Character c : row.toCharArray()) {
                if (charFrequencies.size() == index) {
                    charFrequencies.add(new HashMap<>());
                }
                if (charFrequencies.get(index).containsKey(c)) {
                    charFrequencies.get(index).put(c, charFrequencies.get(index).get(c) + 1);
                } else {
                    charFrequencies.get(index).put(c, 1);
                }
                index++;
            }
        }
        return charFrequencies;
    }

    String errorCorrectedVersion() {
        List<Map<Character, Integer>> charFrequencies = buildFrequencyMap();
        StringBuilder code = new StringBuilder();

        for (Map<Character, Integer> map : charFrequencies) {
            code.append(map.entrySet().stream().max(Comparator.comparing(Map.Entry<Character, Integer>::getValue)).map(Map.Entry::getKey).get());
        }

        return code.toString();
    }

    String originalMessage() {
        List<Map<Character, Integer>> charFrequencies = buildFrequencyMap();
        StringBuilder code = new StringBuilder();

        for (Map<Character, Integer> map : charFrequencies) {
            code.append(map.entrySet().stream().min(Comparator.comparing(Map.Entry<Character, Integer>::getValue)).map(Map.Entry::getKey).get());
        }

        return code.toString();
    }
}
