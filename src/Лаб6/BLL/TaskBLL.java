package Лаб6.BLL;

import Лаб6.DAL.TaskDAL;
import Лаб6.Diff;
import Лаб6.ReportManagementSystemException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("rawtypes")
public class TaskBLL {

    private int id;
    Employee employee;
    String name;
    String comment;
    Date date;
    String status;

    private ArrayList<Diff> changes;

    public TaskBLL(TaskDAL taskDAL) {
        this.changes=taskDAL.getChanges();
        this.name = taskDAL.getName();
        this.comment = taskDAL.getComment();
        this.id = taskDAL.getId();
        this.date = taskDAL.getDate();
        this.status = taskDAL.getStatus();
        this.employee=taskDAL.getEmployee();
    }

    public TaskBLL(int id, String name, String comment, String status, Employee employee) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.date = new Date();
        this.status = status;
        this.changes=new ArrayList<>();
        this.employee = employee;
    }

    public List<Diff> getChangesAfterCertainDate(Date date) throws ReportManagementSystemException {
        List<Diff> changesAfter = changes.stream().filter(diff -> diff.getDate().after(date))
                .collect(Collectors.toList());
        if (changesAfter.isEmpty())
            throw new ReportManagementSystemException("No changes after provided date");
        return changesAfter;
    }

    public Date getChangeDate(String fieldname, Object oldValue, Object newValue) throws ReportManagementSystemException {
        Diff change = changes.stream().filter(diff -> diff.getFieldName().equals(fieldname)
                        && oldValue.equals(diff.getOldValue())
                        && newValue.equals(diff.getNewValue()))
                    .findFirst().orElse(null);
        if (change == null)
            throw new ReportManagementSystemException("No such change");
        return change.getDate();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Diff> getChanges() {
        return changes;
    }

    public Employee getEmployee() {
        return employee;
    }
}
