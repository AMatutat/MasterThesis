import java.util.Scanner;

public class MyScanner implements Runnable {


    private Scanner sc;
    private String scanForExit;
    private String scanForSave;

    public MyScanner(String scanForExit, String scanForSave) {
        sc = new Scanner(System.in);
        this.scanForExit = scanForExit;
        this.scanForSave = scanForSave;
    }

    @Override
    public void run() {
        System.out.println("Write '" + scanForExit + "' to save and exit");
        System.out.println("Write '" + scanForSave + "' to save");
        while (!Main.end) {
            String input = sc.nextLine();
            if (input.equals(scanForExit)) {
                Main.end = true;
                System.out.println("force close");
            } else if (input.equals(scanForSave)) {
                Main.backupSave = true;
            }
        }
        sc.close();
    }
}
