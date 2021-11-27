package Лаб35.Tests;

import Лаб35.Algorithms.SingleStorageAlgorithm;
import Лаб35.Algorithms.SplitedStorageAlgorithm;
import Лаб35.Entities.BackUpJob;
import Лаб35.Entities.FileDesc;
import Лаб35.Exception.BackupException;
import Лаб35.Loggers.ConsoleLogger;
import Лаб35.Repository.LocalRepository;

import java.nio.file.Path;


public class testing {

    public static void main(String[] args) throws BackupException {
        BackUpJob backUpJob = new BackUpJob("main",new SingleStorageAlgorithm(),
                new LocalRepository(Path.of("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Repository1")),
                new ConsoleLogger());
        backUpJob.addJobObject(new FileDesc("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\binsearch.in","binsearch"));
        backUpJob.addJobObject(new FileDesc("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\garland.in","garland"));
        backUpJob.addJobObject(new FileDesc("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\isheap.in","isheap"));
        backUpJob.createRestorePoint("first");

        BackUpJob backUpJob1 = new BackUpJob("second",new SplitedStorageAlgorithm(),
                new LocalRepository(Path.of("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Repository2")),
                new ConsoleLogger());
        backUpJob1.addJobObject(new FileDesc("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\binsearch.in","binsearch"));
        backUpJob1.addJobObject(new FileDesc("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\garland.in","garland"));
        backUpJob1.addJobObject(new FileDesc("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\isheap.in","isheap"));
        backUpJob1.createRestorePoint("first");
        backUpJob1.removeJobObject("binsearch");
        backUpJob1.createRestorePoint("second");
    }
}
