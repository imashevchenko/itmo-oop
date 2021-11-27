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
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class SplitedStorageAlgorithm extends Algorithm {
    @Override
    public void processFiles(List<FileDesc> jobObjects, Repository repository, String name) {
        try {
            List<File> files = new ArrayList<>();
            for (FileDesc jobObject : jobObjects) {
                File file = new File(jobObject.getName()+"_"+name+".zip");
                FileOutputStream fout = new FileOutputStream(file);
                ZipOutputStream zout = new ZipOutputStream(fout);
                createZip(jobObject, zout);
                zout.close();
                files.add(file);
            }

            repository.add(files);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void extractFilesToOriginalLocation(RestorePoint restorePoint, Repository repository) throws BackupException {
        List<String> filenames = restorePoint.getJobObjects().stream()
                .map(fileDesc -> fileDesc.getName()+"_"+restorePoint.getName()+".zip")
                .collect(Collectors.toList());
        List<File> files = repository.extractFiles(filenames);
        try {
            for (File file : files) {
                ZipFile zip = new ZipFile(file);
                Enumeration entries = zip.entries();

                extractToOrigin(restorePoint, zip, entries);

                zip.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }



    @Override
    public void extractFilesToDifferentLocation(RestorePoint restorePoint, Repository repository, Path pathToLocation) throws BackupException {
        List<String> filenames = restorePoint.getJobObjects().stream()
                .map(fileDesc -> fileDesc.getName()+"_"+restorePoint.getName()+".zip")
                .collect(Collectors.toList());
        List<File> files = repository.extractFiles(filenames);
        try {
            for (File file : files) {
                ZipFile zip = new ZipFile(file);
                Enumeration entries = zip.entries();

                ZipEntry entry = (ZipEntry) entries.nextElement();
                write(zip.getInputStream(entry),
                        new BufferedOutputStream(new FileOutputStream(
                                new File(String.valueOf(pathToLocation), entry.getName()))));

                zip.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getType() {
        return "SplitedStorageAlgorithm";
    }

}
