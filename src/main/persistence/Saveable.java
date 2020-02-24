package persistence;

import java.io.FileWriter;

// Represents saveable data
public interface Saveable {

    // MODIFIES: printWriter
    // EFFECTS: writes the saveable to printWriter
    void save(FileWriter fileWriter);
}
