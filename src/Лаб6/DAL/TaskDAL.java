package Лаб6.DAL;

import Лаб6.BLL.TaskBLL;
import Лаб6.Diff;
import Лаб6.BLL.Employee;

import java.util.ArrayList;
import java.util.Date;

public class TaskDAL {
    private int id;
    String name;
    String comment;
    Date date;
    String status;
    Employee employee;

    private ArrayList<Diff> changes;

    public TaskDAL(int id, String name, String comment, String status, Employee employee) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.date = new Date();
        this.status = status;
        this.changes = new ArrayList<>();
        this.employee = employee;
    }

    public TaskDAL(TaskBLL taskBLL){
        this.id = taskBLL.getId();
        this.name = taskBLL.getName();
        this.comment = taskBLL.getComment();
        this.date = taskBLL.getDate();
        this.status = taskBLL.getStatus();
        this.changes = taskBLL.getChanges();
        this.employee = taskBLL.getEmployee();
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

    public Employee getEmployee() {
        return employee;
    }
}
