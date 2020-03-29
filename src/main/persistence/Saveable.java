package persistence;

import java.io.FileWriter;

public interface Saveable {
    void save(FileWriter fileWriter);
}
