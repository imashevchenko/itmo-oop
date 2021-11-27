package Лаб35.Entities;

import java.nio.file.Path;
import java.util.Objects;

public class FileDesc {
    private Path filePath;
    private String name;

    public FileDesc(String filePath, String name) {
        this.filePath = Path.of(filePath);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDesc fileDesc = (FileDesc) o;
        return filePath.equals(fileDesc.filePath) && name.equals(fileDesc.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath, name);
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getName() {
        return name;
    }
}
