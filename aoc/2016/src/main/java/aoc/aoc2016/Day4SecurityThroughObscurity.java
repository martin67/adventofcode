package aoc.aoc2016;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Comparator.reverseOrder;

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

            for (char c : name.replace("-", "").toCharArray()) {
                if (characterFrequency.containsKey(c)) {
                    characterFrequency.put(c, characterFrequency.get(c) + 1);
                } else {
                    characterFrequency.put(c, 1);
                }
            }
            String checksum = characterFrequency.entrySet().stream()
                    .sorted(Comparator.comparing(Entry<Character, Integer>::getValue, reverseOrder())
                            .thenComparing(Entry::getKey))
                    .map(Entry::getKey)
                    .map(Object::toString)
                    .collect(Collectors.joining());

            return checksum.substring(0, 5);
        }

        String shiftDecipher() {
            StringBuilder sb = new StringBuilder();
            for (char c : name.toCharArray()) {
                if (c == '-') {
                    sb.append(' ');
                } else {
                    int newCharCode = c + (sectorId % 26);
                    if (newCharCode > 122) {
                        newCharCode -= 26;
                    }
                    sb.append((char) newCharCode);
                }
            }
            return sb.toString().trim();
        }
    }

    private final Set<Room> rooms = new HashSet<>();

    public Day4SecurityThroughObscurity(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        var pattern = Pattern.compile("^([\\w-]+)(\\d\\d\\d)\\[(\\w+)]$");

        for (String row : inputStrings) {
            var matcher = pattern.matcher(row);
            if (matcher.find()) {
                String name = matcher.group(1);
                int sectorId = Integer.parseInt(matcher.group(2));
                String checksum = matcher.group(3);
                rooms.add(new Room(name, sectorId, checksum));
            }
        }
    }

    long sectorIdSum() {
        return rooms.stream().filter(Room::real).mapToInt(Room::getSectorId).sum();
    }

    int northPoleObjectsSectorId() {
        // Room to look for: northpole object storage

        return rooms.stream().filter(r -> r.shiftDecipher().equals("northpole object storage"))
                .mapToInt(Room::getSectorId).findFirst().orElse(0);
    }
}
