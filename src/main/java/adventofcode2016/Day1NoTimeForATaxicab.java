package adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day1NoTimeForATaxicab {

    public Day1NoTimeForATaxicab(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        for (String row : inputStrings) {

        }
    }

    int shortestPath() {
        return 0;
    }
}
