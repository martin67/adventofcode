package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Slf4j
public class Day18OperationOrder {
    private final List<String> inputLines;
    private boolean problem2;

    public Day18OperationOrder(List<String> inputLines) {
        this.inputLines = inputLines;
    }

    public long problem1() {
        long result = 0;
        for (String line : this.inputLines) {
            List<String> answerList = getPostFixString(line);
            long answer = calculatePostFix(answerList);
            result += answer;

        }
        return result;
    }

    public long problem2() {
        problem2 = true;
        long result = 0;
        for (String line : this.inputLines) {
            List<String> answerList = getPostFixString(line);
            long answer = calculatePostFix(answerList);
            result += answer;
        }
        return result;
    }

    private int getPreference(char c) {
        if (!problem2) {
            if (c == '+' || c == '-') return 1;
            else if (c == '*' || c == '/') return 1;
            else return -1;
        } else {
            if (c == '+' || c == '-') return 2;
            else if (c == '*' || c == '/') return 1;
            else return -1;
        }
    }


    private long calculatePostFix(List<String> postFixList) {
        Stack<Long> stack = new Stack<>();
        for (String word : postFixList) {
            switch (word.charAt(0)) {
                case '+' -> stack.push(stack.pop() + stack.pop());
                case '-' -> stack.push(stack.pop() - stack.pop());
                case '*' -> stack.push(stack.pop() * stack.pop());
                case '/' -> stack.push(stack.pop() / stack.pop());
                default -> stack.push(Long.parseLong(word));
            }
        }
        return stack.pop();
    }

    private List<String> getPostFixString(String s) {
        Stack<Character> stack = new Stack<>();
        List<String> postFixList = new ArrayList<>();
        boolean flag = false;
        for (int i = 0; i < s.length(); i++) {
            char word = s.charAt(i);
            if (word == ' ') {
                continue;
            }
            if (word == '(') {
                stack.push(word);
                flag = false;
            } else if (word == ')') {
                flag = false;
                while (!stack.isEmpty()) {
                    if (stack.peek() == '(') {
                        stack.pop();
                        break;
                    } else {
                        postFixList.add(stack.pop() + "");
                    }
                }
            } else if (word == '+' || word == '-' || word == '*' || word == '/') {
                flag = false;
                if (!stack.isEmpty()) {
                    while (!stack.isEmpty() && getPreference(stack.peek()) >= getPreference(word)) {
                        postFixList.add(stack.pop() + "");
                    }
                }
                stack.push(word);
            } else {
                if (flag) {
                    String lastNumber = postFixList.get(postFixList.size() - 1);
                    lastNumber += word;
                    postFixList.set(postFixList.size() - 1, lastNumber);
                } else
                    postFixList.add(word + "");
                flag = true;
            }
        }
        while (!stack.isEmpty()) {
            postFixList.add(stack.pop() + "");
        }
        return postFixList;
    }

}
