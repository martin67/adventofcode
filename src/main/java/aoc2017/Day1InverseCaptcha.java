package aoc2017;

public class Day1InverseCaptcha {

    int computeCaptcha(String sequence) {
        int sum = 0;
        int previousChar = sequence.charAt(sequence.length() - 1);

        for (char currentChar : sequence.toCharArray()) {
            if (currentChar == previousChar) {
                sum += Character.getNumericValue(currentChar);
            }
            previousChar = currentChar;
        }
        return sum;
    }

    int computeHalfwayCaptcha(String sequence) {
        int sum = 0;
        char[] digits = sequence.toCharArray();

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] == digits[(i + digits.length / 2) % digits.length]) {
                sum += Character.getNumericValue(digits[i]);
            }
        }
        return sum;
    }

}
