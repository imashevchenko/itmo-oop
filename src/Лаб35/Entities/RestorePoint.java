package Лаб35.Entities;

import Лаб35.Algorithms.Algorithm;
import Лаб35.Exception.BackupException;
import Лаб35.Repository.Repository;

import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RestorePoint {
    private String name;
    private Algorithm algorithm;
    private Repository repository;
    private List<FileDesc> jobObjects;
    private Date date;

    public RestorePoint(String name, List<FileDesc> jobObjects, Algorithm algorithm, Repository repository, Date date) {
        this.name = name;
        this.algorithm = algorithm;
        this.repository = repository;
        this.jobObjects = jobObjects;
        this.date = date;
    }


    public void create() throws BackupException {
        if (jobObjects.isEmpty())
            throw new BackupException("jobObjects are empty");
        algorithm.processFiles(jobObjects, repository, name);

    }

    public void addJobObjects(List<FileDesc> newFiles) throws BackupException {
        if (algorithm.getType().equals("SingleStorageAlgorithm"))
            throw new BackupException("Unsupported operation with single storage");

        jobObjects.addAll(newFiles);
        algorithm.processFiles(newFiles, repository, name);
    }

    public void restoreToOriginalLocation() throws BackupException {
        algorithm.extractFilesToOriginalLocation(this, repository);
    }

    public void restoreToDifferentLocation(Path pathToLocation) throws BackupException {
        algorithm.extractFilesToDifferentLocation(this, repository, pathToLocation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestorePoint that = (RestorePoint) o;
        return name.equals(that.name) && repository.equals(that.repository)
                && jobObjects.equals(that.jobObjects) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, repository, jobObjects, date);
    }

    public String getName() {
        return name;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Repository getRepository() {
        return repository;
    }

    public List<FileDesc> getJobObjects() {
        return jobObjects;
    }

    public Date getDate() {
        return date;
    }


}
