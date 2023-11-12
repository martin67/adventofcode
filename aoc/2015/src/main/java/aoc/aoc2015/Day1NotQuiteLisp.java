package aoc.aoc2015;

import com.google.common.base.CharMatcher;

public class Day1NotQuiteLisp {

    public int getFloor(String input) {
        int up = CharMatcher.is('(').countIn(input);
        int down = CharMatcher.is(')').countIn(input);
        return up - down;
    }

    public int getPosition(String input) {
        int level = 0;
        int position = 1;
        for (char c : input.toCharArray()) {
            switch (c) {
                case '(' -> level++;
                case ')' -> level--;
            }
            if (level == -1) {
                return position;
            }
            position++;
        }
        return 0;
    }
}
