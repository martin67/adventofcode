package aoc2019;

public class Day4SecureContainer {
    boolean validPassword(int password) {
        String passwordString = String.valueOf(password);

        for (char c : passwordString.toCharArray()) {

        }
        return true;
    }

    int validPasswordRange(int start, int end) {
        int validPasswords = 0;

        for (int i = start; i < end + 1; i++) {
            if (validPassword(i)) {
                validPasswords++;
            }
        }
        return validPasswords;
    }
}
