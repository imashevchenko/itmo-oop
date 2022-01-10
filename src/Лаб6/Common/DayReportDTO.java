package Лаб6.Common;

import Лаб6.BLL.EmployeeManagement;
import Лаб6.DAL.DayReportDAL;
import Лаб6.DAL.TaskDAL;

import java.util.ArrayList;
import java.util.Date;

public class DayReportDTO {

    private ArrayList<TaskDTO> tasks;
    private EmployeeDTO employee;
    private Date date;

    public DayReportDTO(EmployeeDTO employee) {
        this.tasks = new ArrayList<>();
        this.employee = employee;
        this.date=new Date();
    }


    public DayReportDTO(EmployeeDTO user, ArrayList<TaskDTO> tasks) {
        this.employee = user;
        this.tasks=tasks;
        this.date = new Date();
    }

    public DayReportDTO(DayReportDAL dayReport){
        ArrayList<TaskDTO> tasks = new ArrayList<>();
        for (TaskDAL taskDAL : dayReport.getTasks())
            tasks.add(new TaskDTO(taskDAL.getId(), taskDAL.getName(), taskDAL.getComment(), taskDAL.getStatus(), EmployeeManagement.transferBack(taskDAL.getEmployee())));
        this.tasks=tasks;
        this.date=dayReport.getDate();
        this.employee=EmployeeManagement.transferBack(dayReport.getEmployee());
    }

    public void addEmployeesTasks(TaskDTO task){
        this.tasks.add(task);
    }

    public ArrayList<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public Date getDate() {
        return date;
    }
}
