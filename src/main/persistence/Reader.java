package persistence;

import com.google.gson.Gson;
import model.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Reader {

    public static Game readGame(File file) throws FileNotFoundException {
        return new Gson().fromJson(new FileReader(file), Game.class);
    }
}
