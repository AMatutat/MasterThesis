import java.util.ArrayList;
import java.util.Scanner;

/**
 * analyse graphg
 */
public class Main {
    //used by the scanner to end program
    public static boolean end = false;
    public static boolean backupSave = false;
    public static void main(String[] args) {
        MyScanner sc = new MyScanner("exit","save");
        String path = "analyse/graphg.csv";
        MyExcelWriter ex = new MyExcelWriter(path);
        //the threads
        ArrayList<Setup> setups = new ArrayList<>();
        String[] labels = new String[32];
        for (int i = 0; i < 32; i++)
            labels[i] = "Node " + i;

        //start values and parameters
        int nodecounter = 2;
        int edgecounter = 0;
        int endNodeCounter = 32;
        int endEdgeCounter = 10;
        int wantedruns = 10;

        //setup threads
        for (int i = nodecounter; i < endNodeCounter; i++)
            for (int j = edgecounter; j < endEdgeCounter; j++)
                setups.add(new Setup(nodecounter + i, edgecounter + j, labels, wantedruns, ex));

        //start threads
        for (Setup s : setups)
            s.start();
        System.out.println("Threads startet");

        //start scanner thread to exit program via shell
        new Thread(sc).start();

        boolean calculating = true;
        long time =System.currentTimeMillis();
        long autosave=60000*60*2; //1min*60*2 = 2h

        //run until exit command or all calculations are finished
        while (calculating && !end) {
            calculating = false;
            for (Setup s : setups)
                if (!s.getDone()) calculating = true;

            if(backupSave || System.currentTimeMillis()-time>autosave){
                ex.save();
                backupSave=false;
                time=System.currentTimeMillis();
            }
        }

        //if end via shell, interrupt all threads
        if (end)
            for (Setup s : setups)
                s.interrupt();

        //SAVE DATA
        ex.save();
        System.out.println("Finished");
        System.exit(0);
    }
}
