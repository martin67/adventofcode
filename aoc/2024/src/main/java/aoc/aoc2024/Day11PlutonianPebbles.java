package aoc.aoc2024;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day11PlutonianPebbles {

    List<Long> stones = new ArrayList<>();

    Day11PlutonianPebbles(List<String> inputLines) {
        for (String line : inputLines) {
            String[] split = line.split(" ");
            for (String word : split) {
                stones.add(Long.parseLong(word));
            }
        }
    }

    int problem1() {
        List<Long> s = stones;
        for (int i = 0; i < 25; i++) {
            s = blink(s);
        }
        return s.size();
    }

    long problem2() {
        Map<Long, Long> cur = new HashMap<>();
        for (long stone : stones) {
            cur.put(stone, 1L);
        }
        for (int i = 0; i < 75; i++) {
            Map<Long, Long> next = new HashMap<>();
            for (Long stone : cur.keySet()) {
                if (stone == 0) {
                    next.put(1L, cur.get(stone));
                } else if (String.valueOf(stone).length() % 2 == 0) {
                    String s = String.valueOf(stone);
                    long left = Long.parseLong(s.substring(0, s.length() / 2));
                    long right = Long.parseLong(s.substring(s.length() / 2));
                    next.put(left, next.getOrDefault(left, 0L) + cur.get(stone));
                    next.put(right, next.getOrDefault(right, 0L) + cur.get(stone));
                } else {
                    next.put(stone * 2024, cur.get(stone));
                }
            }
            cur = next;
        }

        return cur.values().stream().mapToLong(Long::longValue).sum();
    }

    List<Long> blink(List<Long> stones) {
        List<Long> newStones = new ArrayList<>();
        for (Long stone : stones) {
            if (stone == 0) {
                newStones.add(1L);
            } else if (String.valueOf(stone).length() % 2 == 0) {
                String s = String.valueOf(stone);
                newStones.add(Long.parseLong(s.substring(0, s.length() / 2)));
                newStones.add(Long.parseLong(s.substring(s.length() / 2)));
            } else {
                newStones.add(stone * 2024);
            }
        }
        return newStones;
    }
}
