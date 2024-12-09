package aoc.aoc2024;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Day9DiskFragmenter {
    List<Integer> blocks = new ArrayList<>();
    List<File> files = new ArrayList<>();

    Day9DiskFragmenter(List<String> inputLines) {
        for (String line : inputLines) {
            boolean space = false;
            int index = 0;
            int blockId = 0;
            for (char c : line.toCharArray()) {
                int number = Character.getNumericValue(c);
                for (int i = 0; i < number; i++) {
                    if (space) {
                        blocks.add(-1);
                    } else {
                        blocks.add(blockId);
                    }
                    index++;
                }
                if (!space) {
                    files.add(new File(blockId, number, index - number));
                    blockId++;
                }
                space = !space;
            }
        }
    }

    long problem1() {
        int i = hasEmptyBlock();
        while (i >= 0) {
            int last = blocks.getLast();
            blocks.set(i, last);
            blocks.removeLast();
            i = hasEmptyBlock();
        }
        // checksum
        long checksum = 0;
        for (int j = 0; j < blocks.size(); j++) {
            checksum += blocks.get(j) * j;
        }
        return checksum;
    }

    long problem2() {
        Collections.reverse(files);
        for (File file : files) {
            fit(blocks, file);
        }

        // checksum
        long checksum = 0;
        for (int j = 0; j < blocks.size(); j++) {
            int id = blocks.get(j);
            if (id > 0) {
                checksum += blocks.get(j) * j;
            }
        }
        return checksum;

    }

    void fit(List<Integer> blocks, File file) {
        int size = 0;
        int position = 0;
        boolean hit = false;
        for (int i = 0; i < blocks.size(); i++) {
            if (i > file.start) {
                break;
            }
            if (blocks.get(i) == -1) {
                size++;
                // will it fit?
                if (size == file.size) {
                    position = i - size + 1;
                    hit = true;
                    break;
                }
            } else {
                size = 0;
            }
        }
        if (hit) {
            for (int i = 0; i < size; i++) {
                blocks.set(i + position, file.id);
                blocks.set(file.start + i, -1);
            }
        }
    }

    int hasEmptyBlock() {
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i) == -1) {
                return i;
            }
        }
        return -1;
    }

    static class File {
        int id;
        int size;
        int start;

        public File(int id, int size, int start) {
            this.id = id;
            this.size = size;
            this.start = start;
        }
    }
}
