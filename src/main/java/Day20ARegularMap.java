import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class DAG {
    private Node start;
    private Set<Node> nodeSet = new HashSet<>();

    static class Node {
        private static int idCounter = 0;
        int id;
        char direction;
        List<Node> parents = new ArrayList<>();
        List<Node> children = new ArrayList<>();

        Node(char direction) {
            this.direction = direction;
            id = idCounter;
            idCounter++;
        }

        public String toString() {
            return id + " (" + direction + ")";
        }
    }

    static class AddNodesResult {
        List<Node> parents;
        List<Node> children;

        AddNodesResult(List<Node> parents, List<Node> children) {
            this.parents = parents;
            this.children = children;
        }
    }

    void toGML(String fileName) throws IOException {
        StringBuilder output = new StringBuilder();

        output.append("graph [ ");
        for (Node node : nodeSet) {
            output.append(String.format("node [ id %d label \"%d - %s\" ]\n", node.id, node.id, node.direction));
        }

        for (Node node : nodeSet) {
            for (Node child : node.children) {
                output.append(String.format("edge [ source %d target %d ]\n", node.id, child.id));
            }
        }
        for (Node node : nodeSet) {
            for (Node parent : node.parents) {
                output.append(String.format("edge [ source %d target %d ]\n", node.id, parent.id));
            }
        }

        output.append("]");

        Files.write(Paths.get(fileName), output.toString().getBytes());
    }

    void setup(Queue<Character> queue) {
        addNodes(start, queue);
    }


    // Add nodes
    // parent -> XXXXX -> next
    // the XXXXX is unknown. parent will have more than one child and next will have one or more
    // parents.
    // It will add all nodes from queue and add them as child or children
    // to the parent. It will return all parents to next (all "dangling" children)
    // so the call should be
    //   children = addNodes(parent, queue)
    //   next = new Node(queue.poll)
    //   next.parents = children
    //   last = next
    private AddNodesResult addNodes(Node parent, Queue<Character> queue) {

        // currentNode
        Node currentNode;
        // newChildren are the nodes that the node that calls addNodes will get as children.
        List<Node> newChildren = new ArrayList<>();
        List<Node> nextNodeParents = new ArrayList<>();

        AddNodesResult addNodesResult;

        System.out.println("****************** Starting addNodes,  queue: " + queue);
        if (parent == null) {
            parent = new Node(queue.poll());
            nodeSet.add(parent);
            start = parent;
        }
        currentNode = parent;

        while (!queue.isEmpty()) {
            //System.out.println();
            //System.out.println(queue);
            switch (queue.peek()) {
                case '(':
                    dumpNodes(queue.peek(), currentNode, parent, nextNodeParents, newChildren);
                    queue.poll();
                    addNodesResult = addNodes(currentNode, queue);  // when we get back from this call, the
                    //System.out.println("## Returning from recursive call: currentNode: " + currentNode + ", currentNode.parents: " + currentNode.parents + ", currentNode.children: " + currentNode.children);
                    //System.out.println("## Returning from recursive call: parent: " + parent);
                    //System.out.println("## Returning from recursive call: result.parents: " + addNodesResult.parents + ", result.children: " + addNodesResult.children);
                    currentNode.children.addAll(addNodesResult.children);
                    nextNodeParents.addAll(addNodesResult.parents);
                    break;

                case '|':
                    dumpNodes(queue.peek(), currentNode, parent, nextNodeParents, newChildren);
                    // The node just before a '| will be a parent to a node that will come later (when it's closed)
                    queue.poll(); // Skip the '|'
                    // if the | is followed by a ), the whole detour should be skipped
                    if (queue.peek() == ')') {
                        System.out.println("**** detour, removing");
                        parent.children.clear();
                    } else {
                        nextNodeParents.add(currentNode);
                    }
                    currentNode = parent; // start on next child. this will also add
                    break;

                case ')':
                    dumpNodes(queue.peek(), currentNode, parent, nextNodeParents, newChildren);
                    queue.poll();
                    nextNodeParents.add(currentNode); // save the last child so we can save them as parents to the node after the ')'
                    addNodesResult = new AddNodesResult(nextNodeParents, newChildren);
                    return addNodesResult;

                case '$':
                    queue.poll();
                    break;

                default:
                    dumpNodes(queue.peek(), currentNode, parent, nextNodeParents, newChildren);
                    // A new node will be added to the list. It's parent is the currentNode, unless the currentNode
                    // has had a branch recently - in that case use the nextNodeParents list.
                    // It will also
                    Node newNode = new Node(queue.poll());
                    nodeSet.add(newNode);
                    newNode.parents.add(currentNode);
                    currentNode.children.add(newNode);
                    currentNode = newNode;
                    break;
            }
        }

        System.out.println("Finished addNodes, queue empty");
        return null;
    }

    private void dumpNodes(char found, Node currentNode, Node parent, List<Node> nextNodeParents, List<Node> newChildren) {
//        System.out.println("Found: " + found);
//        System.out.println("Current node:" + currentNode + ", parents: " + currentNode.parents + ", children: " + currentNode.children);
//        System.out.println("parent: " + parent + ", parent.parents: " + parent.parents + ", parent.children: " + parent.children);
//        System.out.println("nextNodeParents: " + nextNodeParents + ", newChildren: " + newChildren);
    }

    int longestPath() {
        return getLongestPathLength(start);
    }

    private int getLongestPathLength(Node node) {

        if (node == null) return 0;
        int max = 0;
        for (Node child : node.children) {
            max = Math.max(getLongestPathLength(child), max);
        }
        return 1 + max;
    }
}


public class Day20ARegularMap {

    // Structure is a (unweighted) directed acyclic graph (DAG)
    private DAG dag;

    public Day20ARegularMap(String fileName, String output) throws IOException {
        dag = new DAG();
        readData(fileName);
        if (output != null) {
            dag.toGML(output);
        }
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        // Only one line
        String line = inputStrings.get(0);

        // put input string on queue
        Queue<Character> queue = new LinkedList<>();

        for (char c : line.toCharArray()) {
            if (c != '^' && c != '$') {
                queue.add(c);
            }
        }

        dag.setup(queue);
        System.out.println("Read queue, size: " + queue.size());
    }

    int largestNumberOfDoors() {
        return dag.longestPath();
    }

    int countRooms(int minNumberOfDoors) {
        return 0;
    }
}
