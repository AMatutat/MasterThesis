import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String path="analyse/excel.xls";
        String[] labels = new String[32];
        for (int i = 0; i < 32; i++)
            labels[i] = "Node " + i;


        MyExcelWriter ex = new MyExcelWriter(path);
        ArrayList <Setup> setups = new ArrayList<>();
        int nodecounter = 2;
        int edgecounter = 0;


        for (int i=0; i<32;i++)
            for (int j=0;j<10;j++)
                setups.add(new Setup(nodecounter+i,edgecounter+j,labels,100,ex,"Thread"+i+j));
        for (Setup s: setups)
            s.start();

        boolean calculating=true;
        while (calculating){
            calculating=false;
            for (Setup s: setups)
                if(!s.done) calculating=true;
        }
        ex.save();
    }
}
