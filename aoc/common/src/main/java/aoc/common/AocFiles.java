package aoc.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AocFiles {

    public static List<String> readAllLines(String fileName) throws IOException {
        return Files.readAllLines(Paths.get("src/test/resources/" + fileName));
    }

    public static byte[] readAllBytes(String fileName) throws IOException {
        return Files.readAllBytes(Paths.get("src/test/resources/" + fileName));
    }

}
