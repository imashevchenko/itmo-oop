package Лаб3.Loggers;

import java.io.*;
import java.nio.file.Path;

public class FileLogger implements Logger{

    private Path path;

    public FileLogger(Path path){
        this.path=path;
    }

    @Override
    public void log(String message) {
        try (PrintWriter out = new PrintWriter(new FileWriter(path.toFile(), true))){
            out.println(message);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getType(){
        return "FileLogger";
    }

    public Path getPath() {
        return path;
    }
}
