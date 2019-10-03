package adventofcode2016;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Day4SecurityThroughObscurity {

    @Data
    @AllArgsConstructor
    static class Room {
        String name;
        int sectorId;
        String checksum;

        boolean real() {
            return checksum.equals(computeChecksum());
        }

        String computeChecksum() {
            Map<Character, Integer> characterFrequency = new HashMap<>();
            for (char c : name.toCharArray()) {
                if (characterFrequency.containsKey(c)) {
                    characterFrequency.put(c, characterFrequency.get(c) + 1);
                } else {
                    characterFrequency.put(c, 1);
                }
            }
            List<Character> checksum = characterFrequency.entrySet().stream()
                    .sorted(Comparator.comparing(Entry::getValue).reversed())
                    .map(Entry::getKey).collect(Collectors.toList());

            return checksum.toString();
        }
    }

    private Set<Room> rooms = new HashSet<>();

    public Day4SecurityThroughObscurity(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        Pattern pattern = Pattern.compile("^([\\w-]+)(\\d\\d\\d)\\[(\\w+)\\]$");

        for (String row : inputStrings) {
            Matcher matcher = pattern.matcher(row);
            if (matcher.find()) {
                String name = matcher.group(1).replace("-", "");
                int sectorId = Integer.parseInt(matcher.group(2));
                String checksum = matcher.group(3);
                rooms.add(new Room(name, sectorId, checksum));
            }
        }
    }

    long sectorIdSum() {
        return rooms.stream().filter(Room::real).count();
    }

}
