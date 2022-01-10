package Лаб6.DAL;

import Лаб6.ReportManagementSystemException;

import java.util.List;

public interface IRepository {
    List<TaskDAL> getAll() throws ReportManagementSystemException;

    TaskDAL get(int id) throws ReportManagementSystemException;

    void create(TaskDAL task) throws ReportManagementSystemException;

    TaskDAL updateTask(int id, String fieldName, Object newValue, EmployeeDAL author) throws ReportManagementSystemException;

    void createSprintReport(List<DayReportDAL> dayReports) throws ReportManagementSystemException;

    void createDayReport(DayReportDAL dayReport) throws ReportManagementSystemException;

    void createEmployee(EmployeeDAL employee) throws ReportManagementSystemException;

    EmployeeDAL updateEmployee(String name, String fieldName, Object newValue) throws ReportManagementSystemException;

    void deleteEmployee(String name) throws ReportManagementSystemException;

    List<EmployeeDAL> getAllEmployees() throws ReportManagementSystemException;

    void saveReportConfig(DayReportDAL dayReport) throws ReportManagementSystemException;

    List<DayReportDAL> getAllReports() throws ReportManagementSystemException;
}
