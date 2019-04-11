import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3NoMatterHowYouSliceIt {

    private int[][] fabric = new int[1000][1000];

    void printFabric() {
        for (int y = 0; y < fabric.length; y++) {
            StringBuilder row = new StringBuilder();
            for (int x = 0; x < fabric[0].length ; x++) {
                row.append(fabric[x][y]);
            }
            System.out.println(y + ": " + row);
        }
        System.out.println();
    }

    int countSlices(String input) {
        //printFabric();

        int overlaps = 0;
        // Input string: #1 @ 1,3: 4x4
        String regexStr = "^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)$";
        // Compile the regex String into a Pattern
        Pattern pattern = Pattern.compile(regexStr);

        // Split string into a list
        List<String> inputStrings = Arrays.stream(input.trim().split("\\n+"))
                .collect(Collectors.toList());

        for (String string: inputStrings) {
            // Split into individual parts
            // Create a matcher with the input String
            Matcher matcher = pattern.matcher(string);

            // If we find a match
            if (matcher.find()) {
                // Get the String from the first capture group
                Integer id = Integer.parseInt(matcher.group(1));
                Integer startx = Integer.parseInt(matcher.group(2));
                Integer starty = Integer.parseInt(matcher.group(3));
                Integer width = Integer.parseInt(matcher.group(4));
                Integer height = Integer.parseInt(matcher.group(5));

                for (int y = starty; y < starty+height; y++) {
                    for (int x = startx; x < startx+width; x++) {
                        fabric[x][y]++;
                        if (fabric[x][y] == 2) {
                            overlaps++;
                        }
                    }
                }
                //printFabric();
            }
        }
        return overlaps;
    }
}
