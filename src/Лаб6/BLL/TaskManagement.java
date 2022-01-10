package Лаб6.BLL;

import Лаб6.Common.EmployeeDTO;
import Лаб6.Common.TaskDTO;
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
    private ArrayList<TaskDTO> tasks;

    public TaskManagement(IRepository repository) throws ReportManagementSystemException {
        this.repository = repository;
        this.tasks = new ArrayList<>();
        List<TaskDAL> taskDALS = repository.getAll();
        if (!taskDALS.isEmpty()) {
            for (TaskDAL taskDAL : taskDALS) {
                tasks.add(new TaskDTO(taskDAL));
            }
        }
    }

    public void createTask(String name, String comment, String status, EmployeeDTO employee) throws ReportManagementSystemException {
        tasks.add(new TaskDTO(id, name, comment, status, employee));
        repository.create(new TaskDAL(id, name, comment, status, EmployeeManagement.transfer(employee)));
        id++;
    }

    public TaskDTO getTaskById(int id) throws ReportManagementSystemException {
        TaskDTO task = tasks.stream().filter(taskDTO -> taskDTO.getId()==id).findFirst().orElse(null);
        if (task == null)
            throw new ReportManagementSystemException("Bad id provided");
        return task;
    }

    public void updateTask(int id, String fieldName, Object newValue, EmployeeDTO author) throws ReportManagementSystemException{
        TaskDTO task = tasks.stream().filter(taskDTO -> taskDTO.getId()==id).findFirst().orElse(null);
        if (task == null)
            throw new ReportManagementSystemException("Bad id provided");
        tasks.set(tasks.indexOf(task), new TaskDTO(repository.updateTask(id, fieldName, newValue, EmployeeManagement.transfer(author))));
    }

    public ArrayList<TaskDTO> getTasksByEmployee(EmployeeDTO employee) {
        return (ArrayList<TaskDTO>) tasks.stream().filter(taskDTO -> taskDTO.getEmployee().equals(employee))
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByEmployeesSubordinates(EmployeeDTO employee) {
        return tasks.stream().filter(taskDTO -> employee.getSubordinates().contains(taskDTO.getEmployee()))
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByChangesAuthor(EmployeeDTO employee) {
        return tasks.stream()
                .filter(taskDTO -> taskDTO.getChanges().stream().anyMatch(diff -> EmployeeManagement.transferBack(diff.getAuthor()).equals(employee)))
                .collect(Collectors.toList());
    }

    public List<String> getThisWeekTasks(){
        return tasks.stream()
                .filter(taskDTO -> new Date().getTime() - taskDTO.getDate().getTime() < new Date().getTime() - 5.184e9)
                .map(TaskDTO::getName)
                .collect(Collectors.toList());
    }

    public ArrayList<TaskDTO> getTasks() {
        return tasks;
    }
}
