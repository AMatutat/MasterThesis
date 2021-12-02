package roomg;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import stuff.DesignLabel;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReplacementLoader {
    private List<Replacement> replacements;

    public ReplacementLoader(String path) {
        readFromJson(path);
    }

    public List<Replacement> getReplacements(DesignLabel l) {
        List<Replacement> results = new ArrayList<>(replacements);
        if (l != DesignLabel.ALL)
            results.removeIf(r -> r.getDesign() != l);
        return results;
    }

    private Replacement rotate90(final Replacement r) {
        int[][] originalLayout = r.getLayout();
        int mSize = originalLayout.length;
        int nSize = originalLayout[0].length;
        int[][] rotatedLayout = new int[nSize][mSize];
        for (int row = 0; row < mSize; row++)
            for (int col = 0; col < nSize; col++)
                rotatedLayout[col][mSize - 1 - row] = originalLayout[row][col];
        return new Replacement(rotatedLayout, r.canRotate(), r.getDesign());
    }

    private void readFromJson(String path) {
        Type replacementType = new TypeToken<ArrayList<Replacement>>() {
        }.getType();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(path));
            replacements = new Gson().fromJson(reader, replacementType);
            if (replacements == null) throw new NullPointerException("File is empty");
            //add all rotations to list
            List<Replacement> toRotate = new ArrayList<>(replacements);
            toRotate.removeIf(r -> !r.canRotate());

            for (Replacement r : toRotate) {
                Replacement tmp = r;
                //90,180,270
                for (int i = 0; i < 3; i++) {
                    tmp = rotate90(tmp);
                    replacements.add(tmp);
                }
            }

        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("No Replacements to load in " + path);
            replacements = new ArrayList<>();
        } catch (Exception e) {
            System.out.println("File Corrupted or other error");
            e.printStackTrace();
            replacements = new ArrayList<>();
        }

    }

    public void addReplacement(Replacement r) {
        if (!replacements.contains(r)) replacements.add(r);
    }

    public void writeToJSON(List<Replacement> rep, String path) {
        Gson gson = new Gson();
        String json = gson.toJson(rep);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            System.out.println("File" + path + " not found");
        }
    }

    //ToDo remove
    public static void createTemplates() {
        int xs = 4;
        int ys = 4;
        boolean rotate = false;
        int[][] squarelay = new int[xs][ys];
        for (int y = 0; y < ys; y++)
            for (int x = 0; x < xs; x++) {
                squarelay[x][y] = LevelElement.FLOOR.getValue();
            }

        String path = "./replacementLayouts/replacements.json";
        Replacement square = new Replacement(squarelay, rotate, DesignLabel.DEFAULT);
        ReplacementLoader rl = new ReplacementLoader(path);
        rl.addReplacement(square);
        rl.writeToJSON(rl.getReplacements(DesignLabel.ALL), path);
    }
}


