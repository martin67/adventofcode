package adventofcode2018;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
class Day8MemoryManeuver {

    @Data
    class Node {
        final int id;
        int length; // length of node
        int numberOfChildNodes;
        int numberOfMetadataEntries;
        int value = -1;
        final List<Node> childNodes = new ArrayList<>();
        final List<Integer> metadata = new ArrayList<>();

        Node(int id, List<Integer> numbers, int start) {
            this.id = id;
            this.length = 2;
            this.numberOfChildNodes = numbers.get(start);
            this.numberOfMetadataEntries = numbers.get(start + 1);
            //log.info("Constructing new node " + id + " at " + start);

            // create children
            for (int i = 0; i < numberOfChildNodes; i++) {
                Node child = new Node(id + 1, numbers, start + length);
                childNodes.add(child);
                length += child.length;
            }
            // create metadata
            for (int i = 0; i < numberOfMetadataEntries; i++) {
                metadata.add(numbers.get(start + length + i));
            }
            length += numberOfMetadataEntries;

            // also add it to the global nodelist so we can traverse all nodes later
            nodeList.add(this);
        }

    }

    private final List<Node> nodeList = new ArrayList<>();


    private Node readData(String input) {
        // Split string into a list
        List<Integer> inputList = Arrays.stream(input.split("\\s")).
                map(Integer::parseInt).
                collect(Collectors.toList());

        return new Node(1, inputList, 0);
    }


    int computeSumOfMetadata(String input) {

        int sum = 0;
        readData(input);

        for (Node node : nodeList) {
            sum += node.getMetadata().stream().mapToInt(n -> n).sum();
        }
        return sum;
    }


    int computeRootNode(String input) {
        Node start = readData(input);

        // first, compute all leaf nodes (the one without children)
        nodeList.stream().filter(node -> node.getNumberOfChildNodes() == 0).forEach(node -> node.setValue(node.getMetadata().stream().mapToInt(n -> n).sum()));

        // find nodes that have values = -1 and all their children have values != -1
        // those nodes can be computed

        // repeat until all nodes have a value

        while (nodeList.stream().filter(node -> node.getValue() != -1).count() < nodeList.size()) {
            log.info("Running while loop");
            for (Node node : nodeList) {
                if (node.getValue() == -1) {
                    // check that all children have a value
                    boolean ok = true;
                    for (Node n2 : node.getChildNodes()) {
                        if (n2.getValue() == -1) {
                            ok = false;
                        }
                    }
                    if (ok) {
                        // Compute value for nodes. metadata is the index for which child to get the value from
                        node.value = 0;
                        for (int i : node.getMetadata()) {
                            // check for valid index
                            if (i-1 < node.numberOfChildNodes) {
                                node.value += node.getChildNodes().get(i - 1).getValue();
                            }
                        }
                    }
                }
            }

            int values = (int) nodeList.stream().filter(node -> node.getValue() != -1).count();
            log.info("Number of nodes with values " + values + " of " + nodeList.size());
        }
        // Root node has id = 1, saved from earlier
        return start.getValue();
    }

}
