package Лаб35.Algorithms;

import Лаб35.Entities.FileDesc;
import Лаб35.Entities.RestorePoint;
import Лаб35.Exception.BackupException;
import Лаб35.Repository.Repository;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class SingleStorageAlgorithm extends Algorithm {

    @Override
    public void processFiles(List<FileDesc> jobObjects, Repository repository, String name) {
        try {
            File file = new File("backup_" + name + ".zip");
            FileOutputStream fout = new FileOutputStream(file);
            ZipOutputStream zout = new ZipOutputStream(fout);
            for (FileDesc jobObject : jobObjects) {
                SplitedStorageAlgorithm.createZip(jobObject, zout);
            }
            zout.close();

            repository.add(List.of(file));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void extractFilesToOriginalLocation(RestorePoint restorePoint, Repository repository) throws BackupException {
        try {
            ZipFile zip = new ZipFile(repository.extractFile("backup_" + restorePoint.getName() + ".zip"));
            Enumeration entries = zip.entries();

            extractToOrigin(restorePoint, zip, entries);

            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void extractFilesToDifferentLocation(RestorePoint restorePoint, Repository repository, Path pathToLocation) throws BackupException {
        try {
            ZipFile zip = new ZipFile(repository.extractFile("backup_" + restorePoint.getName() + ".zip"));
            Enumeration entries = zip.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                write(zip.getInputStream(entry),
                        new BufferedOutputStream(new FileOutputStream(
                                new File(String.valueOf(pathToLocation), entry.getName()))));
            }

            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void merge(List<RestorePoint> restorePointsForDelete, List<RestorePoint> restorePoints, Repository repository){
        List<String> fileNames = new ArrayList<>();
        for (RestorePoint restorePoint : restorePointsForDelete){
            fileNames.add("backup_"+restorePoint.getName()+".zip");
        }
        repository.delete(fileNames);
    }

    @Override
    public String getType() {
        return "SingleStorageAlgorithm";
    }


}
