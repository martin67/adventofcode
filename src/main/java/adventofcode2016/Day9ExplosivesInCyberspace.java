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

    long decompressedVersion1() {

        long length = 0;
        for (String message : messages) {
            length = decompress(message, false);
        }
        return length;
    }

    long decompressedVersion2() {

        long length = 0;
        for (String message : messages) {
            length = decompress(message, true);
        }
        return length;
    }

    private long decompress(String message, boolean version2) {
        Pattern pattern = Pattern.compile("^(\\((\\d+)x(\\d+)\\))");
        int index = 0;
        long length = 0;

        while (index < message.length()) {
            String input = message.substring(index);
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                String header = matcher.group(1);
                int decompressedLength = Integer.parseInt(matcher.group(2));
                int decompressedRepeat = Integer.parseInt(matcher.group(3));
                if (version2) {
                    int start = index + header.length();
                    length += decompress(message.substring(start, start + decompressedLength), true) * decompressedRepeat;
                } else {
                    length += decompressedLength * decompressedRepeat;
                }
                index += header.length() + decompressedLength;
            } else {
                index++;
                length++;
            }
        }
        return length;
    }

}
