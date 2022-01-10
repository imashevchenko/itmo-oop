package Лаб35.Repository;

import Лаб35.Entities.RestorePoint;
import Лаб35.Exception.BackupException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Repository {


    void add(List<File> files) throws IOException;

    void delete(List<String> fileNames);

    List<File> extractFiles(List<String> fileNames) throws BackupException;

    File extractFile(String filename) throws BackupException;
}
