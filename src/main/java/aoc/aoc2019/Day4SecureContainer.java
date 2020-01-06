package aoc.aoc2019;

public class Day4SecureContainer {

    boolean validPassword(int password) {
        char[] digits = String.valueOf(password).toCharArray();

        for (int i = 1; i < 6; i++) {
            if (digits[i] < digits[i - 1]) {
                return false;
            }
        }
        for (int i = 1; i < 6; i++) {
            if (digits[i - 1] == digits[i]) {
                return true;
            }
        }
        return false;
    }

    boolean validPasswordNoGroup(int password) {
        char[] digits = String.valueOf(password).toCharArray();

        for (int i = 1; i < 6; i++) {
            if (digits[i] < digits[i - 1]) {
                return false;
            }
        }

        int similar = 0;
        for (int i = 1; i < 6; i++) {
            if (digits[i - 1] == digits[i]) {
                similar++;
                if (i == 5 && similar == 1) {
                    return true;
                }
            } else {
                if (similar == 1) {
                    return true;
                }
                similar = 0;
            }
        }
        return false;
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

    int validPasswordNoGroupRange(int start, int end) {
        int validPasswords = 0;

        for (int i = start; i < end + 1; i++) {
            if (validPasswordNoGroup(i)) {
                validPasswords++;
            }
        }
        return validPasswords;
    }
}
