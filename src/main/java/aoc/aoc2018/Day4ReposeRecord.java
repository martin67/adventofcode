package aoc.aoc2018;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
class Day4ReposeRecord {

    @Data
    @RequiredArgsConstructor
    class Guard {
        final int id;
        List<SleepInterval> sleepIntervals = new ArrayList<>();
    }


    @Data
    @AllArgsConstructor
    static
    class SleepInterval {
        String date;
        int start;
        int end;

        boolean asleep(int minute) {
            return (minute >= start && minute < end);
        }
    }


    private final List<Guard> guardList = new ArrayList<>();


    private void loadData(String input) {

        // Split string into a list and sort it
        List<String> inputStrings = Arrays.stream(input.trim().split("\\n+")).sorted().collect(Collectors.toList());

        Guard activeGuard = null;
        int lastAsleep = 0;
        for (String inputString : inputStrings) {
            if (inputString.contains("Guard")) {
                // Guard row, create the guard if needed.
                int guardId = Integer.parseInt(StringUtils.substringBetween(inputString, "#", " "));
                log.debug("Found active guard " + guardId);

                activeGuard = guardList.stream().filter(g -> g.getId() == guardId).findAny().orElse(null);
                if (activeGuard == null) {
                    activeGuard = new Guard(guardId);
                    guardList.add(activeGuard);
                }

                log.debug("Adding sleep interval for guard " + activeGuard.getId());

            } else if (inputString.contains("falls asleep")) {
                lastAsleep = Integer.parseInt(StringUtils.substringBetween(inputString, ":", "]"));
            } else if (inputString.contains("wakes up")) {
                String date = StringUtils.substringBetween(inputString, "[", " ");
                int wakeup = Integer.parseInt(StringUtils.substringBetween(inputString, ":", "]"));
                activeGuard.getSleepIntervals().add(new SleepInterval(date, lastAsleep, wakeup));
            } else {
                log.error("Should not get here, error in input data");
            }

        }

    }

    int computeChecksum(String input) {
        loadData(input);

        // Compute sleep time for all guards
        Guard sleepiestGuard = null;
        int longestSleep = -1;

        for (Guard guard : guardList) {
            int sleepTime = 0;
            for (SleepInterval sleepInterval : guard.getSleepIntervals()) {
                sleepTime += sleepInterval.getEnd() - sleepInterval.getStart();
                log.debug("Guard " + guard.getId() + ", sleeptime " + sleepTime);
            }
            if (sleepTime > longestSleep) {
                sleepiestGuard = guard;
                longestSleep = sleepTime;
            }
        }
        log.info("Sleepiest guard " + sleepiestGuard.getId() + ", sleeptime " + longestSleep);

        // Find the minute where the guard sleeps the most
        int sleepiestMinute = 0;
        int sleepiestMinuteSleep = 0;
        for (int i = 0; i < 60; i++) {
            int sleep = 0;
            for (SleepInterval sleepInterval : sleepiestGuard.getSleepIntervals()) {
                if (sleepInterval.asleep(i)) {
                    sleep++;
                }
            }
            if (sleep > sleepiestMinuteSleep) {
                sleepiestMinuteSleep = sleep;
                sleepiestMinute = i;
            }

        }
        log.info("Sleepiest minute " + sleepiestMinute + ", with " + sleepiestMinuteSleep + " hits");


        return sleepiestGuard.getId() * sleepiestMinute;
    }


    int computeChecksum2(String input) {
        loadData(input);

        // For each minute, find out how many guards that are asleep. Save the one that has the most hits
        Guard sleepiestGuard = null;
        int sleepiestMinuteHits = -1;
        int sleepiestMinute = 0;
        for (int i = 0; i < 60; i++) {
            for (Guard guard : guardList) {
                int hits = 0;
                for (SleepInterval sleepInterval : guard.getSleepIntervals()) {
                    if (sleepInterval.asleep(i)) {
                        hits++;
                    }
                    if (hits > sleepiestMinuteHits) {
                        sleepiestGuard = guard;
                        sleepiestMinuteHits = hits;
                        sleepiestMinute = i;
                    }
                }
            }
        }
        return sleepiestMinute * sleepiestGuard.getId();
    }

}

