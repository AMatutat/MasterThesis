package graphg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;

/**
 * due to the random factors it is difficult to test graphs in detail, therefore only error cases
 * are tested
 *
 * @author Andre Matutat
 */
public class GraphTest {

    private String[] labels;

    @BeforeEach
    public void init() {
        labels = new String[] {"A", "B", "C"};
    }

    @Test
    public void constructor_noNodes_throwsIllegalArgumentException() {
        Throwable exception =
                assertThrows(IllegalArgumentException.class, () -> new Graph(0, 2, labels));
    }

    @Test
    public void constructor_oneNode_throwsIllegalArgumentException() {
        Throwable exception =
                assertThrows(IllegalArgumentException.class, () -> new Graph(1, 2, labels));
    }

    @Test
    public void constructor_negativeNodes_throwsIllegalArgumentException() {
        Throwable exception =
                assertThrows(IllegalArgumentException.class, () -> new Graph(-1, 2, labels));
    }

    @Test
    public void constructor_negativeExtraEdges_throwsIllegalArgumentException() {
        Throwable exception =
                assertThrows(IllegalArgumentException.class, () -> new Graph(2, -1, labels));
    }

    @Test
    public void constructor_emptyLabels_throwsIllegalArgumentException() {
        String[] l = new String[0];
        Throwable exception =
                assertThrows(IllegalArgumentException.class, () -> new Graph(3, 2, l));
    }

    @Test
    public void constructor_notEnoughLabels_throwsIllegalArgumentException() {
        String[] l = new String[] {"A"};
        Throwable exception =
                assertThrows(IllegalArgumentException.class, () -> new Graph(3, 2, l));
    }

    @Test
    public void constructor_e3v6NotHold_throwsCantBePlanarException() {
        Throwable exception =
                assertThrows(CantBePlanarException.class, () -> new Graph(3, 25, labels));
    }
}
