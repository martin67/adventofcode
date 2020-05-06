package aoc.aoc2016;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day14OneTimePad {
    String salt;

    public Day14OneTimePad(String salt) {
        this.salt = salt;
    }

    int hashIndex() {
        List<String> hashes = new ArrayList<>();
        int keysFound = 0;
        int index = 0;

        // preload
        for (int i = 0; i < 1002; i++) {
            hashes.add(getHash(i));
        }

        while (keysFound < 64) {
            String triple = isTriple(hashes.get(index));
            if (triple != null) {
                log.debug("Found triple {} at index {}", triple, index);
                for (int i = index + 1; i < index + 1002; i++) {
                    if (isValidKey(triple, hashes.get(i))) {
                        log.debug("Found fiver {} at index {}, key number: {}", triple, i, keysFound);
                        keysFound++;
                        break;
                    }
                }
            }
            index++;
            hashes.add(getHash(index + 1001));
        }

        return index - 1;
    }

    String isTriple(String hash) {

        String[] triples = {"000", "111", "222", "333", "444", "555", "666", "777",
                "888", "999", "aaa", "bbb", "ccc", "ddd", "eee", "fff"};

        int lastIndex = 100;
        String foundTriple = null;
        for (String triple : triples) {
            int index = hash.indexOf(triple);
            if (index != -1 && index < lastIndex) {
                lastIndex = index;
                foundTriple = triple;
            }
        }
        return foundTriple;
    }

    boolean isValidKey(String triple, String hash) {
        String five = triple + triple.substring(0, 2);
        return hash.contains(five);
    }

    String getHash(int index) {
        String value = salt + index;
        String md5 = Hashing.md5().hashString(value, Charsets.UTF_8).toString();

        return md5;
    }
}
