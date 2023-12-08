package aoc.aoc2018;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
class Day3NoMatterHowYouSliceIt {

    private final Set<Claim>[][] fabric = new HashSet[1000][1000];
    private List<Claim> claimList;

    private void printFabric() {
        for (int y = 0; y < fabric.length; y++) {
            StringBuilder row = new StringBuilder();
            for (int x = 0; x < fabric[0].length; x++) {
                row.append(fabric[x][y]);
            }
            System.out.println(y + ": " + row);
        }
        System.out.println();
    }

    private List<Claim> createClaimList(String input) {
        // Input string: #1 @ 1,3: 4x4
        String regexStr = "^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)$";
        // Compile the regex String into a Pattern
        var pattern = Pattern.compile(regexStr);

        // Split string into a list
        List<String> inputStrings = Arrays.stream(input.trim().split("\\n+")).toList();

        List<Claim> claimList = new ArrayList<>();

        for (String string : inputStrings) {
            // Split into individual parts
            // Create a matcher with the input String
            var matcher = pattern.matcher(string);

            // If we find a match
            if (matcher.find()) {
                claimList.add(new Claim(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(2)) + Integer.parseInt(matcher.group(4)),
                        Integer.parseInt(matcher.group(3)) + Integer.parseInt(matcher.group(5))
                ));
            }
        }
        return claimList;
    }

    private void setup(String input) {
        claimList = createClaimList(input);
        fillFabric(claimList);
    }

    private void fillFabric(List<Claim> claimList) {

        for (Claim claim : claimList) {
            for (int y = claim.getStarty(); y < claim.getEndy(); y++) {
                for (int x = claim.getStartx(); x < claim.getEndx(); x++) {
                    if (fabric[x][y] == null) {
                        fabric[x][y] = new HashSet<>();
                    }
                    fabric[x][y].add(claim);
                }
            }
        }
    }

    int countSlices(String input) {

        setup(input);
        int overlaps = 0;

        for (int y = 0; y < 1000; y++) {
            for (int x = 0; x < 1000; x++) {
                if (fabric[x][y] != null && fabric[x][y].size() > 1) {
                    overlaps++;
                }
            }
        }
        return overlaps;
    }

    int getSingleClaim(String input) {
        setup(input);

        log.info("Starting singleClaim search");

        for (Claim claim : claimList) {
            //log.info("Checking claim, " + claim.getId());

            boolean discardClaim = false;
            // Check if every piece of the claim is alone on the fabric
            for (int y = claim.getStarty(); y < claim.getEndy(); y++) {
                for (int x = claim.getStartx(); x < claim.getEndx(); x++) {
                    if (fabric[x][y].size() != 1) {
                        log.debug("more than 1 slice on position [" + x + ", " + y + "], id: " + claim.getId());
                        discardClaim = true;
                        break;
                    }
                }
            }
            // check if found
            if (!discardClaim) {
                log.info("Found it, id: " + claim.getId());
                return claim.getId();
            }
        }
        return 0;
    }

    @Data
    @AllArgsConstructor
    static class Claim {
        int id;
        int startx;
        int starty;
        int endx;
        int endy;
    }
}
