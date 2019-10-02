import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day24ImmuneSystemSimulator20XX {

    @Data
    @AllArgsConstructor
    static class Group {
        int units;
        int hitPoints;
        int initiative;
        int damage;
        String attackType;
        Set<String> weaknesses;
        Set<String> immunities;

        int effectivePower() {
            return units * damage;
        }
    }

    Set<Group> immuneSystem = new HashSet<>();
    Set<Group> infection = new HashSet<>();

    public Day24ImmuneSystemSimulator20XX(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        Pattern pattern = Pattern.compile("^(\\d+) units each with (\\d+) hit points \\((.*)\\) with an attack that does (\\d+) (\\w+) damage at initiative (\\d+)$");
        Pattern weaknessPattern = Pattern.compile("weak to (.*);?");
        Pattern immunePattern = Pattern.compile("immune to (.*);?");
        Set<Group> activeGroup = null;

        for (String row : inputStrings) {
            if (row.equals("Immune System:")) {
                activeGroup = immuneSystem;
            } else if (row.equals("Infection:")) {
                activeGroup = infection;
            } else {
                Matcher matcher = pattern.matcher(row);
                if (matcher.find()) {
                    int units = Integer.parseInt(matcher.group(1));
                    int hitPoints = Integer.parseInt(matcher.group(2));
                    String modifiers = matcher.group(3);

                    Set<String> weaknesses = new HashSet<>();
                    Matcher modifyMatcher = weaknessPattern.matcher(modifiers);
                    if (modifyMatcher.find()) {
                        weaknesses.addAll(Arrays.asList(modifyMatcher.group(1).trim().split(",")));
                    }

                    Set<String> immunities = new HashSet<>();
                    modifyMatcher = immunePattern.matcher(modifiers);
                    if (modifyMatcher.find()) {
                        immunities.addAll(Arrays.asList(modifyMatcher.group(1).trim().split(",")));
                    }

                    int damage = Integer.parseInt(matcher.group(4));
                    String attackType = matcher.group(5);
                    int initiative = Integer.parseInt(matcher.group(6));

                    activeGroup.add(new Group(units, hitPoints, initiative, damage, attackType, weaknesses, immunities));
                }
            }
        }
    }

    int winningArmyUnits() {
        return 0;
    }
}
