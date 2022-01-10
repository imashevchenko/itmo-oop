package Лаб6.DAL;

import Лаб6.Diff;

import java.util.ArrayList;
import java.util.Date;

public class TaskDAL {
    private int id;
    String name;
    String comment;
    Date date;
    String status;
    EmployeeDAL employee;

    private ArrayList<Diff> changes;

    public TaskDAL(int id, String name, String comment, String status, EmployeeDAL employee) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.date = new Date();
        this.status = status;
        this.changes = new ArrayList<>();
        this.employee = employee;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Diff> getChanges() {
        return changes;
    }

    public void addChange(Diff diff) {
        if (changes == null)
            changes = new ArrayList<>();
        this.changes.add(diff);
    }

    public EmployeeDAL getEmployee() {
        return employee;
    }
}
