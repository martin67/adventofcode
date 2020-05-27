package aoc.aoc2015;

public class Day10ElvesLookElvesSay {

    String input;

    public Day10ElvesLookElvesSay(String input) {
        this.input = input;
    }

    String lookAndSay(String in) {
        StringBuilder sb = new StringBuilder();

        int numberOfEquals = 0;
        char previousChar = in.charAt(0);
        for (char currentChar : in.toCharArray()) {
            if (currentChar == previousChar) {
                numberOfEquals++;
            } else {
                sb.append(numberOfEquals).append(previousChar);
                previousChar = currentChar;
                numberOfEquals = 1;
            }
        }
        sb.append(numberOfEquals).append(previousChar);
        return sb.toString();
    }

    int codeLength(int times) {
        String sequence = input;
        for (int i = 0; i < times; i++) {
            sequence = lookAndSay(sequence);
        }
        return sequence.length();
    }
}
