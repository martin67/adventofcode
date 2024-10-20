package aoc.aoc2022;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day7NoSpaceLeftOnDevice {
    final Content root;

    Day7NoSpaceLeftOnDevice(List<String> inputLines) {
        root = new Content("/", null);
        Content cwd = root;
        for (String line : inputLines) {
            if (line.startsWith("$ cd")) {
                String arg = line.substring(5);
                if (arg.equals("/")) {
                    cwd = root;
                } else if (arg.equals("..")) {
                    cwd = cwd.parent;
                } else {
                    cwd = cwd.contents.stream()
                            .filter(c -> (c.type == ContentType.Directory))
                            .filter(c -> (c.name.equals(arg)))
                            .findFirst().orElseThrow();
                }
            } else if (line.equals("$ ls")) {
                // skip
            } else {
                // Directory listing
                if (line.startsWith("dir")) {
                    Content directory = new Content(line.substring(4), cwd);
                    cwd.contents.add(directory);
                } else {
                    String[] s = line.split(" ");
                    String name = s[1];
                    int size = Integer.parseInt(s[0]);
                    var file = new Content(name, cwd, size);
                    if (!cwd.contents.contains(file)) {
                        cwd.contents.add(file);
                        // add to all parent sizes
                        Content parent = cwd;
                        while (parent != null) {
                            parent.size += size;
                            parent = parent.parent;
                        }
                    }
                }
            }
        }
    }

    int problem1() {
        return root.getAllContent().stream()
                .filter(c -> (c.type == ContentType.Directory))
                .filter(c -> (c.size <= 100000))
                .mapToInt(Content::getSize)
                .sum();
    }

    int problem2() {
        int needToRemove = 30000000 - (70000000 - root.size);
        return root.getAllContent().stream()
                .filter(c -> (c.type == ContentType.Directory))
                .filter(c -> (c.size >= needToRemove))
                .min(Comparator.comparing(Content::getSize))
                .orElseThrow().size;
    }

    enum ContentType {Directory, File}

    static class Content {
        final String name;
        @Getter
        int size;
        final ContentType type;
        final Content parent;
        final Set<Content> contents = new HashSet<>();

        Content(String name, Content parent) {
            this.name = name;
            this.type = ContentType.Directory;
            this.parent = parent;
        }

        Content(String name, Content parent, int size) {
            this.name = name;
            this.type = ContentType.File;
            this.parent = parent;
            this.size = size;
        }

        Set<Content> getAllContent() {
            Set<Content> result = new HashSet<>(contents);
            for (var content : contents) {
                result.addAll(content.getAllContent());
            }
            return result;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, type, parent);
        }
    }
}
