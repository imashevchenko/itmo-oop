package Лаб35.Filters;

import Лаб35.Entities.RestorePoint;
import Лаб35.Exception.BackupException;

import java.util.List;

public interface Filter {

    List<RestorePoint> filter(List<RestorePoint> restorePoints) throws BackupException;
}
