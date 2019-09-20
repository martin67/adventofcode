import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day23ExperimentalEmergencyTeleportation {

    @Data
    @AllArgsConstructor
    static class Nanobot {
        SpacePosition pos;
        int r;
    }


    private Set<Nanobot> nanobots = new HashSet<>();

    public Day23ExperimentalEmergencyTeleportation(String fileName) throws IOException {
        readData(fileName);
    }


    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        Pattern pattern = Pattern.compile("^pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(\\d+)$");

        for (String row : inputStrings) {
            Matcher matcher = pattern.matcher(row);
            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                int z = Integer.parseInt(matcher.group(3));
                int r = Integer.parseInt(matcher.group(4));
                nanobots.add(new Nanobot(new SpacePosition(x, y, z), r));
            }
        }
        System.out.printf("Read %d nanobots from %s\n", nanobots.size(), fileName);
    }

    int nanoBotsInRange() {
        Nanobot strongestNanobot = nanobots.stream().max(Comparator.comparing(Nanobot::getR)).get();

        System.out.printf("Strongest nanobot: %s\n", strongestNanobot);

        Set<Nanobot> inRange = nanoBotsInRange(strongestNanobot);

        return inRange.size();
    }

    private Set<Nanobot> nanoBotsInRange(Nanobot nanoBot) {
        return nanobots.stream().filter(n -> n.pos.distance(nanoBot.pos) <= nanoBot.r).collect(Collectors.toSet());
    }

}
