package aoc.aoc2022;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day20GrovePositioningSystem {

    List<Long> numbers = new ArrayList<>();
    CircularLinkedList cll = new CircularLinkedList();

    public Day20GrovePositioningSystem(List<String> inputLines) {
        int index = 0;
        for (String line : inputLines) {
            long number = Long.parseLong(line);
            numbers.add(number);
            cll.addNode(number, index);
            index++;
        }
    }

    long problem1() {
        for (int i = 0; i < numbers.size(); i++) {
            cll.move(i, numbers.get(i));
        }
        Node zeroNode = cll.findNode(0);
        Node node1 = cll.move(zeroNode, 1000);
        Node node2 = cll.move(node1, 1000);
        Node node3 = cll.move(node2, 1000);
        return node1.value + node2.value + node3.value;
    }

    long problem2() {
        long decryptionKey = 811589153;
        numbers.replaceAll(aLong -> aLong * decryptionKey);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < numbers.size(); j++) {
                cll.move(j, numbers.get(j));
            }
        }
        Node zeroNode = cll.findNode(0);
        Node node1 = cll.move(zeroNode, 1000);
        Node node2 = cll.move(node1, 1000);
        Node node3 = cll.move(node2, 1000);
        return (node1.value + node2.value + node3.value) * decryptionKey;
    }

    static class Node {
        long value;
        int index;
        Node next;
        Node previous;

        public Node(long value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    class CircularLinkedList {
        private Node head = null;

        public void addNode(long value, int index) {
            Node newNode = new Node(value, index);

            if (head == null) {
                head = newNode;
                head.next = head;
                head.previous = head;
            } else {
                head.previous.next = newNode;
                newNode.previous = head.previous;
                newNode.next = head;
                head.previous = newNode;
            }
        }

        public Node findNode(int searchValue) {
            Node currentNode = head;

            do {
                if (currentNode.value == searchValue) {
                    return currentNode;
                }
                currentNode = currentNode.next;
            } while (currentNode != head);
            return null;
        }

        Node move(Node node, int steps) {
            Node currentNode = node;
            for (int i = 0; i < steps; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        }

        void move(int index, long number) {
            // find node with index == index
            Node nodeToMove = head;
            while (nodeToMove.index != index) {
                nodeToMove = nodeToMove.next;
            }

            Node nodeToInsert = nodeToMove;
            number = number % (numbers.size() - 1);

            if (number < 0) {
                // Move left
                for (int i = 0; i < Math.abs(number); i++) {
                    nodeToInsert = nodeToInsert.previous;
                }
                // remove
                nodeToMove.previous.next = nodeToMove.next;
                nodeToMove.next.previous = nodeToMove.previous;

                // insert to the left
                nodeToInsert.previous.next = nodeToMove;
                nodeToMove.next = nodeToInsert;
                nodeToMove.previous = nodeToInsert.previous;
                nodeToInsert.previous = nodeToMove;

            } else if (number > 0) {
                // Move right
                for (int i = 0; i < number; i++) {
                    nodeToInsert = nodeToInsert.next;
                }

                // remove
                nodeToMove.previous.next = nodeToMove.next;
                nodeToMove.next.previous = nodeToMove.previous;

                // insert to the right
                nodeToInsert.next.previous = nodeToMove;
                nodeToMove.next = nodeToInsert.next;
                nodeToMove.previous = nodeToInsert;
                nodeToInsert.next = nodeToMove;
            }
        }
    }

}