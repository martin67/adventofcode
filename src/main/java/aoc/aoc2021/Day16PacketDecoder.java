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

    Result decodePacket(String binaryInput) {
        Result result = new Result();
        char lengthTypeId;
        int length;
        int pos = 0;

        while (pos < binaryInput.length()) {
            int version = Integer.parseInt(binaryInput.substring(pos, pos += 3), 2);
            versionNumbers += version;
            int id = Integer.parseInt(binaryInput.substring(pos, pos += 3), 2);

            switch (id) {
                case 4:
                    log.debug("Decoding literal, version {}, id: {}, in length: {}", version, id, binaryInput.length());
                    StringBuilder number = new StringBuilder();
                    boolean stop = false;
                    do {
                        String n = binaryInput.substring(pos, pos + 5);
                        number.append(n.substring(1));
                        if (n.charAt(0) == '0') {
                            stop = true;
                        }
                        pos += 5;
                    } while (!stop);
                    long n2 = Long.parseLong(number.toString(), 2);
                    log.debug("number: {}", n2);
                    result.output = binaryInput.substring(pos);
                    result.value = n2;
                    return result;

                case 0:
                    lengthTypeId = binaryInput.charAt(pos++);
                    log.debug("Decoding sum operator type {}, version {}, id: {}, in length: {}", lengthTypeId, version, id, binaryInput.length());

                    if (lengthTypeId == '0') {
                        length = Integer.parseInt(binaryInput.substring(pos, pos += 15), 2);
                        Result r = new Result(binaryInput.substring(pos, pos + length));
                        do {
                            r = decodePacket(r.output);
                            result.value += r.value;
                        } while (r.output.length() > 0);
                        result.output = binaryInput.substring(pos + length);
                    } else {
                        length = Integer.parseInt(binaryInput.substring(pos, pos += 11), 2);
                        Result r = new Result(binaryInput.substring(pos));
                        for (int i = 0; i < length; i++) {
                            r = decodePacket(r.output);
                            result.value += r.value;
                        }
                        result.output = r.output;
                    }
                    return result;

                case 1:
                    lengthTypeId = binaryInput.charAt(pos++);
                    log.debug("Decoding mul operator type {}, version {}, id: {}, in length: {}", lengthTypeId, version, id, binaryInput.length());
                    result.value = 1;
                    if (lengthTypeId == '0') {
                        length = Integer.parseInt(binaryInput.substring(pos, pos += 15), 2);
                        Result r = new Result(binaryInput.substring(pos, pos + length));
                        do {
                            r = decodePacket(r.output);
                            result.value *= r.value;
                        } while (r.output.length() > 0);
                        result.output = binaryInput.substring(pos + length);
                    } else {
                        length = Integer.parseInt(binaryInput.substring(pos, pos += 11), 2);
                        Result r = new Result(binaryInput.substring(pos));
                        for (int i = 0; i < length; i++) {
                            r = decodePacket(r.output);
                            result.value *= r.value;
                        }
                        result.output = r.output;
                    }
                    return result;

                case 2:
                    lengthTypeId = binaryInput.charAt(pos++);
                    log.debug("Decoding min operator type {}, version {}, id: {}, in length: {}", lengthTypeId, version, id, binaryInput.length());
                    result.value = Long.MAX_VALUE;
                    if (lengthTypeId == '0') {
                        length = Integer.parseInt(binaryInput.substring(pos, pos += 15), 2);
                        Result r = new Result(binaryInput.substring(pos, pos + length));
                        do {
                            r = decodePacket(r.output);
                            result.value = Math.min(result.value, r.value);
                        } while (r.output.length() > 0);
                        result.output = binaryInput.substring(pos + length);
                    } else {
                        length = Integer.parseInt(binaryInput.substring(pos, pos += 11), 2);
                        Result r = new Result(binaryInput.substring(pos));
                        for (int i = 0; i < length; i++) {
                            r = decodePacket(r.output);
                            result.value = Math.min(result.value, r.value);
                        }
                        result.output = r.output;
                    }
                    return result;

                case 3:
                    lengthTypeId = binaryInput.charAt(pos++);
                    log.debug("Decoding max operator type {}, version {}, id: {}, in length: {}", lengthTypeId, version, id, binaryInput.length());
                    result.value = Long.MIN_VALUE;
                    if (lengthTypeId == '0') {
                        length = Integer.parseInt(binaryInput.substring(pos, pos += 15), 2);
                        Result r = new Result(binaryInput.substring(pos, pos + length));
                        do {
                            r = decodePacket(r.output);
                            result.value = Math.max(result.value, r.value);
                        } while (r.output.length() > 0);
                        result.output = binaryInput.substring(pos + length);
                    } else {
                        length = Integer.parseInt(binaryInput.substring(pos, pos += 11), 2);
                        Result r = new Result(binaryInput.substring(pos));
                        for (int i = 0; i < length; i++) {
                            r = decodePacket(r.output);
                            result.value = Math.max(result.value, r.value);
                        }
                        result.output = r.output;
                    }
                    return result;

                case 5:
                    lengthTypeId = binaryInput.charAt(pos++);
                    log.debug("Decoding greater than operator type {}, version {}, id: {}, in length: {}", lengthTypeId, version, id, binaryInput.length());
                    if (lengthTypeId == '0') {
                        length = Integer.parseInt(binaryInput.substring(pos, pos += 15), 2);
                        Result r = new Result(binaryInput.substring(pos, pos + length));
                        r = decodePacket(r.output);
                        Result r2 = decodePacket(r.output);
                        result.value = (r.value > r2.value) ? 1L : 0L;
                        result.output = binaryInput.substring(pos + length);
                    } else {
                        length = Integer.parseInt(binaryInput.substring(pos, pos += 11), 2);
                        Result r = new Result(binaryInput.substring(pos));
                        r = decodePacket(r.output);
                        Result r2 = decodePacket(r.output);
                        result.value = (r.value > r2.value) ? 1L : 0L;
                        result.output = r2.output;
                    }
                    return result;

                case 6:
                    lengthTypeId = binaryInput.charAt(pos++);
                    log.debug("Decoding less than operator type {}, version {}, id: {}, in length: {}", lengthTypeId, version, id, binaryInput.length());
                    if (lengthTypeId == '0') {
                        length = Integer.parseInt(binaryInput.substring(pos, pos += 15), 2);
                        Result r = new Result(binaryInput.substring(pos, pos + length));
                        r = decodePacket(r.output);
                        Result r2 = decodePacket(r.output);
                        result.value = (r.value < r2.value) ? 1L : 0L;
                        result.output = binaryInput.substring(pos + length);
                    } else {
                        length = Integer.parseInt(binaryInput.substring(pos, pos += 11), 2);
                        Result r = new Result(binaryInput.substring(pos));
                        r = decodePacket(r.output);
                        Result r2 = decodePacket(r.output);
                        result.value = (r.value < r2.value) ? 1L : 0L;
                        result.output = r2.output;
                    }
                    return result;

                case 7:
                    lengthTypeId = binaryInput.charAt(pos++);
                    log.debug("Decoding equal operator type {}, version {}, id: {}, in length: {}", lengthTypeId, version, id, binaryInput.length());
                    if (lengthTypeId == '0') {
                        length = Integer.parseInt(binaryInput.substring(pos, pos += 15), 2);
                        Result r = decodePacket(binaryInput.substring(pos, pos + length));
                        Result r2 = decodePacket(r.output);
                        result.value = (r.value == r2.value) ? 1L : 0L;
                        result.output = binaryInput.substring(pos + length);
                    } else {
                        length = Integer.parseInt(binaryInput.substring(pos, pos += 11), 2);
                        Result r = decodePacket(binaryInput.substring(pos));
                        Result r2 = decodePacket(r.output);
                        result.value = (r.value == r2.value) ? 1L : 0L;
                        result.output = r2.output;
                    }
                    return result;
            }
        }
        return null;
    }

    int problem1() {
        decodePacket(binary);
        return versionNumbers;
    }

    long problem2() {
        return decodePacket(binary).value;
    }

    static class Result {
        String output;
        long value;

        public Result() {
        }

        public Result(String output) {
            this.output = output;
            this.value = 0;
        }

    }
}
