package Лаб6.BLL;

import Лаб6.DAL.IRepository;
import Лаб6.DAL.TaskDAL;
import Лаб6.ReportManagementSystemException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManagement {

    private static int id=0;

    private IRepository repository;
    private ArrayList<TaskBLL> tasks;

    public TaskManagement(IRepository repository) throws ReportManagementSystemException {
        this.repository = repository;
        this.tasks = new ArrayList<>();
        List<TaskDAL> taskDALS = repository.getAll();
        if (!taskDALS.isEmpty()) {
            for (TaskDAL taskDAL : taskDALS) {
                tasks.add(new TaskBLL(taskDAL));
            }
        }
    }

    public void createTask(String name, String comment, String status, Employee employee) throws ReportManagementSystemException {
        tasks.add(new TaskBLL(id, name, comment, status, employee));
        repository.create(new TaskDAL(id, name, comment, status, employee));
        id++;
    }

    public TaskBLL getTaskById(int id) throws ReportManagementSystemException {
        TaskBLL task = tasks.stream().filter(taskBLL -> taskBLL.getId()==id).findFirst().orElse(null);
        if (task == null)
            throw new ReportManagementSystemException("Bad id provided");
        return task;
    }

    public void updateTask(int id, String fieldName, Object newValue, Employee author) throws ReportManagementSystemException{
        TaskBLL task = tasks.stream().filter(taskBLL -> taskBLL.getId()==id).findFirst().orElse(null);
        if (task == null)
            throw new ReportManagementSystemException("Bad id provided");
        tasks.set(tasks.indexOf(task), new TaskBLL(repository.updateTask(id, fieldName, newValue, author)));
    }

    public ArrayList<TaskBLL> getTasksByEmployee(Employee employee) throws ReportManagementSystemException {
        ArrayList<TaskBLL> tasksByEmployee = (ArrayList<TaskBLL>) tasks.stream().filter(taskBLL -> taskBLL.employee.equals(employee))
                .collect(Collectors.toList());
        return tasksByEmployee;
    }

    public List<TaskBLL> getTasksByEmployeesSubordinates(Employee employee) throws ReportManagementSystemException {
        List<TaskBLL> tasksByEmployee = tasks.stream().filter(taskBLL -> employee.getSubordinates().contains(taskBLL.employee))
                .collect(Collectors.toList());
        return tasksByEmployee;
    }

    public List<TaskBLL> getTasksByChangesAuthor(Employee employee) throws ReportManagementSystemException {
        return tasks.stream()
                .filter(taskBLL -> taskBLL.getChanges().stream().anyMatch(diff -> diff.getAuthor().equals(employee)))
                .collect(Collectors.toList());
    }

    public List<String> getThisWeekTasks(){
        return tasks.stream()
                .filter(taskBLL -> new Date().getTime() - taskBLL.date.getTime() < new Date().getTime() - 5.184e9)
                .map(TaskBLL::getName)
                .collect(Collectors.toList());
    }

    public ArrayList<TaskBLL> getTasks() {
        return tasks;
    }
}
