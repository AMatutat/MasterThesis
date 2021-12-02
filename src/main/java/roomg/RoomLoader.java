package roomg;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import stuff.DesignLabel;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RoomLoader {
    private List<Room> roomTemplates;

    public RoomLoader(String path) {
        this.readFromJson(path);
    }

    public List<Room> getRoomTemplates(DesignLabel l) {
        List<Room> results = new ArrayList<>(roomTemplates);
        if (l != DesignLabel.ALL) results.removeIf(r -> r.getDesign() != l);
        return results;
    }

    public void addRoomTemplate(Room r) {
        if (!roomTemplates.contains(r)) roomTemplates.add(r);
    }

    private void readFromJson(String path) {
        Type roomType = new TypeToken<ArrayList<Room>>() {
        }.getType();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(path));
            roomTemplates = new Gson().fromJson(reader, roomType);
            if (roomTemplates == null) throw new NullPointerException("File is empty");
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("No Rooms to load in " + path);
            roomTemplates = new ArrayList<>();
        } catch (Exception e) {
            System.out.println("File Corrupted or other error");
            e.printStackTrace();
            roomTemplates = new ArrayList<>();
        }
    }

    public void writeToJSON(List<Room> rooms, String path) {
        Gson gson = new Gson();
        String json = gson.toJson(rooms);
        try {
            System.out.println(rooms.size());
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            System.out.println("File" + path + " not found");
        }
    }

}
