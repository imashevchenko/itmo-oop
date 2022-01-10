package Лаб35.Filters;

import Лаб35.Entities.RestorePoint;
import Лаб35.Exception.BackupException;

import java.util.ArrayList;
import java.util.List;

public class NumberFilter implements Filter{

    int number;

    public NumberFilter(int number) {
        this.number = number;
    }

    @Override
    public List<RestorePoint> filter(List<RestorePoint> restorePoints) throws BackupException {
        if (number >= restorePoints.size())
            throw new BackupException("Incorrect number");
        if (number == 0)
            throw new BackupException("Impossible to delete all points");
        return new ArrayList<>(restorePoints.subList(0, restorePoints.size() - number));
    }
}
