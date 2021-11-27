package Лаб3.Loggers;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Logger {
    void log(String message);

    String getType();

}
