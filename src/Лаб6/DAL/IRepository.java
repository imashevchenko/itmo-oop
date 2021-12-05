package Лаб6.DAL;

import Лаб6.BLL.DayReport;
import Лаб6.BLL.Employee;
import Лаб6.ReportManagementSystemException;

import java.util.List;

public interface IRepository {
    List<TaskDAL> getAll() throws ReportManagementSystemException;

    TaskDAL get(int id) throws ReportManagementSystemException;

    void create(TaskDAL task) throws ReportManagementSystemException;

    TaskDAL updateTask(int id, String fieldName, Object newValue, Employee author) throws ReportManagementSystemException;

    void createSprintReport(List<DayReport> dayReports) throws ReportManagementSystemException;

    void createDayReport(DayReport dayReport) throws ReportManagementSystemException;

    void createEmployee(Employee employee) throws ReportManagementSystemException;

    Employee updateEmployee(String name, String fieldName, Object newValue) throws ReportManagementSystemException;

    void deleteEmployee(String name) throws ReportManagementSystemException;

    List<Employee> getAllEmployees() throws ReportManagementSystemException;

    void saveReportConfig(DayReport dayReport) throws ReportManagementSystemException;

    List<DayReport> getAllReports() throws ReportManagementSystemException;
}
