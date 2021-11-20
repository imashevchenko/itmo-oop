package Лаб3.Entities;

import java.nio.file.Path;

public class FileDesc {
    private Path filePath;
    private String name;

    public FileDesc(String filePath, String name) {
        this.filePath = Path.of(filePath);
        this.name = name;
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getName() {
        return name;
    }
}
