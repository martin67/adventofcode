package aoc.aoc2015;

import lombok.extern.slf4j.Slf4j;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;

@Slf4j
public class Day8Matchsticks {
    int numberOfCharacters = 0;
    int numberOfMemory = 0;
    int extended = 0;

    public Day8Matchsticks(List<String> inputLines) {
        for (String line : inputLines) {
            String before = line;
            numberOfCharacters += line.length();
            extended += line.length() + 4;

            line = line.substring(1, line.length() - 1);

            StringBuilder sb = new StringBuilder();
            StringCharacterIterator it = new StringCharacterIterator(line);

            while (it.current() != CharacterIterator.DONE) {
                if (it.current() == '\\') {
                    it.next();
                    extended++;
                    switch (it.current()) {
                        case CharacterIterator.DONE:
                            log.error("Is this legal?");
                            sb.append('\\');
                            break;
                        case ('\\'):
                            sb.append('A');
                            it.next();
                            extended++;
                            break;
                        case '"':
                            sb.append('B');
                            it.next();
                            extended++;
                            break;
                        case 'x':
                            char a = it.next();
                            char b = it.next();
                            if (Character.digit(a, 16) != -1 && Character.digit(b, 16) != -1) {
                                // valid hex chars, skip
                                sb.append('X');
                                it.next();
                            } else {
                                // back one
                                it.previous();
                            }
                            break;
                        default:
                            log.error("Is this legal?");
                            sb.append('C').append(it.current());
                            it.next();
                            break;
                    }
                } else {
                    sb.append(it.current());
                    it.next();
                }
            }

            line = sb.toString();
            numberOfMemory += line.length();
            log.debug("{} [{}] -> {} [{}]", before, before.length(), line, line.length());
        }
    }

    public int matches() {
        return numberOfCharacters - numberOfMemory;
    }

    public int encoded() {
        return extended - numberOfCharacters;
    }
}
