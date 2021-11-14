package Лаб0.Entities;

import Лаб0.Exceptions.IsuException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group {
    static final int groupNameLength = 5;

    private String groupName;
    private CourseNumber courseNumber;
    private GroupNumber groupNumber;
    private List<Student> StudentsList;

    public Group(String groupName) throws IsuException {

        if (groupName.isBlank() || groupName.length() != groupNameLength) {
            throw new IsuException("Invalid group name");
        }
        GroupNumber groupNumber = new GroupNumber(Integer.parseInt(groupName.substring(3,5)));
        CourseNumber courseNumber = new CourseNumber(Integer.parseInt(groupName.substring(2,3)));

        this.groupName = groupName;
        this.groupNumber = groupNumber;
        this.courseNumber = courseNumber;
        this.StudentsList = new ArrayList<>();
    }


    public boolean isInGroup(Student student) {
        return StudentsList.contains(student);
    }

    public void addStudent(Student student) throws IsuException {
        if (isInGroup(student))
            throw new IsuException("Student is already in the group");
        StudentsList.add(student);
    }

    public void removeStudent(Student student) throws IsuException {
        if (!isInGroup(student))
            throw new IsuException("No such student in the group");
        StudentsList.remove(student);
    }

    public Student getStudentById(int id){
        return StudentsList.stream().filter((student) -> student.getId() == id).findFirst().orElse(null);
    }

    public Student getStudentByName(String name){
        return StudentsList.stream().filter((student) -> student.getName() == name).findFirst().orElse(null);
    }

    public int getGroupSize(){
        return StudentsList.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupName.equals(group.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName);
    }

    public CourseNumber getCourseNumber() {
        return courseNumber;
    }

    public GroupNumber getGroupNumber() {
        return groupNumber;
    }

    public List<Student> getStudentsList() {
        return StudentsList;
    }

    public String getGroupName() {
        return groupName;
    }
}
