package Лаб6.BLL;

import Лаб6.Common.DayReportDTO;
import Лаб6.Common.EmployeeDTO;
import Лаб6.Common.TaskDTO;
import Лаб6.DAL.DayReportDAL;
import Лаб6.DAL.EmployeeDAL;
import Лаб6.DAL.IRepository;
import Лаб6.DAL.TaskDAL;
import Лаб6.ReportManagementSystemException;

import java.util.ArrayList;
import java.util.Date;

public class ReportManagement {
    private EmployeeDTO user;
    private TaskManagement taskManagement;
    private ArrayList<DayReportDTO> dayReports;

    private IRepository repository;

    public ReportManagement(EmployeeDTO user, TaskManagement taskManagement, IRepository repository) throws ReportManagementSystemException {
        this.user = user;
        this.taskManagement = taskManagement;
        this.dayReports = new ArrayList<>();
        this.repository = repository;
        for (DayReportDAL dayReportDAL : repository.getAllReports())
            this.dayReports.add(new DayReportDTO(dayReportDAL));
    }

    static DayReportDAL transfer(DayReportDTO dayReport) {
        ArrayList<TaskDAL> tasks = new ArrayList<>();
        for (TaskDTO taskDTO : dayReport.getTasks()) {
            tasks.add(new TaskDAL(taskDTO.getId(), taskDTO.getName(), taskDTO.getComment(), taskDTO.getStatus(),
                    EmployeeManagement.transfer(taskDTO.getEmployee())));
        }
        EmployeeDAL employee = EmployeeManagement.transfer(dayReport.getEmployee());

        return new DayReportDAL(tasks, employee, dayReport.getDate());
    }


    public void createReport() throws ReportManagementSystemException {
        ArrayList<TaskDTO> tasks = taskManagement.getTasksByEmployee(user);
        tasks.addAll(taskManagement.getTasksByEmployeesSubordinates(user));
        DayReportDTO dayReport = new DayReportDTO(user, tasks);
        dayReports.add(new DayReportDTO(user, tasks));
        repository.createDayReport(ReportManagement.transfer(dayReport));
        repository.saveReportConfig(ReportManagement.transfer(dayReport));

    }

    public void createSprintReport() throws ReportManagementSystemException {
        if (user.isTeamLead()) {
            try {
                if (user.getSubordinates().stream()
                        .allMatch(employee -> new Date().getTime() - employee.getLastReportTime().getTime() < new Date().getTime() - 5.184e+9)) {
                    ArrayList<DayReportDAL> dayReportDALS = new ArrayList<>();
                    for (DayReportDTO dayReport : dayReports) {
                        dayReportDALS.add(ReportManagement.transfer(dayReport));
                    }
                    repository.createSprintReport(dayReportDALS);
                    System.out.println();
                }
            } catch (NullPointerException e){
                throw new ReportManagementSystemException("Reports not ready");
            }
        } else {
            throw new ReportManagementSystemException("Only team lead can make sprint report");
        }
    }

    public ArrayList<DayReportDTO> getDayReports() {
        return dayReports;
    }
}
