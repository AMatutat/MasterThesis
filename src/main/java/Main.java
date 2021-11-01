import graphg.CantBePlanarException;
import graphg.Graph;
import graphg.NoSolutionException;
import graphg.Setup;

public class Main {
    public static void main(String[] args) {
        String path="file";
        String[] labels = new String[32];
        for (int i = 0; i < 32; i++)
            labels[i] = "Node " + i;

        int nodecounter = 5;
        int edgecounter = 4;

        Setup s = new Setup(nodecounter,edgecounter,labels,10000);

        try {
            s.run();
            s.printResult(path);
        } catch (CantBePlanarException e) {
            e.printStackTrace();
        }

    }
}
