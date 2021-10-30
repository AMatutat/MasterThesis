package graphg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

/**
 * @author Andre Matutat
 */
public class EdgeTest {

    private Edge e;
    private Node n1;
    private Node n2;

    @BeforeEach
    public void init(){
        n1= new Node("node1");
        n2= new Node("node2");
        e=new Edge(n1,n2);
    }

    @Test
    public void getConnectionHash_equalsNodeHashesSum(){
        assertEquals(n1.hashCode()+n2.hashCode(),e.getConnectionHash());
     }

    @Test
    public void toDot_equalsNodeNamesWithArrow(){
        assertEquals("node1->node2",e.toDot());
     }
}
