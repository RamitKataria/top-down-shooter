package persistence;

import model.Game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private FileWriter fileWriter;

    // EFFECTS: constructs a writer that will write data to file
    public Writer(File file) throws IOException {
        fileWriter = new FileWriter(file);
    }

    // MODIFIES: this
    // EFFECTS: writes game to file
    public void write(Game game) throws IOException {
        game.save(fileWriter);
        fileWriter.close();
    }
}
