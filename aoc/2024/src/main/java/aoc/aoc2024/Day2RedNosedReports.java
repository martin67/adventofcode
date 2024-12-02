package aoc.aoc2024;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day2RedNosedReports {

    List<List<Integer>> reports = new ArrayList<>();

    Day2RedNosedReports(List<String> inputLines) {
        for (String line : inputLines) {
            List<Integer> report = new ArrayList<>();
            String[] split = line.split("\\s+");
            for (String word : split) {
                report.add(Integer.parseInt(word));
            }
            reports.add(report);
        }
    }

    long problem1() {
        return reports.stream().filter(this::isReportOk).count();
    }

    int problem2() {
        int safeReports = 0;
        for (List<Integer> report : reports) {
            if (isReportOk(report)) {
                safeReports++;
            } else {
                // try all other
                for (int i = 0; i < report.size(); i++) {
                    List<Integer> newReport = new ArrayList<>(report);
                    newReport.remove(i);
                    if (isReportOk(newReport)) {
                        safeReports++;
                        break;
                    }
                }
            }
        }
        return safeReports;
    }

    boolean isLevelOk(int level, int previousLevel, boolean increasing) {
        return (level > previousLevel && level - previousLevel <= 3 && increasing) || (level < previousLevel && previousLevel - level <= 3 && !increasing);
    }

    boolean isReportOk(List<Integer> report) {
        boolean safe = true;
        Boolean increasing = null;
        for (int i = 1; i < report.size(); i++) {
            int level = report.get(i);
            int previousLevel = report.get(i - 1);
            if (increasing == null) {
                increasing = level > previousLevel;
            }
            if (!isLevelOk(level, previousLevel, increasing)) {
                safe = false;
            }
        }
        return safe;
    }
}
