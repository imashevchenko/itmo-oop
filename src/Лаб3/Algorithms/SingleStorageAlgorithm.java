package Лаб3.Algorithms;

import Лаб3.Entities.FileDesc;
import Лаб3.Repository.Repository;

import java.io.*;
import java.util.List;
import java.util.zip.ZipOutputStream;

public class SingleStorageAlgorithm extends Algorithm {

    @Override
    public void processFiles(List<FileDesc> jobObjects, Repository repository, String name) {
        try {
            File file = new File("backup_"+name+".zip");
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

}
