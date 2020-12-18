package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Day18OperationOrder {
    List<String> inputLines;

    public Day18OperationOrder(List<String>inputLines) {
        this.inputLines = inputLines;
    }

    int problem1() {
        int result = 0;
        for (String line : inputLines) {
            int r = operate(line);
            result += r;
            log.info("{} = {}, total {}", line, r, result);
        }
        return result;
    }

    int evaluate(String input) {
        if (input.charAt(0) == '(') {

        } else if (input.charAt(0) == ')') {

        }
    }

    Result operate(String input) {
        Result result;
        int left;
        String operand;
        int right = 0;
        if (input.charAt(0) == '(') {
            result = operate(input.substring(1));
        } else {
            String[] s = input.split(" ", 3);
            if (s[0].endsWith(")")) {
                Result r = new Result();
                r.value = Integer.parseInt(s[0].substring(0, s[0].length() - 2));
                r.remaining =
            }
            left = Integer.parseInt(s[0]);
            operand = s[1];
            Result rightHand = operate(s[2]);

            right
            // Upprepa höger tills man kommer till en högerparantes eller strängen tar slut
            String remaining = s[2];


            while (remaining.length() > 0 ) {
                Result r;
                if (remaining.charAt(0) == '(') {
                    r = operate(remaining.substring(1));
                    right += r.value;
                    remaining = r.remaining;
                } else {
                    // next number. Return if it ends with a parenthesis
                    String[] s2 = remaining.split(" ");
                    if (s2[0].endsWith(")")) {
                        return r;
                    }
                }
            }
            // om den högra är ett tal med högerparantes efter så skall man avsluta
            if (s[2].charAt(0) == '(') {
                right = operate(s[2]);
            } else {


            }

            if (operand.equals("+")) {
                result = left + right;
            } else {
                result = left * right;
            }
        }

        return result;
    }

    static class Result {
        int value;
        String remaining;
    }
}
