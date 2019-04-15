import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Day7TheSumofItsParts {

    @Data
    @RequiredArgsConstructor
    class Step {
        final String name;
        List<Step> beforeSteps = new ArrayList<>();
        boolean available = true;
        @NonNull
        int timeLeft;
    }

    List<Step> stepList = new ArrayList<>();


    void readData(String input) {
        // Split string into a list
        List<String> inputStrings = Arrays.stream(input.trim().split("\\n")).collect(Collectors.toList());
        for (String string : inputStrings) {
            String before = StringUtils.substringBetween(string, "Step ", " must");
            String after = StringUtils.substringBetween(string, "step ", " can");

            // See if the before step exist in the list
            Step beforeStep = stepList.stream().filter(s -> s.getName().equals(before)).findAny().orElse(null);
            if (beforeStep == null) {
                beforeStep = new Step(before, before.charAt(0) - 4);
                stepList.add(beforeStep);
            }
            Step afterStep = stepList.stream().filter(s -> s.getName().equals(after)).findAny().orElse(null);
            if (afterStep == null) {
                afterStep = new Step(after, after.charAt(0) - 4);
                stepList.add(afterStep);
            }
            afterStep.getBeforeSteps().add(beforeStep);
        }
        log.info("Read " + inputStrings.size() + " lines, creating " + stepList.size() + " steps");
    }


    String puzzleOrder(String input) {
        readData(input);

        StringBuilder result = new StringBuilder();

        Step aStep;
        do {
            aStep = stepList.stream().
                    filter(s -> s.isAvailable()).
                    filter(s -> s.getBeforeSteps().isEmpty()).
                    sorted(Comparator.comparing(Step::getName)).findAny().orElse(null);
            // Process aStep
            if (aStep != null) {
                // add to result
                result.append(aStep.getName());
                // make it not available (as it has been done)
                aStep.setAvailable(false);
                // Remove it from all before steps (as it is no longer a preq)
                Step finalAStep = aStep;
                stepList.stream().filter(s -> s.getBeforeSteps().contains(finalAStep)).forEach(s -> s.getBeforeSteps().remove(finalAStep));
            }
        }
        while (aStep != null);

        return result.toString();
    }


    int computeTime(String input) {
        readData(input);

        return 0;
    }
}
