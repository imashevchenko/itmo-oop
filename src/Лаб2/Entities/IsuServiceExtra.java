package Лаб2.Entities;

import Лаб0.Entities.CourseNumber;
import Лаб0.Exceptions.IsuException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IsuServiceExtra {

    private List<OGNP> ognps;
    private List<GroupExtra> groups;
    private int maxGroupSize;
    private long id = 0;


    public IsuServiceExtra() {
        this.ognps = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public GroupExtra AddGroup(String name) throws IsuException {
        GroupExtra group = new GroupExtra(name);
        if (groups.contains(group)) {
            throw new IsuException("Group is already created");
        }
        groups.add(group);
        return group;
    }

    public OGNP addOGNP(String megafaculty) throws IsuException {
        if (ognps.stream().anyMatch(ognp -> ognp.getMegafaculty().equals(megafaculty)))
            throw new IsuException("OGNP already exists");
        OGNP ognp = new OGNP(megafaculty);
        ognps.add(ognp);
        return ognp;
    }

    public GroupOGNP addGroupOGNP(String megafaculty) throws IsuException {
        OGNP ognp = ognps.stream()
                .filter(ognp1 -> ognp1.getMegafaculty().equals(megafaculty))
                .findFirst()
                .orElse(null);
        if (ognp == null)
            throw new IsuException("No such OGNP");
        GroupOGNP group = new GroupOGNP(id++);
        ognp.AddGroup(group);
        return group;
    }

    public void enrollStudent(StudentExtra student, String megafaculty) throws IsuException {
        OGNP ognp = ognps.stream()
                .filter(ognp1 -> ognp1.getMegafaculty().equals(megafaculty))
                .findFirst()
                .orElse(null);
        if (ognp == null)
            throw new IsuException("No such OGNP");
        GroupOGNP groupOGNP=ognp.getOGNPgroups().stream()
                .filter(groupOGNP1 -> !checkConflicts(student, groupOGNP1))
                .findFirst()
                .orElse(null);
        if (groupOGNP == null)
            throw new IsuException("No groups with corresponding timetable");
        student.enrollOnOGNP(ognp);
        groupOGNP.addStudent(student);
    }

    public void cancelOgnpEnrollment(StudentExtra student, String megafaculty) throws IsuException {
        OGNP ognp = ognps.stream()
                .filter(ognp1 -> ognp1.getMegafaculty().equals(megafaculty))
                .findFirst()
                .orElse(null);
        if (ognp == null)
            throw new IsuException("No such OGNP");
        GroupOGNP groupOGNP=ognp.getOGNPgroups().stream()
                .filter(groupOGNP1 -> groupOGNP1.getStudents().contains(student))
                .findFirst()
                .orElse(null);
        if (groupOGNP == null)
            throw new IsuException("Student wasn't enrolled");
        student.cancelOgnpEnrollment(ognp);
        groupOGNP.removeStudent(student);
    }

    public boolean checkConflicts(StudentExtra student, GroupOGNP groupOGNP){
        List<Lesson> groupOGNPLessons=groupOGNP.getLessons();
        List<Lesson> groupLessons = student.getGroup().getTimetable();

        if (groupOGNPLessons.isEmpty() || groupLessons.isEmpty())
            return false;
        for (Lesson lesson : groupOGNPLessons){
            if (groupLessons.stream().anyMatch(lesson1 ->
                    ((lesson.getStartTime().after(lesson1.getStartTime())) && (lesson.getStartTime().before(lesson1.getEndTime())))
                            || ((lesson1.getStartTime().after(lesson.getStartTime())) && (lesson1.getStartTime().before(lesson.getEndTime())))))
                return true;
        }
        return false;
    }

    public List<GroupOGNP> getGroups(OGNP ognp){
        return ognp.getOGNPgroups();
    }

    public List<StudentExtra> studentsOnCourse(GroupOGNP groupOGNP){
        return groupOGNP.getStudents();
    }

    public List<StudentExtra> notEnrolledStudents(GroupExtra group){
        return group.getStudentsList().stream()
                .filter(student -> student.getOgnps().size()!=2).collect(Collectors.toList());
    }

    public StudentExtra AddStudent(GroupExtra group, String name) throws IsuException {
        if (group.getGroupSize() > maxGroupSize)
            throw new IsuException("Can't add student to group, reached max size");
        id++;
        StudentExtra student = new StudentExtra(id, name, group);
        group.addStudent(student);
        return student;
    }


    public StudentExtra GetStudent(int id) throws IsuException {
        GroupExtra _group = groups.stream()
                .filter((group -> group.getStudentById(id) != null))
                .findFirst()
                .orElse(null);
        if (_group == null)
            throw new IsuException("No student with such ID");
        return _group.getStudentById(id);
    }


    public StudentExtra FindStudent(String name) throws IsuException {
        GroupExtra _group = groups.stream()
                .filter((group -> group.getStudentByName(name) != null))
                .findFirst()
                .orElse(null);
        if (_group == null)
            throw new IsuException("No student with such name");
        return _group.getStudentByName(name);
    }


    public List<StudentExtra> FindStudents(String groupName) throws IsuException {
        GroupExtra _group = groups.stream()
                .filter(group -> groupName.equals(group.getGroupName()))
                .findFirst()
                .orElse(null);
        if (_group == null)
            throw new IsuException("No group with such name");
        return _group.getStudentsList();
    }


    public List<StudentExtra> FindStudents(CourseNumber courseNumber) throws IsuException {
        List<GroupExtra> _groups = groups.stream()
                .filter(group -> group.getCourseNumber().equals(courseNumber))
                .collect(Collectors.toList());
        if (_groups.isEmpty())
            throw new IsuException("No such course number");
        List<StudentExtra> students = new ArrayList<>();
        _groups.forEach(group -> students.addAll(group.getStudentsList()));
        return students;
    }


    public GroupExtra FindGroup(String groupName) throws IsuException {
        GroupExtra _group = groups.stream()
                .filter(group -> groupName.equals(group.getGroupName()))
                .findFirst()
                .orElse(null);
        if (_group == null)
            throw new IsuException("No such group");
        return _group;
    }

    public List<GroupOGNP> FindOGNPGroup(String megafaculty) throws IsuException {
        OGNP ognp = ognps.stream()
                .filter(ognp1 -> ognp1.getMegafaculty().equals(megafaculty))
                .findFirst()
                .orElse(null);
        if (ognp == null)
            throw new IsuException("No such OGNP");
        return ognp.getOGNPgroups();
    }



    public List<GroupExtra> FindGroups(CourseNumber courseNumber) throws IsuException {
        List<GroupExtra> _groups = groups.stream()
                .filter(group -> courseNumber.equals(group.getCourseNumber()))
                .collect(Collectors.toList());
        if (_groups.isEmpty())
            throw new IsuException("No such course number");
        return _groups;
    }


    public void ChangeStudentGroup(StudentExtra student, GroupExtra newGroup) throws IsuException {
        GroupExtra oldGroup = groups.stream()
                .filter(group -> group.isInGroup(student))
                .findFirst()
                .orElse(null);
        if (oldGroup == null)
            throw new IsuException("No such student");
        oldGroup.removeStudent(student);
        newGroup.addStudent(student);
    }

    public int getMaxGroupSize() {
        return maxGroupSize;
    }

    public void setMaxGroupSize(int maxGroupSize) {
        this.maxGroupSize = maxGroupSize;
    }
}
