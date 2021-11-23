import graphg.CantBePlanarException;
import graphg.Graph;
import graphg.GraphG;
import graphg.NoSolutionException;

public class Main {
    public static void main(String[] args) throws CantBePlanarException, NoSolutionException {
        GraphG g= new GraphG();
        g.writeToJSON(g.generateGraphs(8, 6),"8_6.json");

    }
}
