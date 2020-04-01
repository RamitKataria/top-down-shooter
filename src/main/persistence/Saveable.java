package persistence;

import java.io.FileWriter;

// represents an object that can be saved
public interface Saveable {
    void save(FileWriter fileWriter);
}
