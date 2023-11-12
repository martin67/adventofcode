package aoc.aoc2022;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Slf4j
public class Day13DistressSignal {

    final List<PacketPair> packetPairs = new ArrayList<>();

    public Day13DistressSignal(List<String> inputLines) {
        String a = null;
        int index = 1;
        for (String line : inputLines) {
            if (!line.isEmpty()) {
                if (a == null) {
                    a = line;
                } else {
                    packetPairs.add(new PacketPair(index, a, line));
                    a = null;
                    index++;
                }
            }
        }
    }

    int problem1() {
        int result = 0;
        for (PacketPair packetPair : packetPairs) {
//            log.info("-----------");
//            List<Packet> myList = new ArrayList<>();
//            flatten(myList, packetPair.left2);
//            for (Packet p : myList) {
//                log.info("packet: {}", p);
//            }
//            log.info("###########");
//            log.info("Start: {}", packetPair.left2);
//            Iterator<Packet> iterator = packetPair.left2.iterator();
//            while (iterator.hasNext()) {
//                log.info("Next packet: {}", iterator.next());
//            }
//            log.info("***********");

            if (packetPair.isInOrder2(packetPair.left2, packetPair.right2)) {
                log.info("Packet pair {} is in order", packetPair.index);
                result += packetPair.index;
            } else {
                log.info("Packet pair {} is not in order", packetPair.index);
            }
        }
        return result;
    }

    void compare(PacketData left, String right) {

    }

    class PacketData {
        String start;

        public PacketData(String start) {
            this.start = start;
        }
    }

    void flatten(List<Packet> packetList, Packet start) {
        packetList.add(start);
        if (start.isNumber()) {
            log.debug("recurse number: {}", start.number);
        } else {
            log.debug("recurse list  : {}", start);
            while (!start.packets2.isEmpty()) {
                flatten(packetList, start.packets2.pop());
            }
        }
    }

    int problem2() {
        return 0;
    }


    class PacketPair {
        final int index;
        final Packet left;
        final Packet right;
        final Packet left2 = new Packet();
        final Packet right2 = new Packet();
        final String leftStr;
        final String rightStr;

        public PacketPair(int index, String leftStr, String rightStr) {
            this.index = index;
            this.leftStr = leftStr;
            this.rightStr = rightStr;

            left = new Packet(null);
            right = new Packet(null);
            createPacket(left, leftStr);
            createPacket(right, rightStr);
            createPacketQueue(left2, leftStr);
            createPacketQueue(right2, rightStr);
        }

        String createPacket(Packet parent, String input) {
            while (!input.isEmpty()) {
                if (input.startsWith("[")) {
                    // Packet contains other packets
                    Packet packet = new Packet(parent);
                    parent.packets.add(packet);
                    input = createPacket(packet, input.substring(1));
                } else if (input.startsWith("]")) {
                    return input.substring(1);
                } else if (input.startsWith(",")) {
                    input = input.substring(1);
                } else {
                    // must be a number

                    StringBuilder number = new StringBuilder();
                    int pos = 0;
                    while (input.charAt(pos) >= '0' && input.charAt(pos) <= '9') {
                        number.append(input.charAt(pos));
                        pos++;
                    }
                    parent.packets.add(new Packet(parent, Integer.parseInt(number.toString())));
                    input = input.substring(pos);
                }
            }
            return input;
        }

        String createPacketQueue(Packet packet, String in) {

            while (!in.isEmpty()) {
                if (in.charAt(0) == '[') {
                    Packet p = new Packet();
                    in = createPacketQueue(p, in.substring(1));
                    packet.packets2.add(p);
                } else if (in.charAt(0) == ']') {
                    return in.substring(1);
                } else if (in.charAt(0) == ',') {
                    in = in.substring(1);
                } else {
                    StringBuilder number = new StringBuilder();
                    int pos = 0;
                    while (in.charAt(pos) >= '0' && in.charAt(pos) <= '9') {
                        number.append(in.charAt(pos));
                        pos++;
                    }

                    Packet p = new Packet(Integer.parseInt(number.toString()));
                    packet.packets2.add(p);
                    in = in.substring(pos);
                }
            }
            return in;
        }

