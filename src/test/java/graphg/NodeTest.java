package graphg;

import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stuff.DesignLabels;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** @author Andre Matutat */
public class NodeTest {

    private Node n;
    private final String nodeName = "Test Node";

    @BeforeEach
    public void init() {
        n = new Node(nodeName, DesignLabels.DEFAULT);
    }

    @Test
    @Description("Test getNodeName")
    public void getNodeName_equalsNodeName() {
        assertEquals(nodeName, n.getNodeName());
    }

    @Test
    @Description("addNeighbour: basic")
    public void addNeighbour_add1toCount() {
        n.addNeighbour();
        assertEquals(1, n.getNeighbourCount());
        n.addNeighbour();
        assertEquals(2, n.getNeighbourCount());
    }

    @Test
    @Description("removeNeighbour: basic")
    public void removeNeighbour_remove1fromCount() {
        n.addNeighbour();
        n.addNeighbour();
        n.removeNeighbour();
        assertEquals(1, n.getNeighbourCount());
        n.removeNeighbour();
        assertEquals(0, n.getNeighbourCount());
    }

    @Test
    @Description("removeNeighbour: negative")
    public void removeNeighbour_hasNoNeighbour_equals0() {
        n.removeNeighbour();
        assertEquals(0, n.getNeighbourCount());
    }
}
