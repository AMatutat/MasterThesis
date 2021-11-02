import java.util.Scanner;

public class MyScanner implements Runnable{


    private Scanner sc;
    private String scanFor;

    public MyScanner(String scanFor){
        sc= new Scanner(System.in);
        this.scanFor=scanFor;
    }

    @Override
    public void run() {
        System.out.println("Write '"+scanFor+"' to exit");
        while(!Main.end)
        if (sc.nextLine().equals(scanFor)){
            Main.end=true;
            System.out.println("force close");
        }
        sc.close();
    }
}