        Boolean isInOrder2(Packet leftPacket, Packet rightPacket) {
//            Packet leftPacket = left2.packets2.pop();
//            Packet rightPacket = right2.packets2.pop();
            List<Packet> leftList = new ArrayList<>();
            flatten(leftList, leftPacket);
            List<Packet> rightList = new ArrayList<>();
            flatten(rightList, rightPacket);
            ListIterator<Packet> leftIterator = leftList.listIterator();
            ListIterator<Packet> rightIterator = rightList.listIterator();

            boolean ready = false;
            leftPacket = leftIterator.next();
            rightPacket = rightIterator.next();
            while (!ready) {

                if (leftPacket == null) {
                    log.info("- Left side ran out of items, so inputs are in the right order");
                    return true;
                }
                if (rightPacket == null) {
                    log.info("- Right side ran out of items, so inputs are not in the right order");
                    return false;
                }

                if (leftPacket.isNumber() && rightPacket.isNumber()) {
                    log.debug("# Compare {} vs {}", leftPacket.number, rightPacket.number);
                    // both values are integers
                    if (leftPacket.number < rightPacket.number) {
                        log.info("- Left side is smaller, so inputs are in the right order");
                        return true;
                    } else if (leftPacket.number > rightPacket.number) {
                        log.info("- Right side is smaller, so inputs are not in the right order");
                        return false;
                    }

                    if (leftIterator.hasNext()) {
                        leftPacket = leftIterator.next();
                    } else {
                        log.info("- Left side ran out of items, so inputs are in the right order");
                        return true;
                    }
                    if (rightIterator.hasNext()) {
                        rightPacket = rightIterator.next();
                    } else {
                        log.info("- Right side ran out of items, so inputs are not in the right order");
                        return false;
                    }

                } else if (!leftPacket.isNumber() && !rightPacket.isNumber()) {
                    log.debug("- Compare {} vs {} ", leftPacket, rightPacket);
                    if (leftIterator.hasNext()) {
                        leftPacket = leftIterator.next();
                    } else {
                        log.info("- Left side ran out of items, so inputs are in the right order");
                        return true;
                    }
                    if (rightIterator.hasNext()) {
                        rightPacket = rightIterator.next();
                    } else {
                        log.info("- Right side ran out of items, so inputs are not in the right order");
                        return false;
                    }
                } else {
                    log.debug("- Compare {} vs {} ", leftPacket, rightPacket);
                    // special cases
//                    if (leftPacket.number == null && leftPacket.packets2.isEmpty()) {
//                        log.info("- Left side ran out of items, so inputs are in the right order");
//                        return true;
//                    }
//                    if (rightPacket.number == null && rightPacket.packets2.isEmpty()) {
//                        log.info("- Right side ran out of items, so inputs are not in the right order");
//                        return false;
//                    }

                    // One of the values is an integer. Convert to list
                    if (leftPacket.isNumber()) {
                        log.info("Mixed types; convert left to [{}] and retry comparison", leftPacket.number);
                        leftIterator.add(new Packet(leftPacket.number));
                        leftPacket.number = null;
                        leftIterator.previous();
                    } else {
                        log.info("Mixed types; convert right to [{}] and retry comparison", rightPacket.number);
                        rightIterator.add(new Packet(rightPacket.number));
                        rightPacket.number = null;
                        rightIterator.previous();
                    }
                }
            }
            log.error("#################### Never get here");
            return true;
        }

