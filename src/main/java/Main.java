import graphg.CantBePlanarException;
import graphg.Graph;
import graphg.GraphG;
import graphg.NoSolutionException;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws CantBePlanarException, NoSolutionException {
        GraphG g = new GraphG();
        //g.writeToJSON(g.generateGraphs(5, 2),"5_2.json");

        List<Graph> so = null;
        try {
            so = g.readFromJson("5_2.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        so.forEach(s -> System.out.println(s.toDot()));
    }
}
