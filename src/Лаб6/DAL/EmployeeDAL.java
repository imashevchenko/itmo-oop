package Лаб6.DAL;

import java.util.ArrayList;
import java.util.Date;

public class EmployeeDAL {

    private String name;
    public ArrayList<EmployeeDAL> subordinates;
    public boolean isTeamLead;
    public Date LastReportTime;

    public EmployeeDAL(String name, ArrayList<EmployeeDAL> subordinates, boolean isTeamLead, Date lastReportTime) {
        this.name = name;
        this.subordinates = subordinates;
        this.isTeamLead = isTeamLead;
        LastReportTime = lastReportTime;
    }

    public String getName() {
        return name;
    }

    public ArrayList<EmployeeDAL> getSubordinates() {
        return subordinates;
    }

    public boolean isTeamLead() {
        return isTeamLead;
    }
}
