package Лаб6.BLL;

import Лаб6.DAL.IRepository;
import Лаб6.DAL.TaskDAL;
import Лаб6.ReportManagementSystemException;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManagement {

    private IRepository repository;

    private ArrayList<Employee> employees;
    private Employee chief;

    public EmployeeManagement(IRepository repository) throws ReportManagementSystemException {
        employees = new ArrayList<>();
        this.repository = repository;
        List<Employee> employees = repository.getAllEmployees();
        if (!employees.isEmpty()) {
            for (Employee employee : employees) {
                this.employees.add(employee);
                if (employee.isTeamLead())
                    chief=employee;
            }
        }
    }

    public void addEmployee(Employee employeeChief, Employee employee) throws ReportManagementSystemException {
        employees.add(employee);
        employeeChief.addSubordinate(employee);
        repository.createEmployee(employee);
        repository.updateEmployee(employeeChief.getName(), "subordinates", employeeChief.getSubordinates());
    }

    public void addChief(Employee employee) throws ReportManagementSystemException {
        employee.setTeamLead(true);
        chief = employee;
        repository.createEmployee(chief);
    }

    public void updateEmployee(String name, String fieldName, Object newValue) throws ReportManagementSystemException {
        Employee employee1 = employees.stream().filter(employee -> employee.getName().equals(name)).findFirst().orElse(null);
        if (employee1 == null)
            throw new ReportManagementSystemException("Bad name provided");
        employees.set(employees.indexOf(employee1), repository.updateEmployee(name, fieldName, newValue));
    }

    public Employee getEmployee(String name){
        return employees.stream().filter(employee -> employee.getName().equals(name)).findFirst().orElse(null);
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public Employee getChief() {
        return chief;
    }
}
