package Лаб2.Entities;

import Лаб0.Entities.CourseNumber;
import Лаб0.Entities.GroupNumber;
import Лаб0.Exceptions.IsuException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GroupExtra {
    static final int groupNameLength = 5;

    private String groupName;
    private CourseNumber courseNumber;
    private GroupNumber groupNumber;
    private List<StudentExtra> StudentsList;
    private List<Lesson> timetable;
    private String megafaculty;

    public GroupExtra(String groupName) throws IsuException {

        if (groupName.isBlank() || groupName.length() != groupNameLength) {
            throw new IsuException("Invalid group name");
        }
        GroupNumber groupNumber = new GroupNumber(Integer.parseInt(groupName.substring(3, 5)));
        CourseNumber courseNumber = new CourseNumber(Integer.parseInt(groupName.substring(2, 3)));

        this.groupName = groupName;
        this.groupNumber = groupNumber;
        this.courseNumber = courseNumber;
        this.StudentsList = new ArrayList<>();
        this.timetable = new ArrayList<>();
        this.megafaculty = groupName.substring(0, 0);
    }


    public boolean isInGroup(StudentExtra student) {
        return StudentsList.contains(student);
    }

    public void addStudent(StudentExtra student) throws IsuException {
        if (isInGroup(student))
            throw new IsuException("Student is already in the group");
        StudentsList.add(student);
    }

    public void removeStudent(StudentExtra student) throws IsuException {
        if (!isInGroup(student))
            throw new IsuException("No such student in the group");
        StudentsList.remove(student);
    }

    public StudentExtra getStudentById(int id) {
        return StudentsList.stream().filter((student) -> student.getId() == id).findFirst().orElse(null);
    }

    public StudentExtra getStudentByName(String name) {
        return StudentsList.stream().filter((student) -> Objects.equals(student.getName(), name)).findFirst().orElse(null);
    }

    public int getGroupSize() {
        return StudentsList.size();
    }

    public void addLesson(Lesson lesson) throws IsuException {
        if (timetable.stream().anyMatch(lesson1 ->
                ((lesson.getStartTime().after(lesson1.getStartTime())) && (lesson.getStartTime().before(lesson1.getEndTime())))
                        || ((lesson1.getStartTime().after(lesson.getStartTime())) && (lesson1.getStartTime().before(lesson.getEndTime())))))
            throw new IsuException("Lessons cover each other");
        timetable.add(lesson);
    }

    public void removeLesson(Lesson lesson) {
        timetable.remove(lesson);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupExtra group = (GroupExtra) o;
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

    public List<StudentExtra> getStudentsList() {
        return StudentsList;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getMegafaculty() {
        return megafaculty;
    }

    public List<Lesson> getTimetable() {
        return timetable;
    }
}
