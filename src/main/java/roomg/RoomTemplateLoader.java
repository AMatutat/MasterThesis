package roomg;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import stuff.DesignLabel;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * loads and stores roomtemplates from a json
 *
 * @author Andre Matutat
 */
public class RoomTemplateLoader {
    private List<RoomTemplate> roomTemplates;

    /**
     * Creates a RoomTemplateLoader and loads the template from the json. if the json is empty, the list is empty
     *
     * @param path path to json
     */
    public RoomTemplateLoader(String path) {
        this.readFromJson(path);
    }

    /**
     * Returns a list of RoomTemplates that have the corresponding DesignLabel
     *
     * @param label the DesignLabel, use ALL if you don't care
     * @return the list
     */
    public List<RoomTemplate> getRoomTemplates(DesignLabel label) {
        List<RoomTemplate> results = new ArrayList<>(roomTemplates);
        if (label != DesignLabel.ALL) results.removeIf(r -> r.getDesign() != label);
        return results;
    }

    /**
     * adds a template to the list
     *
     * @param template the template to add
     */
    public void addRoomTemplate(RoomTemplate template) {
        if (!roomTemplates.contains(template)) roomTemplates.add(template);
    }

    private void readFromJson(String path) {
        Type roomType = new TypeToken<ArrayList<RoomTemplate>>() {
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

    /**
     * Writes down the list to a json
     *
     * @param templates the list of template to save
     * @param path      where to save
     */
    public void writeToJSON(List<RoomTemplate> templates, String path) {
        Gson gson = new Gson();
        String json = gson.toJson(templates);
        try {
            System.out.println(templates.size());
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            System.out.println("File" + path + " not found");
        }
    }

}