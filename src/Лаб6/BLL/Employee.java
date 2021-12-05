package Лаб6.BLL;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Employee {

    private String name;
    public ArrayList<Employee> subordinates;
    public boolean isTeamLead = false;
    public Date LastReportTime;

    public Employee(String name) {

        this.name = name;
        this.subordinates = new ArrayList<>();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return name.equals(employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }


    public ArrayList<Employee> getSubordinates() {
        return subordinates;
    }

    public void addSubordinate(Employee employee) {
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
