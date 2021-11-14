package Лаб0.Entities;

import Лаб0.Exceptions.IsuException;
import Лаб0.Interfaces.IIsuService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IsuService implements IIsuService {

    private List<Group> groups;
    private int maxGroupSize;
    private long id = 0;


    public IsuService() {
        this.groups = new ArrayList<>();
    }

    @Override
    public Group AddGroup(String name) throws IsuException {
        Group group = new Group(name);
        if (groups.contains(group)) {
            throw new IsuException("Group is already created");
        }
        groups.add(group);
        return group;
    }

    @Override
    public Student AddStudent(Group group, String name) throws IsuException {
        if (group.getGroupSize() > maxGroupSize)
            throw new IsuException("Can't add student to group, reached max size");
        id++;
        Student student = new Student(id, name);
        group.addStudent(student);
        return student;
    }

    @Override
    public Student GetStudent(int id) throws IsuException {
        Group _group = groups.stream()
                .filter((group -> group.getStudentById(id) != null))
                .findFirst()
                .orElse(null);
        if (_group == null)
            throw new IsuException("No student with such ID");
        return _group.getStudentById(id);
    }

    @Override
    public Student FindStudent(String name) throws IsuException {
        Group _group = groups.stream()
                .filter((group -> group.getStudentByName(name) != null))
                .findFirst()
                .orElse(null);
        if (_group == null)
            throw new IsuException("No student with such name");
        return _group.getStudentByName(name);
    }

    @Override
    public List<Student> FindStudents(String groupName) throws IsuException {
        Group _group = groups.stream()
                .filter(group -> groupName.equals(group.getGroupName()))
                .findFirst()
                .orElse(null);
        if (_group == null)
            throw new IsuException("No group with such name");
        return _group.getStudentsList();
    }

    @Override
    public List<Student> FindStudents(CourseNumber courseNumber) throws IsuException {
        List<Group> _groups = groups.stream()
                .filter(group -> group.getCourseNumber().equals(courseNumber))
                .collect(Collectors.toList());
        if (_groups.isEmpty())
            throw new IsuException("No such course number");
        List<Student> students = new ArrayList<>();
        _groups.forEach(group -> students.addAll(group.getStudentsList()));
        return students;
    }

    @Override
    public Group FindGroup(String groupName) throws IsuException {
        Group _group = groups.stream()
                .filter(group -> groupName.equals(group.getGroupName()))
                .findFirst()
                .orElse(null);
        if (_group == null)
            throw new IsuException("No such group");
        return _group;
    }

    @Override
    public List<Group> FindGroups(CourseNumber courseNumber) throws IsuException {
        List<Group> _groups = groups.stream()
                .filter(group -> courseNumber.equals(group.getCourseNumber()))
                .collect(Collectors.toList());
        if (_groups.isEmpty())
            throw new IsuException("No such course number");
        return _groups;
    }

    @Override
    public void ChangeStudentGroup(Student student, Group newGroup) throws IsuException {
        Group oldGroup = groups.stream()
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
