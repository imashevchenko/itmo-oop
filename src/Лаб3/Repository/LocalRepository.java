package Лаб3.Repository;

import Лаб3.Entities.FileDesc;
import Лаб3.Exception.BackupException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LocalRepository implements Repository{

    private final Path directoryPath;

    public LocalRepository(Path directoryPath) throws BackupException {
        if (!directoryPath.toFile().isDirectory())
            throw new BackupException("Wrong name for local repository");
        this.directoryPath = directoryPath;
    }

    @Override
    public void add(List<File> files) throws IOException {
        for (File file : files){
            Files.move(file.toPath(), Path.of(directoryPath +"\\"+ file.getName()));
        }
    }
}
