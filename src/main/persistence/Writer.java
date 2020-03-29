package persistence;

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
    // EFFECTS: writes object to file
    public void write(Saveable saveable) throws IOException {
        saveable.save(fileWriter);
        fileWriter.close();
    }
}
