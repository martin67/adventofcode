package aoc.aoc2024;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day1HistorianHysteria {
    List<Integer> leftList = new ArrayList<>();
    List<Integer> rightList = new ArrayList<>();

    Day1HistorianHysteria(List<String> inputLines) {
        for (String line : inputLines) {
            String[] s = line.split("\\s+");
            leftList.add(Integer.parseInt(s[0]));
            rightList.add(Integer.parseInt(s[1]));
        }
    }

    int problem1() {
        Collections.sort(leftList);
        Collections.sort(rightList);
        int distance = 0;
        for (int i = 0; i < leftList.size(); i++) {
            distance += Math.abs(leftList.get(i) - rightList.get(i));
        }
        return distance;
    }

    int problem2() {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i : rightList) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        int score = 0;
        for (int i : leftList) {
            score += i * map.getOrDefault(i, 0);
        }
        return score;
    }

}
