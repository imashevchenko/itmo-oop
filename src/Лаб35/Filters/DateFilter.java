package Лаб35.Filters;

import Лаб35.Entities.RestorePoint;
import Лаб35.Exception.BackupException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DateFilter implements Filter{

    Date date;

    public DateFilter(Date date) {
        this.date = date;
    }


    @Override
    public List<RestorePoint> filter(List<RestorePoint> restorePoints) throws BackupException {
        List<RestorePoint> restorePointList = restorePoints.stream()
                .filter(restorePoint -> restorePoint.getDate().before(date))
                .collect(Collectors.toList());
        if (restorePointList.size() == restorePoints.size())
            throw new BackupException("Impossible to delete all points");
        return restorePointList;
    }
}
