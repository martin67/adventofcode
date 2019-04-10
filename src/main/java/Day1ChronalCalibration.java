import java.util.Arrays;

public class Day1ChronalCalibration {
    int computeFrequency(String input) {
        return Arrays.stream(input.trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .sum();
    }
}
