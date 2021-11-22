import graphg.CantBePlanarException;
import graphg.GraphG;
import graphg.NoSolutionException;

public class Main {
    public static void main(String[] args) throws CantBePlanarException, NoSolutionException {
        new GraphG().generateGraphs(5, 2);
    }
}
