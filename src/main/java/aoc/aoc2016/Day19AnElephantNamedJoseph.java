package aoc.aoc2016;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Day19AnElephantNamedJoseph {

    List<Elf> elves = new LinkedList<>();

    public Day19AnElephantNamedJoseph(int numberOfElves) {
        for (int i = 0; i < numberOfElves; i++) {
            elves.add(new Elf(i, 1));
        }
    }

    int allPresents() {

        while (elves.size() > 1) {
            Iterator<Elf> it = elves.iterator();
            while (it.hasNext()) {
                Elf elf = it.next();
                if (!it.hasNext()) {
                    // reset
                    it = elves.iterator();
                }
                Elf nextElf = it.next();
                elf.presents += nextElf.presents;
                it.remove();
            }
        }
        return elves.get(0).id + 1;
    }

    static class Elf {
        int id;
        int presents;

        public Elf(int id, int presents) {
            this.id = id;
            this.presents = presents;
        }
    }
}
