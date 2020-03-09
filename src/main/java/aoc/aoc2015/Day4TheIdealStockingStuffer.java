package aoc.aoc2015;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.security.NoSuchAlgorithmException;

public class Day4TheIdealStockingStuffer {
    int lowestNumber(String secretKey, int length) {
        int number = 0;
        HashFunction hf = Hashing.md5();
        String start = Strings.padStart("", length, '0');

        while (true) {
            number++;
            String input = String.format("%s%d", secretKey, number);
            HashCode hashCode = hf.newHasher().putString(input, Charsets.UTF_8).hash();
            if (hashCode.toString().startsWith(start)) {
                return number;
            }
        }
    }
}

