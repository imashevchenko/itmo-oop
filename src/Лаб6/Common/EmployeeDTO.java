package Лаб6.Common;

import Лаб6.BLL.EmployeeManagement;
import Лаб6.DAL.EmployeeDAL;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class EmployeeDTO {

    private String name;
    private ArrayList<EmployeeDTO> subordinates;
    private boolean isTeamLead = false;
    private Date LastReportTime;

    public EmployeeDTO(String name) {
        this.name = name;
        this.subordinates = new ArrayList<>();
    }

    public EmployeeDTO(EmployeeDAL employeeDAL) {
        EmployeeDTO employee = EmployeeManagement.transferBack(employeeDAL);
        this.name = employee.name;
        this.subordinates = employee.subordinates;
        this.isTeamLead = employee.isTeamLead();
        this.LastReportTime = employee.LastReportTime;
    }

    public EmployeeDTO(String name, ArrayList<EmployeeDTO> subordinates, boolean isTeamLead, Date lastReportTime) {
        this.name = name;
        this.subordinates = subordinates;
        this.isTeamLead = isTeamLead;
        LastReportTime = lastReportTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDTO employee = (EmployeeDTO) o;
        return name.equals(employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }


    public ArrayList<EmployeeDTO> getSubordinates() {
        return subordinates;
    }

    public void addSubordinate(EmployeeDTO employee) {
        subordinates.add(employee);
    }


    public boolean isTeamLead() {
        return isTeamLead;
    }

    public void setTeamLead(boolean teamLead) {
        isTeamLead = teamLead;
    }


    public Date getLastReportTime() {
        return LastReportTime;
    }

    public void setLastReportTime(Date lastReportTime) {
        LastReportTime = lastReportTime;
    }
}
