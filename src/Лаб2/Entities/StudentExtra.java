package Лаб2.Entities;

import Лаб0.Exceptions.IsuException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentExtra {
    private final long id;
    private final String name;
    private GroupExtra group;
    private List<OGNP> ognps;


    public StudentExtra(long id, String name, GroupExtra group) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.ognps = new ArrayList<>();
    }

    public void enrollOnOGNP(OGNP ognp) throws IsuException {
        if (ognp.getMegafaculty().equals(group.getMegafaculty()))
            throw new IsuException("OGNP from the same megafaculty");
        if (ognps.contains(ognp))
            throw new IsuException("Already enrolled");
        if (ognps.size() == 2)
            throw new IsuException("Too many OGNPs");
        ognps.add(ognp);
    }

    public void cancelOgnpEnrollment(OGNP ognp) throws IsuException {
        if (!ognps.contains(ognp))
            throw new IsuException("Enrollment not founded");
        ognps.remove(ognp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentExtra student = (StudentExtra) o;
        return id == student.id && name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GroupExtra getGroup() {
        return group;
    }

    public void setGroup(GroupExtra group) {
        this.group = group;
    }

    public List<OGNP> getOgnps() {
        return ognps;
    }
}
