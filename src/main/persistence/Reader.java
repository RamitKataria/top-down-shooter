package persistence;

import com.google.gson.Gson;
import model.Game;
import model.GameObject;
import model.MovingObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Reader {

    public static Game readGame(File file) throws FileNotFoundException {
        Gson gson = new Gson();
        List<List> retrievedData = gson.fromJson(new FileReader(file), List.class);
        List<MovingObject> movingObjects = retrievedData.get(0);
        List<GameObject> walls = retrievedData.get(1);
        return new Game(movingObjects, walls);
    }
}
