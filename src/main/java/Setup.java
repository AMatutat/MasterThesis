import graphg.CantBePlanarException;
import graphg.Graph;
import graphg.NoSolutionException;


public class Setup implements Runnable {

    private Thread thread;
    private MyExcelWriter ex;
    private boolean done = false;
    private boolean interrupt = false;
    //confifuration parameter
    private final int nodes;
    private final int edges;
    private final String[] labels;
    private final int wantedRuns;
    //counters
    private int totalLoops = 0;
    private int minimalLoops = Integer.MAX_VALUE;
    private int maximalLoops = Integer.MIN_VALUE;
    private int solutions = 0;
    private int fails = 0;
    private double averageLoops = 0;

    /**
     * creates a thread that will setup graphg with the given parameter
     * @param nodes
     * @param edges
     * @param labels
     * @param wantedRuns
     * @param ex
     */
    public Setup(int nodes, int edges, String[] labels, int wantedRuns, MyExcelWriter ex) {
        this.nodes = nodes;
        this.edges = edges;
        this.labels = labels;
        this.wantedRuns = wantedRuns;
        this.ex = ex;
        thread = new Thread(this);
    }

    /**
     * true if all calculations are finished
     * @return
     */
    public boolean getDone(){
        return this.done;
    }

    /**
     * start calculation, after finish results will be written in the workbook sheet
     */
    public void start() {
        thread.start();
    }

    /**
     * interrupt thread, no data will be written
     */
    public void interrupt() {
        thread.interrupt();
        this.interrupt = true;
    }

    @Override
    public void run() {
        try {
            Graph g = null;
            for (int runs = 0; runs < wantedRuns; runs++) {
                if (interrupt) throw new InterruptedException();

                try {
                    g = new Graph(nodes, edges, labels);
                    int loops = g.getBreakAfter() - g.getUntilBreak();
                    solutions++;
                    totalLoops += loops;
                    maximalLoops = Math.max(maximalLoops, loops);
                    minimalLoops = Math.min(minimalLoops, loops);
                } catch (NoSolutionException e) {
                    fails++;
                } catch (CantBePlanarException | IllegalArgumentException e) {
                    runs = wantedRuns;
                    solutions = -1;
                } finally {
                    try {
                        g.resetUntilBreak();
                    } catch (NullPointerException e) {

                    }
                }
            }

            if (solutions > 0)
                averageLoops = totalLoops / solutions;
            this.printResult();
            done = true;
        } catch (InterruptedException e) {
        }
    }

    private void printResult() {
        ex.addEntry(nodes, edges, wantedRuns, solutions, fails, minimalLoops, maximalLoops, averageLoops);
    }
}
