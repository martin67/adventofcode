package aoc.aoc2016;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class Day19AnElephantNamedJoseph {

    private final int numberOfElves;

    public Day19AnElephantNamedJoseph(int numberOfElves) {
        this.numberOfElves = numberOfElves;
    }

    int allPresents() {
        List<Elf> elves = new LinkedList<>();
        for (int i = 0; i < numberOfElves; i++) {
            elves.add(new Elf(i + 1));
        }

        while (elves.size() > 1) {
            Iterator<Elf> it = elves.iterator();
            while (it.hasNext()) {
                it.next();
                if (!it.hasNext()) {
                    // reset
                    it = elves.iterator();
                }
                it.next();
                it.remove();
            }
        }
        return elves.get(0).id;
    }


    int allPresentsAcross() {
        boolean completed = false;
        int currentElf = 0;

        // remove half
        List<Elf> elves = new LinkedList<>();
        for (int i = 0; i < numberOfElves; i++) {
            elves.add(new Elf(i + 1));
        }

        while (!completed) {
            Elf elf = elves.get(currentElf);
            int currentElfIndex = elves.indexOf(elf);
            //log.info("Current elf: {}, index: {}, size: {}", elf.id, currentElfIndex, elves.size());
            int nextElfIndex = (currentElfIndex + elves.size() / 2) % elves.size();

            //log.info("Next elf: {}, size: {}", nextElf.id, elves.size());
            //log.info("Elf {} steals from elf {}", elf.id, nextElf.id);
            elves.remove(nextElfIndex);

            if (elves.size() == 1) {
                completed = true;
            } else {
                if (currentElf + 1 == elves.size()) {
                    currentElf = 0;
                } else {
                    currentElf++;
                }
            }
        }

        return elves.get(0).id;
    }

    int originalJosephus(int n, int k) {
        if (n == 1) {
            log.info("found k = {}", k);
            return 1;
        } else {
            return (originalJosephus(n - 1, k) + k - 1) % n + 1;
        }
    }

    int allPresentsAcross4() {
        int size = numberOfElves;
        Deque<Integer> left = IntStream.rangeClosed(1, size / 2).boxed().collect(Collectors.toCollection(ArrayDeque::new));
        Deque<Integer> right = IntStream.rangeClosed(size / 2 + 1, size).boxed().collect(Collectors.toCollection(ArrayDeque::new));

        while (left.size() + right.size() > 1) {
            right.poll();
            right.add(left.poll());
            if ((left.size() + right.size()) % 2 == 0)
                left.add(right.poll());
        }
        return right.poll();
    }

    static class Elf {
        int id;

        public Elf(int id) {
            this.id = id;
        }
    }
}
