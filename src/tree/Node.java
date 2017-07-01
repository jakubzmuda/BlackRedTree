package tree;


public class Node {

    public static final Node nil = new Node(-1);

    int key = -1;
    Color color = Color.BLACK;
    Node left = nil;
    Node right = nil;
    Node parent = nil;

    public Node(int key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "\n------\n" +
                "key [" + key + "]\n" +
                "color [" + color + "]\n" +
                printNodeDescription("parent", parent) +
                printNodeDescription("left", left) +
                printNodeDescription("right", right) +
                "------\n" +
                printWholeNode(left) +
                printWholeNode(right);
    }

    private String printNodeDescription(String name, Node node) {
        return node != nil ? name +  " [" + node.key +"]\n" : "There is no " + name + " node\n";
    }

    private String printWholeNode(Node node) {
        return node != nil? node.toString() : "";
    }
}