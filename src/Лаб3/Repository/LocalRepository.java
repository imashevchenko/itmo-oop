package Лаб3.Repository;

import Лаб3.Entities.FileDesc;
import Лаб3.Entities.RestorePoint;
import Лаб3.Exception.BackupException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            File goal = new File(directoryPath +"\\"+ file.getName());
            if (goal.exists())
                goal.delete();
            Files.move(file.toPath(), Path.of(directoryPath +"\\"+ file.getName()));
        }
    }


    @Override
    public void deleteRestorePoints(List<RestorePoint> restorePointList) {
        for (RestorePoint restorePoint : restorePointList){
            if (restorePoint.getAlgorithm().getType().equals("SingleStorageAlgorithm")){
                File toDelete = new File(directoryPath +"\\"+ "backup_"+restorePoint.getName()+".zip");
                toDelete.delete();
            } else{
                for(FileDesc jobObject : restorePoint.getJobObjects()){
                    File toDelete = new File(directoryPath +"\\"+ jobObject.getName()+"_"+restorePoint.getName()+".zip");
                    toDelete.delete();
                }
            }
        }
    }

    @Override
    public void delete(List<String> fileNames) {
        for (String fileName : fileNames){
            File goal = new File(directoryPath +"\\"+ fileName);
            if (goal.exists())
                goal.delete();
        }
    }

    @Override
    public List<File> extractFiles(List<String> fileNames) throws BackupException {
        List<File> files = new ArrayList<>();
        for (String fileName : fileNames){
            File goal = new File(directoryPath +"\\"+ fileName);
            if (goal.exists())
                files.add(goal);
            else
                throw new BackupException(String.format("No such file in repository", fileName));
        }
        return files;
    }

    @Override
    public File extractFile(String filename) throws BackupException {
        File goal = new File(directoryPath +"\\"+ filename);
        if (goal.exists())
            return goal;
        throw new BackupException(String.format("No such file in repository", filename));
    }


    public Path getDirectoryPath() {
        return directoryPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalRepository that = (LocalRepository) o;
        return Objects.equals(directoryPath, that.directoryPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directoryPath);
    }
}
