import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day2InventoryManagementSystem {

    class Checksum {
        int numberOfTwos;
        int numberOfThrees;
    }

    Checksum computeRowChecksum(String input) {
        Checksum result = new Checksum();

        Map<Character, Integer> characterOccurrences = new HashMap<>();

        // Create set with the number of occurrences for each letter
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (!characterOccurrences.containsKey(c)) {
                characterOccurrences.put(c, 1);
            } else {
                characterOccurrences.put(c, characterOccurrences.get(c) + 1);
            }
        }

        // Check if any of occurrences = 2 or 3
        result.numberOfTwos = characterOccurrences.containsValue(2) ? 1 : 0;
        result.numberOfThrees = characterOccurrences.containsValue(3) ? 1 : 0;
        return result;
    }

    int computeChecksum(String input) {
        // Split string into a list
        List<String> inputStrings = Arrays.stream(input.trim().split("\\s+"))
                .collect(Collectors.toList());

        int twos = 0, threes = 0;
        for (String string : inputStrings) {
            Checksum result = computeRowChecksum(string);
            twos += result.numberOfTwos;
            threes += result.numberOfThrees;
        }
        return twos * threes;
    }
}
