package Лаб6.BLL;

import Лаб6.Common.EmployeeDTO;
import Лаб6.DAL.EmployeeDAL;
import Лаб6.DAL.IRepository;
import Лаб6.ReportManagementSystemException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeManagement {

    private IRepository repository;

    private ArrayList<EmployeeDTO> employees;
    private EmployeeDTO chief;

    public static EmployeeDAL transfer(EmployeeDTO employee){
        String name = employee.getName();
        ArrayList<EmployeeDAL> subordinates=new ArrayList<>();
        for (EmployeeDTO employeeSub : employee.getSubordinates())
            subordinates.add(transfer(employeeSub));
        boolean isTeamLead = employee.isTeamLead();
        Date last  = employee.getLastReportTime();
        return new EmployeeDAL(name, subordinates, isTeamLead, last);
    }

    public static EmployeeDTO transferBack(EmployeeDAL employee){
        String name = employee.getName();
        ArrayList<EmployeeDTO> subordinates=new ArrayList<>();
        for (EmployeeDAL employeeSub : employee.getSubordinates())
            subordinates.add(transferBack(employeeSub));
        boolean isTeamLead = employee.isTeamLead;
        Date last  = employee.LastReportTime;
        return new EmployeeDTO(name, subordinates, isTeamLead, last);
    }

    public EmployeeManagement(IRepository repository) throws ReportManagementSystemException {
        employees = new ArrayList<>();
        this.repository = repository;
        List<EmployeeDAL> employees = repository.getAllEmployees();
        if (!employees.isEmpty()) {
            for (EmployeeDAL employee : employees) {
                this.employees.add(EmployeeManagement.transferBack(employee));
                if (employee.isTeamLead())
                    chief=EmployeeManagement.transferBack(employee);
            }
        }
    }

    public void addEmployee(EmployeeDTO employeeChief, EmployeeDTO employee) throws ReportManagementSystemException {
        employees.add(employee);
        employeeChief.addSubordinate(employee);
        repository.createEmployee(EmployeeManagement.transfer(employee));

        ArrayList<EmployeeDAL> employeeDALS = new ArrayList<>();
        for (EmployeeDTO employee1 : employeeChief.getSubordinates())
            employeeDALS.add(EmployeeManagement.transfer(employee1));
        repository.updateEmployee(employeeChief.getName(), "subordinates", employeeDALS);
    }

    public void addChief(EmployeeDTO employee) throws ReportManagementSystemException {
        employee.setTeamLead(true);
        chief = employee;
        repository.createEmployee(EmployeeManagement.transfer(chief));
    }

    public void updateEmployee(String name, String fieldName, Object newValue) throws ReportManagementSystemException {
        EmployeeDTO employee1 = employees.stream().filter(employee -> employee.getName().equals(name)).findFirst().orElse(null);
        if (employee1 == null)
            throw new ReportManagementSystemException("Bad name provided");
        employees.set(employees.indexOf(employee1), EmployeeManagement.transferBack(repository.updateEmployee(name, fieldName, newValue)));
    }

    public EmployeeDTO getEmployee(String name){
        return employees.stream().filter(employee -> employee.getName().equals(name)).findFirst().orElse(null);
    }

    public ArrayList<EmployeeDTO> getEmployees() {
        return employees;
    }

    public EmployeeDTO getChief() {
        return chief;
    }
}
