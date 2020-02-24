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
    // EFFECTS: writes saveable to file
    public void write(Saveable saveable) {
        saveable.save(fileWriter);
    }

    // MODIFIES: this
    // EFFECTS: close print writer
    // NOTE: you MUST call this method when you are done writing data!
    public void close() throws IOException {
        fileWriter.close();
    }
}
