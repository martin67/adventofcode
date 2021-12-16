package aoc.aoc2021;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Day16PacketDecoder {

    String binary;
    int versionNumbers = 0;

    public Day16PacketDecoder(List<String> inputLines) {
        StringBuilder sb = new StringBuilder();
        for (String line : inputLines) {
            for (char c : line.toCharArray()) {
                sb.append(String.format("%4s", Integer.toBinaryString(Character.digit(c, 16))).replace(' ', '0'));
            }
        }
        binary = sb.toString();
    }

    String decodePacket(String binaryInput) {
        int pos = 0;
        while (pos < binaryInput.length()) {
            int version = Integer.parseInt(binaryInput.substring(pos, pos + 3), 2);
            pos += 3;
            versionNumbers += version;
            int id = Integer.parseInt(binaryInput.substring(pos, pos + 3), 2);
            pos += 3;

            if (id == 4) {
                log.info("Decoding literal, version {}, id: {}", version, id);
                // Literal value
                StringBuilder number = new StringBuilder();
                //int n = Integer.parseInt(binary.substring(pos, pos + 5), 2);
                boolean stop = false;
                do {
                    String n = binaryInput.substring(pos, pos + 5);
                    number.append(n.substring(1));
                    if (n.charAt(0) == '0') {
                        stop = true;
                    }
                    pos += 5;
                } while (!stop);
                int n2 = Integer.parseInt(number.toString(), 2);
                log.info("number: {}", n2);
                // pad if needed
                log.info("padding pos {} with {} => pos = {}", pos, pos % 4, pos + pos % 4);
                pos += pos % 4;
                return binaryInput.substring(pos);
            } else {
                // operator
                char lengthTypeId = binaryInput.charAt(pos);
                pos++;
                log.info("Decoding operator, version {}, id: {}, type: {}", version, id, lengthTypeId);

                int length;
                String out;
                if (lengthTypeId == '0') {
                    length = Integer.parseInt(binaryInput.substring(pos, pos + 15), 2);
                    log.info("length: {}", length);
                    pos += 15;
                    String t = binaryInput.substring(pos, length - 1);
                    do {
                        out = decodePacket(binaryInput.substring(pos, pos + length - 1));
                        log.info("After {}, {}", out, out.length());
                    }
                    while (out.length() > 0);
                } else {
                    length = Integer.parseInt(binaryInput.substring(pos, pos + 11), 2);
                    log.info("length: {}", length);
                    pos += 11;
                    out = binaryInput.substring(pos);
                    for (int i = 0; i < length; i++) {
                        out = decodePacket(out);
                    }
                }
                return out;
            }
        }
        return "";
    }

    int problem1() {
        String out = decodePacket(binary);
        return versionNumbers;
    }

    int problem2() {
        return 0;
    }
}
