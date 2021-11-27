package Лаб35.Entities;

import Лаб35.Algorithms.Algorithm;
import Лаб35.Algorithms.SingleStorageAlgorithm;
import Лаб35.Algorithms.SplitedStorageAlgorithm;
import Лаб35.Exception.BackupException;
import Лаб35.Loggers.ConsoleLogger;
import Лаб35.Loggers.FileLogger;
import Лаб35.Loggers.Logger;
import Лаб35.Repository.LocalRepository;
import Лаб35.Repository.Repository;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class BackUpJob {

    private static Path pathToConfigurationsDirectory = Path.of("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Configurations");

    private ArrayList<RestorePoint> restorePoints;
    private ArrayList<FileDesc> jobObjects;
    private Algorithm algorithm;
    private Repository repository;
    private String name;
    private Logger logger;

    public BackUpJob(String name, Algorithm algorithm, Repository repository, Logger logger) {
        this.name = name;
        this.restorePoints = new ArrayList<>();
        this.jobObjects = new ArrayList<>();
        this.algorithm = algorithm;
        this.repository = repository;
        this.logger = logger;
    }

    public BackUpJob(File file) throws IOException, BackupException {
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        this.name = String.valueOf(file.toPath().getFileName()).replace(".txt", "");
        String[] repositoryConfiguration = input.readLine().split("\\?");
        if (repositoryConfiguration[0].equals("Local repository"))
            this.repository = new LocalRepository(Path.of(repositoryConfiguration[1]));

        String algorithmConfiguration = input.readLine();
        if (algorithmConfiguration.equals("SingleStorageAlgorithm"))
            this.algorithm = new SingleStorageAlgorithm();
        else
            this.algorithm = new SplitedStorageAlgorithm();

        String loggerConfiguration = input.readLine();
        if (loggerConfiguration.equals("FileLogger"))
            this.logger = new FileLogger(Path.of(input.readLine()));
        else
            this.logger = new ConsoleLogger();


        String jobObjectsConfiguration = input.readLine();
        String[] jobObjectsDescriptions;
        if (jobObjectsConfiguration.isEmpty())
            this.jobObjects = new ArrayList<>();
        else {
            jobObjectsDescriptions = jobObjectsConfiguration.split("\\?");
            this.jobObjects = new ArrayList<>();
            for (String jobObjectDescription : jobObjectsDescriptions) {
                FileDesc fileDesc = new FileDesc(jobObjectDescription.split("=")[1], jobObjectDescription.split("=")[0]);
                this.jobObjects.add(fileDesc);
            }
        }

        int restorePointsNumber = parseInt(input.readLine());

        for (int i = 0; i < restorePointsNumber; i++) {
            String restorePointConfiguration = input.readLine();
            String restorePointName;
            long restorePointDate;
            List<FileDesc> jobObjs;
            this.restorePoints = new ArrayList<>();
            restorePointName = restorePointConfiguration.split("!")[0];
            restorePointDate = parseLong(restorePointConfiguration.split("!")[1]);
            jobObjs = new ArrayList<>();
            for (String jobObjectDescription : restorePointConfiguration.split("!")[2].split("\\?")) {
                FileDesc fileDesc = new FileDesc(jobObjectDescription.split("=")[1], jobObjectDescription.split("=")[0]);
                jobObjs.add(fileDesc);
            }
            this.restorePoints.add(new RestorePoint(restorePointName, jobObjs, algorithm, repository, new Date(restorePointDate)));
        }

    }

    public void addJobObject(FileDesc file) throws BackupException {
        if (!file.getFilePath().toFile().exists())
            throw new BackupException("File doesn't exist");
        jobObjects.add(file);
        logger.log(String.format("Added new job object: %s", file.getName()));
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
        logger.log(String.format("Removed job object: %s", name));
    }

    public void createRestorePoint(String name) throws BackupException {
        RestorePoint restorePoint = new RestorePoint(name, jobObjects, algorithm, repository, new Date());
        restorePoint.create();
        restorePoints.add(restorePoint);
        logger.log(String.format("Created restore point: %s", name));
    }

    public void saveToFile() throws IOException {
        File file = new File(pathToConfigurationsDirectory.toString() + "\\" + name + ".txt");
        if (file.exists())
            file.delete();
        file.createNewFile();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));

        if (repository instanceof LocalRepository)
            out.printf("Local repository?%s\n", ((LocalRepository) repository).getDirectoryPath());

        out.println(algorithm.getType());

        out.println(logger.getType());
        if (logger instanceof FileLogger)
            out.println(((FileLogger) logger).getPath());

        for (FileDesc jobObject : jobObjects) {
            out.printf("%s=%s?", jobObject.getName(), jobObject.getFilePath());
        }

        out.println();

        out.println(restorePoints.size());
        for (RestorePoint restorePoint : restorePoints) {
            out.printf("%s!%d!", restorePoint.getName(), restorePoint.getDate().getTime());
            for (FileDesc jobObject : restorePoint.getJobObjects())
                out.printf("%s=%s?", jobObject.getName(), jobObject.getFilePath());
            out.println();
        }
        out.close();
    }

    public List<RestorePoint> dateFilter(Date date) throws BackupException {
        List<RestorePoint> restorePointList = restorePoints.stream()
                .filter(restorePoint -> restorePoint.getDate().before(date))
                .collect(Collectors.toList());
        if (restorePointList.size() == restorePoints.size())
            throw new BackupException("Impossible to delete all points");
        return restorePointList;
    }

    public List<RestorePoint> numberFilter(int number) throws BackupException {
        if (number >= restorePoints.size())
            throw new BackupException("Incorrect number");
        if (number == 0)
            throw new BackupException("Impossible to delete all points");
        return restorePoints.subList(0, restorePoints.size() - number);
    }

    public List<RestorePoint> dateAndNumberFilter(int number, Date date, boolean bothConditions) throws BackupException {
        if (number >= restorePoints.size())
            throw new BackupException("Incorrect number");
        List<RestorePoint> restorePointList;
        if (bothConditions)
            restorePointList = restorePoints.stream()
                    .filter(restorePoint -> restorePoint.getDate().before(date) &&
                            restorePoints.indexOf(restorePoint) < restorePoints.size() - number)
                    .collect(Collectors.toList());
        else
            restorePointList = restorePoints.stream()
                    .filter(restorePoint -> restorePoint.getDate().before(date) ||
                            restorePoints.indexOf(restorePoint) < restorePoints.size() - number)
                    .collect(Collectors.toList());
        if (restorePointList.size() == restorePoints.size())
            throw new BackupException("Impossible to delete all points");
        return restorePointList;
    }

    public void merge(List<RestorePoint> restorePointList) throws BackupException {
        if (algorithm.getType().equals("SingleStorageAlgorithm")) {
            repository.deleteRestorePoints(restorePointList);
            restorePoints.removeAll(restorePointList);

        } else {
            restorePoints.removeAll(restorePointList);
            List<String> filesToDelete = new ArrayList<>();
            List<FileDesc> filesToSave = new ArrayList<>();
            for (RestorePoint restorePoint : restorePointList) {
                for (FileDesc jobObject : restorePoint.getJobObjects()) {
                    if (restorePoints.get(0).getJobObjects().contains(jobObject))
                        filesToDelete.add(jobObject.getName() + "_" + restorePoint.getName() + ".zip");
                    else
                        filesToSave.add(jobObject);
                }
            }
            repository.delete(filesToDelete);
            restorePoints.get(0).addJobObjects(filesToSave);
        }
    }

    public void restoreToOriginalLocation(String restorePointName) throws BackupException {
        RestorePoint restorePoint = restorePoints.stream().filter(restorePoint1 -> restorePoint1.getName().equals(restorePointName))
                .findFirst().orElse(null);
        if (restorePoint == null)
            throw new BackupException("No such restore point");

        restorePoint.restoreToOriginalLocation();
    }

    public void restoreToDifferentLocation(String restorePointName, Path pathToLocation) throws BackupException {
        RestorePoint restorePoint = restorePoints.stream().filter(restorePoint1 -> restorePoint1.getName().equals(restorePointName))
                .findFirst().orElse(null);
        if (restorePoint == null)
            throw new BackupException("No such restore point");

        restorePoint.restoreToDifferentLocation(pathToLocation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BackUpJob backUpJob = (BackUpJob) o;
        return restorePoints.equals(backUpJob.restorePoints) && jobObjects.equals(backUpJob.jobObjects) && repository.equals(backUpJob.repository) && name.equals(backUpJob.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restorePoints, jobObjects, repository, name);
    }

    public ArrayList<RestorePoint> getRestorePoints() {
        return restorePoints;
    }
}
