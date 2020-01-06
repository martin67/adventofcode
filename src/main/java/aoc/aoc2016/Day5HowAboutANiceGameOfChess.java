package aoc.aoc2016;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day5HowAboutANiceGameOfChess {

    private String doorId;

    public Day5HowAboutANiceGameOfChess(String input) {
        this.doorId = input;
    }

    String getPassword() throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        StringBuilder password = new StringBuilder();
        int counter = 0;

        for (int i = 0; i < 8; i++) {
            String hash;

            do {
                String id = doorId + counter;
                md.update(id.getBytes());
                hash = DatatypeConverter.printHexBinary(md.digest()).toLowerCase();
                counter++;
            } while (!hash.substring(0, 5).equals("00000"));
            password.append(hash.charAt(5));
        }
        return password.toString();
    }

    String getSecondPassword() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        char[] password = new char[8];
        int counter = 0;

        do {
            String hash;

            do {
                String id = doorId + counter;
                md.update(id.getBytes());
                hash = DatatypeConverter.printHexBinary(md.digest()).toLowerCase();
                counter++;
            } while (!hash.substring(0, 5).equals("00000"));

            int index = Character.getNumericValue(hash.charAt(5));
            if (index < 8 && password[index] == 0) {
                password[index] = hash.charAt(6);

            }
        } while (!passwordComplete(password));

        return new String(password);
    }

    private boolean passwordComplete(char[] password) {
        boolean allFilled = true;
        for (int i = 0; i < 8; i++) {
            if (password[i] == 0) {
                allFilled = false;
                break;
            }
        }
        return allFilled;
    }
}
