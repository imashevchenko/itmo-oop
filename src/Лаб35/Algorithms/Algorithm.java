package Лаб35.Algorithms;

import Лаб35.Entities.FileDesc;
import Лаб35.Entities.RestorePoint;
import Лаб35.Exception.BackupException;
import Лаб35.Repository.Repository;

import java.io.*;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public abstract class Algorithm {

    public abstract void processFiles(List<FileDesc> jobObjects, Repository repository, String name);

    public abstract void extractFilesToOriginalLocation(RestorePoint restorePoint, Repository repository) throws BackupException;

    public abstract void extractFilesToDifferentLocation(RestorePoint restorePoint, Repository repository, Path pathToLocation) throws BackupException;

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

    static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        out.close();
        in.close();
    }

    public void extractToOrigin(RestorePoint restorePoint, ZipFile zip, Enumeration entries) throws BackupException, IOException {
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            FileDesc fileDesc = restorePoint.getJobObjects().stream()
                    .filter(jobObject -> jobObject.getName().equals(entry.getName()))
                    .findFirst().orElse(null);
            if (fileDesc == null)
                throw new BackupException("Restore point is not full");
            File origin = new File(String.valueOf(fileDesc.getFilePath()));
            if (origin.exists())
                origin.delete();
            write(zip.getInputStream(entry),
                    new BufferedOutputStream(new FileOutputStream(
                            String.valueOf(origin))));
        }
    }

    public abstract String getType();

}
