package Лаб2.Entities;

import Лаб0.Exceptions.IsuException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OGNP {
    private List<GroupOGNP> OGNPgroups;
    private String megafaculty;


    public OGNP(String megafaculty) throws IsuException {
        if (megafaculty.isBlank())
            throw new IsuException("Invalid megafaculty name");
        this.OGNPgroups = new ArrayList<>();
        this.megafaculty = megafaculty;
    }


    public void AddGroup(GroupOGNP group){
        OGNPgroups.add(group);
    }

    public void RemoveGroup(GroupOGNP group){
        OGNPgroups.remove(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OGNP ognp = (OGNP) o;
        return OGNPgroups.equals(ognp.OGNPgroups) && megafaculty.equals(ognp.megafaculty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(OGNPgroups, megafaculty);
    }

    public String getMegafaculty() {
        return megafaculty;
    }

    public List<GroupOGNP> getOGNPgroups() {
        return OGNPgroups;
    }
}
