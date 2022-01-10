package Лаб6.DAL;

import java.util.ArrayList;
import java.util.Date;

public class DayReportDAL {

    private ArrayList<TaskDAL> tasks;
    private EmployeeDAL employee;
    private Date date;

    public DayReportDAL(ArrayList<TaskDAL> tasks, EmployeeDAL employee, Date date) {
        this.tasks = tasks;
        this.employee = employee;
        this.date = date;
    }

    public ArrayList<TaskDAL> getTasks() {
        return tasks;
    }

    public EmployeeDAL getEmployee() {
        return employee;
    }

    public Date getDate() {
        return date;
    }
}
