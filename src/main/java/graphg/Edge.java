package graphg;

/**
 * Represents an undirected connection between two nodes
 *
 * @author André Matutat
 */
public final class Edge {

    private final Node firstNode;
    private final Node secondNode;

    /**
     * Creates an undirected edge between the two nodes and calculates the combined hash value
     *
     * @param firstNode
     * @param secondNode
     * @throws IllegalArgumentException Loops are not supported.
     */
    public Edge(final Node firstNode, final Node secondNode) throws IllegalArgumentException {
        if (firstNode.equals(secondNode))
            throw new IllegalArgumentException("Loops are not supported.");
        this.firstNode = firstNode;
        this.secondNode = secondNode;
    }

    /** @return Edge in .dot representation */
    public String toDot() {
        return this.getFirstNode().getNodeName() + "->" + this.secondNode.getNodeName();
    }

    public Node getFirstNode() {
        return this.firstNode;
    }

    public Node getSecondNode() {
        return this.secondNode;
    }

    /**
     * Checks if two edges connect the same two nodes
     *
     * @param e
     * @return whether the edges are the same
     */
    public boolean equals(Edge e) {
        return ((e.getFirstNode().equals(getFirstNode())
                        || e.getFirstNode().equals(this.getSecondNode()))
                && (e.getSecondNode().equals(getFirstNode())
                        || e.getSecondNode().equals(this.getSecondNode())));
    }
}
