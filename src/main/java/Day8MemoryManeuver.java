import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Day8MemoryManeuver {

    @Data
    class Node {
        int id;
        int length; // length of node
        int numberOfChildNodes = 0;
        int numberOfMetadataEntries = 0;
        List<Node> childNodes = new ArrayList<>();
        List<Integer> metadata = new ArrayList<>();

        public Node(int id, List<Integer> numbers, int start) {
            this.id = id;
            this.length = 2;
            this.numberOfChildNodes = numbers.get(start);
            this.numberOfMetadataEntries = numbers.get(start + 1);
            log.info("Constructing new node " + id + " at " + start);

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

    List<Node> nodeList = new ArrayList<>();


    void readData(String input) {
        // Split string into a list
        List<Integer> inputList = Arrays.stream(input.split("\\s")).
                map(Integer::parseInt).
                collect(Collectors.toList());

        Node start = new Node(1, inputList, 0);

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

        return 0;
    }

}
