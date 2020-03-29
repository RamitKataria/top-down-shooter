package persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private File file;
    private FileWriter fileWriter;

    // EFFECTS: constructs a writer that will write data to file
    public Writer(File file) throws IOException {
        this.file = file;
        fileWriter = new FileWriter(file);
    }

    // MODIFIES: this
    // EFFECTS: writes game to file
    public void write(Saveable saveable) throws IOException {
        saveable.save(fileWriter);
        fileWriter.close();
    }

    public boolean deleteSaveFile() {
        return file.delete();
    }
}
