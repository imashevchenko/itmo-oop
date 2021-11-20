package Лаб3.Algorithms;

import Лаб3.Entities.FileDesc;
import Лаб3.Repository.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
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

}
