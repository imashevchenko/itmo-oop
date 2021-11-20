package Лаб3.Entities;

import Лаб3.Algorithms.Algorithm;
import Лаб3.Repository.Repository;
import java.util.List;

public class RestorePoint {
    private String name;
    private Algorithm algorithm;
    private Repository repository;
    private List<FileDesc> jobObjects;

    public RestorePoint(String name, List<FileDesc> jobObjects, Algorithm algorithm, Repository repository) {
        this.name = name;
        this.algorithm = algorithm;
        this.repository = repository;
        this.jobObjects = jobObjects;
    }

    public void create(){
        algorithm.processFiles(jobObjects, repository, name);
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
}
