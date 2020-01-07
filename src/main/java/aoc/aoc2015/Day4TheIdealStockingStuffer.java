package aoc.aoc2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day4TheIdealStockingStuffer {
    int lowestNumber(String secretKey) throws NoSuchAlgorithmException {
        int number = 1;
        MessageDigest md = MessageDigest.getInstance("MD5");
//        while (true) {
//            String input = String.format("%s%d", secretKey, number);
//
//            //md.update(input);
//            //hash = DatatypeConverter.printHexBinary(md.digest()).toLowerCase();
//
//        }
        return 0;
    }
}

