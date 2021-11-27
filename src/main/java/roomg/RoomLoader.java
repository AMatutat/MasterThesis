package roomg;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import stuff.DesignLabel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RoomLoader {
    private List<Room> roomTemplates;

    public RoomLoader(String path) throws FileNotFoundException {
        this.readInRoomTemplates(path);
    }

    public List<Room> getRooms(DesignLabel l) {
        List<Room> results = new ArrayList<>(roomTemplates);
        if (l != DesignLabel.ALL)
            results.removeIf(r -> r.getDesign() != l);
        return results;
    }


    private void readInRoomTemplates(String path) throws FileNotFoundException {
        Type roomType = new TypeToken<ArrayList<Room>>() {
        }.getType();
        JsonReader reader = new JsonReader(new FileReader(path));
        roomTemplates = new Gson().fromJson(reader, roomType);
    }
}
