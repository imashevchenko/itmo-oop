package Лаб35.Tests;

import Лаб35.Entities.BackUpJob;
import Лаб35.Entities.RestorePoint;
import Лаб35.Exception.BackupException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class testingextra {
    static File configDir = new File("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Configurations");
    static List<BackUpJob> backUpJobs = new ArrayList<>();

    public static void main(String[] args) throws BackupException, IOException {
        for (File configFile : configDir.listFiles()){
            backUpJobs.add(new BackUpJob(configFile));
        }

        backUpJobs.get(0).createRestorePoint("second");
        for (RestorePoint restorePoint : backUpJobs.get(0).getRestorePoints())
            System.out.println(restorePoint.getDate());
        backUpJobs.get(0).merge(backUpJobs.get(0).numberFilter(1));
        System.out.println();
        for (RestorePoint restorePoint : backUpJobs.get(0).getRestorePoints())
            System.out.println(restorePoint.getDate());
        System.out.println();
        backUpJobs.get(0).createRestorePoint("third");
        for (RestorePoint restorePoint : backUpJobs.get(0).getRestorePoints())
            System.out.println(restorePoint.getDate());
        backUpJobs.get(0).merge(backUpJobs.get(0).dateFilter(new Date(backUpJobs.get(0).getRestorePoints().get(1).getDate().getTime()-15)));
        System.out.println();
        for (RestorePoint restorePoint : backUpJobs.get(0).getRestorePoints())
            System.out.println(restorePoint.getDate());
        backUpJobs.get(0).restoreToOriginalLocation("third");
    }
}
