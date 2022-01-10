package Лаб6.UI;

import Лаб6.BLL.*;
import Лаб6.Common.TaskDTO;
import Лаб6.DAL.IRepository;
import Лаб6.Common.DayReportDTO;
import Лаб6.Common.EmployeeDTO;
import Лаб6.ReportManagementSystemException;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class SystemManagement {

    EmployeeManagement employeeManagement;
    TaskManagement taskManagement;
    ReportManagement reportManagement;

    IRepository repository;
    EmployeeDTO currentUser;

    public SystemManagement(IRepository repository, EmployeeDTO teamLead) throws ReportManagementSystemException {
        this.repository = repository;
        taskManagement = new TaskManagement(repository);
        employeeManagement = new EmployeeManagement(repository);
        if (employeeManagement.getChief()==null)
            employeeManagement.addChief(teamLead);
    }

    public void authorize(String name) throws ReportManagementSystemException {
        currentUser = employeeManagement.getEmployee(name);
        if (currentUser!= null){
            reportManagement = new ReportManagement(currentUser, taskManagement, repository);
        }
    }

    public void createReport() throws ReportManagementSystemException {
        reportManagement.createReport();
        currentUser.setLastReportTime(new Date());
        employeeManagement.updateEmployee(currentUser.getName(), "LastReportTime", new Date());
    }

    public void getEmployeesWithReports(){
        System.out.println(reportManagement.getDayReports().stream().map(DayReportDTO::getEmployee).map(EmployeeDTO::getName).collect(Collectors.toList()));
    }

    public void getEmployeesWithoutReport(){
        List<EmployeeDTO> employees = reportManagement.getDayReports().stream().map(DayReportDTO::getEmployee).collect(Collectors.toList());
        HashSet<EmployeeDTO> employees1 = new HashSet<>(employees);
        System.out.println(employeeManagement.getEmployees().stream().filter(employee -> !employees1.contains(employee)).map(EmployeeDTO::getName).collect(Collectors.toList()));
    }

    public void createSprintReport() throws ReportManagementSystemException {
        reportManagement.createSprintReport();
    }

    public void getAllEmployees(){
        System.out.println(employeeManagement.getEmployees().stream().map(EmployeeDTO::getName).collect(Collectors.toList()));
    }

    public void updateEmployee(String name, String fieldName, Object newValue) throws ReportManagementSystemException {
        employeeManagement.updateEmployee(name, fieldName, newValue);
    }

    public void createEmployee(String name, String chiefName) throws ReportManagementSystemException {
        employeeManagement.addEmployee(employeeManagement.getEmployee(name), employeeManagement.getEmployee(chiefName));
    }

    public void getAllTasks(){
        System.out.println(taskManagement.getTasks().stream().map(TaskDTO::getName).collect(Collectors.toList()));
    }

    public void getTaskByEmployee(String name) throws ReportManagementSystemException {
        System.out.println(taskManagement.getTasksByEmployee(employeeManagement.getEmployee(name)));
    }

    public void getTasksByChangesAuthor(String name) throws ReportManagementSystemException {
        taskManagement.getTasksByChangesAuthor(employeeManagement.getEmployee(name));
    }

    public void getTaskByID(int id) throws ReportManagementSystemException {
        taskManagement.getTaskById(id);
    }

    public void getTasksByEmployeesSubordinates(String name) throws ReportManagementSystemException {
        taskManagement.getTasksByEmployeesSubordinates(employeeManagement.getEmployee(name));
    }

    public void createTask(String name, String comment, String status, String employeeName) throws ReportManagementSystemException {
        taskManagement.createTask(name, comment, status, employeeManagement.getEmployee(employeeName));
    }

    public void updateTask(int id, String fieldName, String newValue) throws ReportManagementSystemException {
        if (fieldName.equals("name") || fieldName.equals("comment") || fieldName.equals("status")){
            taskManagement.updateTask(id, fieldName, newValue, currentUser);
        }
        else
            taskManagement.updateTask(id, fieldName, employeeManagement.getEmployee(newValue), currentUser);
    }

    public void getThisWeekTasks(){
        System.out.println(taskManagement.getThisWeekTasks());
    }
}
