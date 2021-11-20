package Лаб3.Entities;

import Лаб3.Algorithms.Algorithm;
import Лаб3.Exception.BackupException;
import Лаб3.Repository.Repository;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class BackUpJob {

    private ArrayList<RestorePoint> restorePoints;
    private ArrayList<FileDesc> jobObjects;
    private Algorithm algorithm;
    private Repository repository;

    public BackUpJob(Algorithm algorithm, Repository repository) {
        this.restorePoints = new ArrayList<>();
        this.jobObjects = new ArrayList<>();
        this.algorithm=algorithm;
        this.repository=repository;
    }

    public void addJobObject(FileDesc file) throws BackupException {
        if (!file.getFilePath().toFile().exists())
            throw new BackupException("File doesn't exist");
        jobObjects.add(file);
    }

    public void removeJobObject(String name) throws BackupException {
        FileDesc jobObject = jobObjects
                .stream()
                .filter(jobObj -> jobObj.getName().equals(name))
                .findFirst()
                .orElse(null);
        if (jobObject == null)
            throw new BackupException("No such file");
        jobObjects.remove(jobObject);
    }

    public void createRestorePoint(String name){
        RestorePoint restorePoint = new RestorePoint(name, jobObjects, algorithm, repository);
        restorePoint.create();
        restorePoints.add(restorePoint);
    }
}
