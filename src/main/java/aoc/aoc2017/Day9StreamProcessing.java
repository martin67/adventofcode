package aoc.aoc2017;

public class Day9StreamProcessing {
    String stream;
    int totalScore = 0;
    int totalGarbage = 0;

    public Day9StreamProcessing(String stream) {
        this.stream = stream;
    }

    int score() {
        doGroup(stream, 0);
        return totalScore;
    }

    int garbage() {
        doGroup(stream, 0);
        return totalGarbage;
    }

    int doGroup(String input, int level) {
        int currentPosition = 0;
        totalScore += level;
        while (currentPosition < input.length()) {
            switch (input.charAt(currentPosition)) {
                case '{':
                    currentPosition += doGroup(input.substring(currentPosition + 1), level + 1) + 1;
                    break;

                case '}':
                    return currentPosition + 1;

                case '<':
                    currentPosition += doGarbage(input.substring(currentPosition + 1)) + 1;
                    break;

                case ',':
                    currentPosition++;
                    break;

                default:
                    assert false : "Should not get here!";
            }
        }

        return 0;
    }

    int doGarbage(String input) {
        int currentPosition = 0;
        while (currentPosition < input.length()) {
            switch (input.charAt(currentPosition)) {

                case '>':
                    return currentPosition + 1;

                case '!':
                    currentPosition += 2;
                    break;

                default:
                    currentPosition++;
                    totalGarbage++;
                    break;
            }
        }
        return 0;
    }
}
