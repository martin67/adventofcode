package aoc.aoc2016;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class Day16DragonChecksum {

    String dragonCurve(String a) {
        String b = StringUtils.reverse(a)
                .replace('0', 'x')
                .replace('1', '0')
                .replace('x', '1');
        return a + "0" + b;
    }

    String checksum(String input) {
        StringBuilder checksum = new StringBuilder();

        for (int i = 0; i < input.length(); i += 2) {
            switch (input.substring(i, i + 2)) {
                case "00":
                case "11":
                    checksum.append("1");
                    break;
                case "10":
                case "01":
                    checksum.append("0");
                    break;
                default:
                    log.error("Wrong input {}", input.substring(i, i + 2));
                    break;
            }
        }
        return (checksum.length() % 2 == 0) ? checksum(checksum.toString()) : checksum.toString();
    }

    String completeChecksum(int length, String input) {
        while (input.length() < length) {
            input = dragonCurve(input);
        }
        return checksum(input.substring(0, length));
    }
}
