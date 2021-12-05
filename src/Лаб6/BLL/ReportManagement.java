package Лаб6.BLL;

import com.google.gson.Gson;
import Лаб6.DAL.IRepository;
import Лаб6.ReportManagementSystemException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReportManagement {
    private Employee user;
    private TaskManagement taskManagement;
    private ArrayList<DayReport> dayReports;

    private IRepository repository;

    public ReportManagement(Employee user, TaskManagement taskManagement, IRepository repository) throws ReportManagementSystemException {
        this.user = user;
        this.taskManagement = taskManagement;
        this.dayReports = new ArrayList<>();
        this.repository = repository;
        this.dayReports.addAll(repository.getAllReports());
    }

    public void createReport() throws ReportManagementSystemException {
        ArrayList<TaskBLL> tasks = taskManagement.getTasksByEmployee(user);
        tasks.addAll(taskManagement.getTasksByEmployeesSubordinates(user));
        DayReport dayReport = new DayReport(user, tasks);
        dayReports.add(new DayReport(user, tasks));
        repository.createDayReport(dayReport);
        repository.saveReportConfig(dayReport);

    }

    public void createSprintReport() throws ReportManagementSystemException {
        if (user.isTeamLead()){
            if (user.getSubordinates().stream().allMatch(employee -> new Date().getTime() - employee.LastReportTime.getTime() < new Date().getTime()-5.184e+9)){
                repository.createSprintReport(dayReports);
                System.out.println();
            }
        } else {
            throw new ReportManagementSystemException("Only team lead can make sprint report");
        }
    }

    public ArrayList<DayReport> getDayReports() {
        return dayReports;
    }
}
