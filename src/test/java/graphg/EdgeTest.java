package graphg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stuff.DesignLabels;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Andre Matutat
 */
public class EdgeTest {

    private Edge e;
    private Node n1;
    private Node n2;

    @BeforeEach
    public void init() {
        n1 = new Node("node1", DesignLabels.DEFAULT);
        n2 = new Node("node2", DesignLabels.DEFAULT);
        e = new Edge(n1, n2);
    }

    @Test
    public void equals_sameEdge_True() {
        assertTrue(e.equals(e));
    }

    @Test
    public void equals_differentNodes_False() {
        Edge e2 = new Edge(new Node("n1", DesignLabels.DEFAULT), new Node("n2", DesignLabels.DEFAULT));
        assertFalse(e.equals(e2));
    }

    @Test
    public void equals_sameNodes_True() {
        Edge e2 = new Edge(n1, n2);
        assertTrue(e.equals(e2));
    }

    @Test
    public void equals_sameNodesChangesDirection_True() {
        Edge e2 = new Edge(n2, n1);
        assertTrue(e.equals(e2));
    }

    @Test
    public void equals_differentFirstNode_False() {
        Edge e2 = new Edge(new Node("n1", DesignLabels.DEFAULT), n2);
        assertFalse(e.equals(e2));
    }

    @Test
    public void equals_differentSecondNode_False() {
        Edge e2 = new Edge(n1, new Node("n2", DesignLabels.DEFAULT));
        assertFalse(e.equals(e2));
    }

    @Test
    public void toDot_equalsNodeNamesWithArrow_True() {
        assertEquals("node1->node2", e.toDot());
    }
}
