package Лаб6.BLL;

import Лаб6.DAL.TaskDAL;

import java.util.ArrayList;
import java.util.Date;

public class DayReport {

    private ArrayList<TaskBLL> tasks;
    private Employee employee;
    private Date date;

    public DayReport(Employee employee) {
        this.tasks = new ArrayList<>();
        this.employee = employee;
        this.date=new Date();
    }


    public DayReport(Employee user, ArrayList<TaskBLL> tasks) {
        this.employee = user;
        this.tasks=tasks;
        this.date = new Date();
    }


    public void addEmployeesTasks(TaskBLL task){
        this.tasks.add(task);
    }

    public ArrayList<TaskBLL> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<TaskBLL> tasks) {
        this.tasks = tasks;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getDate() {
        return date;
    }
}
