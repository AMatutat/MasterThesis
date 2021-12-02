import roomg.Replacement;
import roomg.ReplacementLoader;
import roomg.Room;
import roomg.RoomLoader;
import stuff.DesignLabel;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        RoomLoader rooml = new RoomLoader("./roomLayouts/roomTemplates.json");
        List<Room> rooms = rooml.getRoomTemplates(DesignLabel.ALL);
        ReplacementLoader repl= new ReplacementLoader("./replacementLayouts/replacements.json");
        List<Replacement> reps = repl.getReplacements(DesignLabel.ALL);

        Room r = rooms.get(0);

        for(int y=0;y<r.getLayout().length;y++){
            for(int x=0;x<r.getLayout()[0].length;x++)
                System.out.print(r.getLayout()[y][x]);
            System.out.println();
        }

        Replacement rep = reps.get(0);
        for(int y=0;y<rep.getLayout().length;y++){
            for(int x=0;x<rep.getLayout()[0].length;x++)
                System.out.print(rep.getLayout()[y][x]);
            System.out.println();
        }

        r.replace(reps);
        for(int y=0;y<r.getLayout().length;y++){
            for(int x=0;x<r.getLayout()[0].length;x++)
                System.out.print(r.getLayout()[y][x]);
            System.out.println();
        }

    }
}
