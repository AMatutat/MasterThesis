package graph;

/**
 * Represents an undirected connection between two nodes
 *
 * @author André Matutat
 */
public final class Edge {

    private final Node firstNode;
    private final Node secondNode;
    private final int hashValue;

    /**
     * Creates an undirected edge between the two nodes and calculates the combined hash value
     * @param firstNode
     * @param secondNode
     */
    public Edge (final Node firstNode, final Node secondNode) {
        this.firstNode =firstNode;
        this.secondNode =secondNode;
        hashValue = firstNode.hashCode()+secondNode.hashCode();
    }

    /**
     *
     * @return The ConnectionHash is the sum of the hash values of both nodes
     */
    public int getConnectionHash(){
        return hashValue;
    }

    /**
     *
     * @return Edge in .dot representation
     */
    public String toDot(){
        return firstNode.getNodeName()+"->"+ secondNode.getNodeName();
    }
}