        Boolean isInOrder() {
            Packet leftPacket = left.packets.get(0);
            Packet rightPacket = right.packets.get(0);
            boolean ready = false;
            while (!ready) {
                if (leftPacket.number != null && rightPacket.number != null) {
                    log.info("# Compare {} vs {}", leftPacket.number, rightPacket.number);
                    // both values are integers
                    if (leftPacket.number < rightPacket.number) {
                        log.info("- Left side is smaller, so inputs are in the right order");
                        return true;
                    } else if (leftPacket.number > rightPacket.number) {
                        log.info("- Right side is smaller, so inputs are not in the right order");
                        return false;
                    } else {
                        leftPacket = leftPacket.getNext();
                        if (leftPacket == null) {
                            log.info("- Left side ran out of items, so inputs are in the right order");
                            return true;
                        }
                        rightPacket = rightPacket.getNext();
                        if (rightPacket == null) {
                            log.info("- Right side ran out of items, so inputs are not in the right order");
                            return false;
                        }
                    }
                } else if (!leftPacket.packets.isEmpty() && !rightPacket.packets.isEmpty()) {
                    log.info("- Compare {} vs {} ", leftPacket.packets, rightPacket.packets);
                    // Both values are lists
                    leftPacket = leftPacket.getNext();
                    if (leftPacket == null) {
                        log.info("Special 1");
                        return true;
                    }
                    rightPacket = rightPacket.getNext();
                    if (rightPacket == null) {
                        log.info("Special 2");
                        return false;
                    }
                } else {
                    log.info("- Compare {} vs {} ", leftPacket.packets, rightPacket.packets);
                    // special cases
                    if (leftPacket.number == null && leftPacket.packets.isEmpty()) {
                        log.info("- Left side ran out of items, so inputs are in the right order");
                        return true;
                    }
                    if (rightPacket.number == null && rightPacket.packets.isEmpty()) {
                        log.info("- Right side ran out of items, so inputs are not in the right order");
                        return false;
                    }

                    // One of the values is an integer. Convert to list
                    Packet intPacket = (leftPacket.number != null) ? leftPacket : rightPacket;
                    if (intPacket.number != null) {
                        log.info("Mixed types; convert {} to [{}] and retry comparison", (leftPacket.number != null) ? "left" : "right", intPacket.number);
                        Packet p = new Packet(intPacket, intPacket.number);
                        intPacket.packets.add(p);
                        intPacket.number = null;
                    }
                }

//                do {
//                    leftPacket = leftPacket.getNext();
//                } while (leftPacket != null && leftPacket.number == null);
//
//                do {
//                    rightPacket = rightPacket.getNext();
//                } while (rightPacket != null && rightPacket.number == null);
//
//                if (leftPacket == null && rightPacket == null) {
//                    return leftStr.length() < rightStr.length();
//                }
//                if (leftPacket == null) {
//                    return true;
//                }
//                if (rightPacket == null) {
//                    return false;
//                }
            }
            return false;
        }
    }


    class Packet implements Iterable<Packet> {
        Packet parent;
        Integer number;
        final List<Packet> packets = new ArrayList<>();
        final Deque<Packet> packets2 = new ArrayDeque<>();
        int nextIndex = 0;

        public Packet() {
        }

        public Packet(int number) {
            this.number = number;
        }

        public Packet(Packet parent) {
            this.parent = parent;
        }

        public Packet(Packet parent, int number) {
            this.parent = parent;
            this.number = number;
        }

        @NotNull
        @Override
        public Iterator<Packet> iterator() {
            return new PacketIterator(this);
        }

        boolean isNumber() {
            return number != null;
        }

        boolean isList() {
            return number == null;
        }

        Packet getNext() {
            if (parent == null) {
                log.debug("parent = null, {}", this);
                return null;
            }
            if (number == null) {
                if (packets.isEmpty()) {
                    log.info("end = null, {}", this);
                    return null;
                } else {
                    if (nextIndex < packets.size()) {
                        Packet nextPacket = packets.get(nextIndex);
                        nextIndex++;
                        return nextPacket;
                    } else {
                        return parent.getNext();
                    }
                }
            } else {
                return parent.getNext();
//
//                int index = parent.packets.indexOf(this);
//                if (index == parent.packets.size() - 1) {
//                    // last packet in list
//                    log.info("last");
//                    //log.info("last {}", this);
//                    return parent.getNext();
//                } else {
//                    parent.nextIndex++;
//                    return parent.packets.get(index + 1);
//                }
            }
        }

//        @Override
//        public String toString() {
//            String result;
//            if (number != null) {
//                result = Integer.toString(number);
//            } else {
//                result = "[";
//                for (int i = 0; i < packets.size(); i++) {
//                    Packet p = packets.get(i);
//                    String p2 = p.toString();
//                    result += p2;
//                    if (i < packets.size() - 1) {
//                        result += ",";
//                    }
//                }
//                result += "]";
//            }
//            return result;
//        }


        @Override
        public String toString() {
            String result;
            if (number != null) {
                result = Integer.toString(number);
            } else {
                result = "[";
                Iterator<Packet> it = packets2.iterator();
                while (it.hasNext()) {
                    Packet next = it.next();
                    result += next.toString();
                    if (it.hasNext()) {
                        result += ",";
                    }
                }
                result += "]";
            }
            return result;
        }
    }

    class PacketIterator implements Iterator<Packet> {
        Packet cursor;

        public PacketIterator(Packet cursor) {
            this.cursor = cursor;
        }

        @Override
        public boolean hasNext() {
            return !cursor.isNumber() && !cursor.packets2.isEmpty();
        }

        @Override
        public Packet next() {
            cursor = cursor.packets2.pop();
            return cursor;
        }

    }
}