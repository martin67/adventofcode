package adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day11RadioisotopeThermoelectricGenerators {

    public Day11RadioisotopeThermoelectricGenerators(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        for (String row : inputStrings) {
        }
    }

    int minimumNumberOfSteps() {
        return 0;
    }
}
