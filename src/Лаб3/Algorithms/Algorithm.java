package Лаб3.Algorithms;

import Лаб3.Entities.FileDesc;
import Лаб3.Repository.Repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public abstract class Algorithm {

    public abstract void processFiles(List<FileDesc> jobObjects, Repository repository, String name);

    public static void createZip(FileDesc jobObject, ZipOutputStream zout) throws IOException {
        FileInputStream fis = new FileInputStream(jobObject.getFilePath().toFile());
        ZipEntry entry1 = new ZipEntry(jobObject.getName());
        zout.putNextEntry(entry1);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = fis.read(buffer)) > 0) {
            zout.write(buffer, 0, len);
        }
        zout.closeEntry();
    }

}
