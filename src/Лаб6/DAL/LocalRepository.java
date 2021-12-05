package Лаб6.DAL;

import com.google.gson.Gson;
import Лаб6.BLL.DayReport;
import Лаб6.BLL.Employee;
import Лаб6.BLL.TaskBLL;
import Лаб6.Diff;
import Лаб6.ReportManagementSystemException;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class LocalRepository implements IRepository {

    private final Path path;
    private final Path pathToEmployees;
    private final Path pathToReports;
    private final Path pathToSprintReport;
    private final Path pathToConfigReports;


    public LocalRepository(Path path, Path pathToEmployees, Path pathToReports, Path pathToSprintReport, Path pathToConfigReports) {
        this.path = path;
        this.pathToEmployees = pathToEmployees;
        this.pathToReports = pathToReports;
        this.pathToSprintReport = pathToSprintReport;
        this.pathToConfigReports = pathToConfigReports;
    }

    @Override
    public List<TaskDAL> getAll() throws ReportManagementSystemException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(path)));
            String line;
            List<TaskDAL> ans = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                Gson gson = new Gson();
                ans.add(gson.fromJson(line, TaskDAL.class));
            }
            return ans;
        } catch (IOException e) {
            throw new ReportManagementSystemException("Bad file path");
        }
    }

    @Override
    public TaskDAL get(int id) throws ReportManagementSystemException {

        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(path)))) {
            String line;
            while ((line = br.readLine()) != null) {
                Gson gson = new Gson();
                TaskDAL taskDAL = gson.fromJson(line, TaskDAL.class);
                if (taskDAL.getId() == id) {
                    return taskDAL;
                }
            }
            throw new ReportManagementSystemException("Task not founded");
        } catch (IOException e) {
            throw new ReportManagementSystemException("Bad file path");
        }
    }

    @Override
    public void create(TaskDAL task) throws ReportManagementSystemException {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(path.toFile(), true));
            Gson gson = new Gson();
            out.println(gson.toJson(task));
            out.close();
        } catch (IOException e) {
            throw new ReportManagementSystemException("Bad file path");
        }
    }

    @Override
    public TaskDAL updateTask(int id, String fieldName, Object newValue, Employee author) throws ReportManagementSystemException {
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(path)))) {
            String line;
            List<String> newLines = new ArrayList<>();
            TaskDAL updated = null;
            try {
                while ((line = br.readLine()) != null) {
                    Gson gson = new Gson();
                    TaskDAL taskDAL = gson.fromJson(line, TaskDAL.class);
                    if (taskDAL.getId() == id) {
                        Field field = TaskDAL.class.getDeclaredField(fieldName);
                        taskDAL.addChange(new Diff<>(fieldName, field.get(taskDAL), newValue, author));
                        field.set(taskDAL, newValue);
                        updated = taskDAL;
                    }
                    newLines.add(gson.toJson(taskDAL));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                System.out.println("Wrong field type provided");
            }
            Files.write(path, newLines);
            return updated;
        } catch (IOException e) {
            throw new ReportManagementSystemException("Bad file path");
        }
    }

    @Override
    public void createSprintReport(List<DayReport> dayReports) throws ReportManagementSystemException {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(pathToSprintReport.toFile()));
            for (DayReport dayReport : dayReports) {
                out.println("Report author : " + dayReport.getEmployee().getName());
                out.println(dayReport.getDate().getTime());
                for (TaskBLL task : dayReport.getTasks()) {
                    out.printf("Task name : %s\nTask comment : %s\nTask status : %s\n Task id : %d",
                            task.getName(), task.getComment(), task.getStatus(), task.getId());
                    for (Diff diff : task.getChanges()) {
                        out.printf("Changed: %s %s %s %s", diff.getDate(), diff.getAuthor().getName(), diff.getFieldName(), diff.getNewValue());
                    }
                }
                out.println();
            }
            out.close();
        } catch (IOException e) {
            throw new ReportManagementSystemException("Bad file path");
        }
    }

    @Override
    public void createDayReport(DayReport dayReport) throws ReportManagementSystemException {

        try {
            PrintWriter out = new PrintWriter(new FileWriter(pathToReports.toFile(), true));
            out.println("Report author : " + dayReport.getEmployee().getName());
            out.println(dayReport.getDate().getTime());
            for (TaskBLL task : dayReport.getTasks()) {
                out.printf("Task name : %s\nTask comment : %s\nTask status : %s\n Task id : %d\n",
                        task.getName(), task.getComment(), task.getStatus(), task.getId());
                for (Diff diff : task.getChanges()) {
                    Gson gson = new Gson();
                    out.print(gson.toJson(diff) + " ");
                }
                out.println();
            }
            out.close();
        } catch (IOException e) {
            throw new ReportManagementSystemException("Bad file path");
        }
    }

    @Override
    public void createEmployee(Employee employee) throws ReportManagementSystemException {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(pathToEmployees.toFile(), true));
            Gson gson = new Gson();
            out.println(gson.toJson(employee));
            out.close();
        } catch (IOException e) {
            throw new ReportManagementSystemException("Bad file path");
        }
    }

    @Override
    public Employee updateEmployee(String name, String fieldName, Object newValue) throws
            ReportManagementSystemException {
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(pathToEmployees)))) {
            String line;
            List<String> newLines = new ArrayList<>();
            Employee updated = null;
            try {
                while ((line = br.readLine()) != null) {
                    Gson gson = new Gson();
                    Employee employee = gson.fromJson(line, Employee.class);
                    if (employee.getName().equals(name)) {
                        Employee.class.getDeclaredField(fieldName).set(employee, newValue);
                        updated = employee;
                    }
                    newLines.add(gson.toJson(employee));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                System.out.println("Wrong field type provided");
            }
            Files.write(pathToEmployees, newLines, StandardOpenOption.TRUNCATE_EXISTING);
            return updated;
        } catch (IOException e) {
            throw new ReportManagementSystemException("Bad file path");
        }
    }

    @Override
    public void deleteEmployee(String name) throws ReportManagementSystemException {
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(pathToEmployees)))) {
            String line;
            List<String> newLines = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                Gson gson = new Gson();
                Employee employee = gson.fromJson(line, Employee.class);
                if (employee.getName().equals(name))
                    continue;
                newLines.add(gson.toJson(employee));
            }
            Files.write(pathToEmployees, newLines);
        } catch (IOException e) {
            throw new ReportManagementSystemException("Bad file path");
        }
    }

    @Override
    public List<Employee> getAllEmployees() throws ReportManagementSystemException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(pathToEmployees)));
            String line;
            List<Employee> ans = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                Gson gson = new Gson();
                ans.add(gson.fromJson(line, Employee.class));
            }
            return ans;
        } catch (IOException e) {
            throw new ReportManagementSystemException("Bad file path");
        }
    }

    public void saveReportConfig(DayReport dayReport) throws ReportManagementSystemException {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(pathToConfigReports.toFile(), true));
            Gson gson = new Gson();
            out.println(gson.toJson(dayReport));
            out.close();

        } catch (IOException e) {
            throw new ReportManagementSystemException("Bad file path");
        }
    }

    @Override
    public List<DayReport> getAllReports() throws ReportManagementSystemException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(pathToConfigReports)));
            String line;
            List<DayReport> ans = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                Gson gson = new Gson();
                ans.add(gson.fromJson(line, DayReport.class));
            }
            return ans;
        } catch (IOException e) {
            throw new ReportManagementSystemException("Bad file path");
        }
    }


}



