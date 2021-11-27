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

public class ReplacementLoader {
    private List<Replacement> replacements;

    public ReplacementLoader(String path) throws FileNotFoundException {
        readInReplacements(path);
    }

    public List<Replacement> getReplacement(DesignLabel l) {
        List<Replacement> results = new ArrayList<>(replacements);
        if (l != DesignLabel.ALL)
            results.removeIf(r -> r.getDesign() != l);
        return results;
    }

    //ToDo
    private Replacement rotate90(Replacement r) {
        return new Replacement(r.getLayout(),r.getRotate(),r.getDesign());
    }

    private void readInReplacements(String path) throws FileNotFoundException {
        Type replacementType = new TypeToken<ArrayList<Replacement>>() {
        }.getType();
        JsonReader reader = new JsonReader(new FileReader(path));
        replacements = new Gson().fromJson(reader, replacementType);

        //add all rotations to list
        List<Replacement> toRotate = new ArrayList<>(replacements);
        toRotate.removeIf(r -> !r.getRotate());

        for (Replacement r : toRotate) {
            Replacement tmp = r;
            //90,180,270
            for (int i = 0; i < 3; i++) {
                tmp = rotate90(tmp);
                replacements.add(tmp);
            }
        }
    }


}
