package Лаб35.Filters;

import Лаб35.Algorithms.Algorithm;
import Лаб35.Entities.RestorePoint;
import Лаб35.Exception.BackupException;

import java.util.*;
import java.util.stream.Collectors;

public class CombinedFilter implements Filter {
    Filter[] filters;
    boolean intersection;

    public CombinedFilter(boolean intersection, Filter... filters) {
        this.filters = filters;
        this.intersection = intersection;
    }

    @Override
    public List<RestorePoint> filter(List<RestorePoint> restorePoints) throws BackupException {
        Set<RestorePoint> restorePointSet = new HashSet<>();
        if (intersection) {
            for (int i = 0; i < filters.length; i++) {
                if (i == 0) {
                    restorePointSet.addAll(filters[i].filter(restorePoints));
                    continue;
                }
                restorePointSet.retainAll(filters[i].filter(restorePoints));
            }
        } else {
            for (Filter filter : filters)
                restorePointSet.addAll(filter.filter(restorePoints));
        }
        return new ArrayList<>(restorePointSet);
    }
}
