package adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9ExplosivesInCyberspace {

    private List<String> messages;

    public Day9ExplosivesInCyberspace(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        messages = Files.readAllLines(Paths.get(fileName));
    }

    int decompressedLength() {
        StringBuilder output = new StringBuilder();

        for (String message : messages) {
            Pattern pattern = Pattern.compile("^(\\((\\d+)x(\\d+)\\))");
            int index = 0;

            while (index < message.length()) {
                String input = message.substring(index);
                Matcher matcher = pattern.matcher(input);
                if (matcher.find()) {
                    String everything = matcher.group(1);
                    int length = Integer.parseInt(matcher.group(2));
                    int repeat = Integer.parseInt(matcher.group(3));
                    int start = index + everything.length();
                    String addString = message.substring(start, start + length);
                    for (int i = 0; i < repeat; i++) {
                        output.append(addString);
                    }
                    index += everything.length() + length;
                } else {
                    output.append(message.charAt(index));
                    index++;
                }
            }
        }
        return output.length();
    }

    int longerDecompressedLength() {
        return 0;
    }
}
