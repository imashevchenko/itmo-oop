package Лаб3.Repository;

import Лаб3.Entities.FileDesc;
import Лаб3.Entities.RestorePoint;
import Лаб3.Exception.BackupException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Repository {


    void add(List<File> files) throws IOException;

    void deleteRestorePoints(List<RestorePoint> restorePointList);

    void delete(List<String> fileNames);

    List<File> extractFiles(List<String> fileNames) throws BackupException;

    File extractFile(String filename) throws BackupException;
}
