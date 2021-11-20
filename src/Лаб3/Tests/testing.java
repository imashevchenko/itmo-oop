package Лаб3.Tests;

import Лаб3.Algorithms.SingleStorageAlgorithm;
import Лаб3.Algorithms.SplitedStorageAlgorithm;
import Лаб3.Entities.BackUpJob;
import Лаб3.Entities.FileDesc;
import Лаб3.Exception.BackupException;
import Лаб3.Repository.LocalRepository;

import java.nio.file.Path;


public class testing {

    public static void main(String[] args) throws BackupException {
        BackUpJob backUpJob = new BackUpJob(new SingleStorageAlgorithm(), new LocalRepository(Path.of("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Repository1")));
        backUpJob.addJobObject(new FileDesc("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\binsearch.in","binsearch"));
        backUpJob.addJobObject(new FileDesc("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\garland.in","garland"));
        backUpJob.addJobObject(new FileDesc("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\isheap.in","isheap"));
        backUpJob.createRestorePoint("first");

        BackUpJob backUpJob1 = new BackUpJob(new SplitedStorageAlgorithm(), new LocalRepository(Path.of("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Repository2")));
        backUpJob1.addJobObject(new FileDesc("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\binsearch.in","binsearch"));
        backUpJob1.addJobObject(new FileDesc("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\garland.in","garland"));
        backUpJob1.addJobObject(new FileDesc("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\isheap.in","isheap"));
        backUpJob1.createRestorePoint("first");
        backUpJob1.removeJobObject("binsearch");
        backUpJob1.createRestorePoint("second");
    }
}
