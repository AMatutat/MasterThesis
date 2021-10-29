package graphg;

/**
 * Node in graph. Represents rooms in the level
 *
 * @author André Matutat
 */
public final class Node {
    // Number of nodes connected to these nodes
    private int neighbourCount = 0;
    private String nodeName;

    public Node(final String name) {
        this.nodeName = name;
    }

    /** Decrease the neighbor counter. Cannot be less than 0 */
    public void removeNeighbour() {
        this.neighbourCount = Math.max(0, this.neighbourCount - 1);
    }

    /** Increase the neighbor counter */
    public void addNeighbour() {
        this.neighbourCount++;
    }

    public int getNeighbourCount() {
        return this.neighbourCount;
    }

    public String getNodeName() {
        return nodeName;
    }
}
