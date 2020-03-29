package persistence;

import com.google.gson.Gson;
import model.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class GameReader {

    // EFFECTS: dummy constructor for autobot to fo through the class name
    public GameReader() {
    }

    // EFFECTS: read saved game from file and return it
    public static Game read(File file) throws FileNotFoundException {
        return new Gson().fromJson(new FileReader(file), Game.class);
    }
}
