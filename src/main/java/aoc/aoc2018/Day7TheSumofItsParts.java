package aoc.aoc2018;

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
class Day7TheSumofItsParts {

    @Data
    @RequiredArgsConstructor
    class Step {
        final String name;
        List<Step> beforeSteps = new ArrayList<>();
        boolean available = true;
        boolean busy = false;
        @NonNull
        Integer timeLeft;
    }

    private final List<Step> stepList = new ArrayList<>();


    private void readData(String input, int duration) {
        // Split string into a list
        List<String> inputStrings = Arrays.stream(input.trim().split("\\n")).collect(Collectors.toList());
        for (String string : inputStrings) {
            String before = StringUtils.substringBetween(string, "Step ", " must");
            String after = StringUtils.substringBetween(string, "step ", " can");

            // See if the before step exist in the list
            Step beforeStep = stepList.stream().filter(s -> s.getName().equals(before)).findAny().orElse(null);
            if (beforeStep == null) {
                beforeStep = new Step(before, before.charAt(0) - 64 + duration);
                stepList.add(beforeStep);
            }
            Step afterStep = stepList.stream().filter(s -> s.getName().equals(after)).findAny().orElse(null);
            if (afterStep == null) {
                afterStep = new Step(after, after.charAt(0) - 64 + duration);
                stepList.add(afterStep);
            }
            afterStep.getBeforeSteps().add(beforeStep);
        }
        log.info("Read " + inputStrings.size() + " lines, creating " + stepList.size() + " steps");
    }


    String puzzleOrder(String input) {
        readData(input, 0);

        StringBuilder result = new StringBuilder();

        Step aStep;
        do {
            aStep = stepList.stream().
                    filter(Step::isAvailable).
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


    int computeTime(String input, int numberOfWorkers, int duration) {
        readData(input, duration);
        StringBuilder result = new StringBuilder();
        int totalTime = 0;

        Step aStep;
        do {
            // Find out how many free workers available
            int freeWorkers = numberOfWorkers - (int) stepList.stream().filter(s -> s.busy).count();
            log.info("Second " + totalTime + ", " + freeWorkers + " free workers");

            // assign work for each free worker
            for (int i = 0; i < freeWorkers; i++) {
                aStep = stepList.stream().
                        filter(Step::isAvailable).
                        filter(s -> !s.isBusy()).
                        filter(s -> s.getBeforeSteps().isEmpty()).
                        sorted(Comparator.comparing(Step::getName)).findAny().orElse(null);
                // Process aStep
                if (aStep != null) {
                    aStep.setBusy(true);
                    log.info("Assigning one worker to step " + aStep.getName());
                }
            }

            // Decrease time left for all busy steps
            stepList.stream().filter(Step::isBusy).forEach(s -> s.timeLeft--);

            // Check of any busy step is finished
            // in that case, remove it
            stepList.stream().filter(Step::isBusy).
                    filter(s -> s.getTimeLeft() == 0).
                    forEach(s -> {
                        result.append(s.getName());
                        log.info("Step " +  s.getName() + " finished");
                        s.setBusy(false);
                        s.setAvailable(false);
                        stepList.stream().filter(s2 -> s2.getBeforeSteps().contains(s)).forEach(s2 -> s2.getBeforeSteps().remove(s));
                    });

            totalTime++;
        } while (stepList.stream().anyMatch(Step::isAvailable));

        return totalTime;
    }
}
