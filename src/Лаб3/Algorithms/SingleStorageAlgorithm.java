package Лаб3.Algorithms;

import Лаб3.Entities.FileDesc;
import Лаб3.Entities.RestorePoint;
import Лаб3.Exception.BackupException;
import Лаб3.Repository.Repository;

import java.io.*;
import java.nio.file.Path;
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



    @Override
    public String getType() {
        return "SingleStorageAlgorithm";
    }


}
